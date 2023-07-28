package com.dr.code.diff.analyze;

import com.alibaba.fastjson.JSON;
import com.dr.code.diff.analyze.bean.MethodInfo;
import com.dr.code.diff.dto.*;
import com.dr.code.diff.enums.MethodNodeTypeEnum;
import com.dr.code.diff.service.CodeDiffService;
import com.dr.code.diff.util.FileUtils;
import com.dr.code.diff.util.StringUtil;
import com.dr.code.diff.common.log.LoggerUtil;
import com.dr.code.diff.common.utils.mapper.OrikaMapperUtils;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

/**
 * @Package: com.dr.code.diff.analyze
 * @Description: 推导变更接口
 * @Author: rayduan
 * @CreateDate: 2023/2/27 21:12
 * @Version: 1.0
 * <p>
 */
@Service
@Slf4j
public class DeduceApiService {

    @Autowired
    private CodeDiffService codeDiffService;

    @Autowired
    private MavenCmdInvokeService mavenCmdInvokeService;

    @Autowired
    private InvokeLinkBuildService invokeLinkBuildService;

    @Resource(name = "asyncExecutor")
    private Executor executor;

    /**
     * 推断api
     *
     * @param diffMethodParams diff方法参数
     * @return {@link ApiModify}
     */
    public ApiModify deduceApi(DiffMethodParams diffMethodParams) {
        ApiModify apiModify = ApiModify.builder().httpApiModifies(new HashSet<>()).dubboApiModifies(new HashSet<>()).customClassModifies(new HashSet<>()).customMethodSignModifies(new HashSet<>()).build();
        //先获取源码,获取差异代码
        DiffInfo diffCode = codeDiffService.getDiffCode(diffMethodParams);
        //然后编译源码
        String pomPath = StringUtil.connectPath(diffCode.getNewProjectPath(), "pom.xml");
        LoggerUtil.info(log, "开始编译项目", pomPath);
        //这里存在风险，如果代码是分支，且有更新，这里必须要重新编译，先不考虑这个
        boolean compileFlag = FileUtils.searchFile(new File(diffCode.getNewProjectPath()), ".class");
        if (compileFlag) {
            log.info("代码已经编译，直接使用");
        } else {
            //编译代码
            mavenCmdInvokeService.compileCode(pomPath);
        }
        LoggerUtil.info(log, "项目编译完成");
        //获取静态调用链
        LoggerUtil.info(log, "开始获取静态调用链");
        Map<MethodNodeTypeEnum, List<MethodInfo>> methodsInvokeLink = invokeLinkBuildService.getMethodsInvokeLink(Lists.newArrayList(diffCode.getNewProjectPath()), diffMethodParams.getExcludeFiles());
        LoggerUtil.info(log, "获取静态调用链完成");
        if (CollectionUtils.isEmpty(methodsInvokeLink)) {
            return apiModify;
        }
        if (CollectionUtils.isEmpty(diffCode.getDiffClasses())) {
            return apiModify;
        }
//        List<MethodInfo> httpMethodInfoList = methodsInvokeLink.get(MethodNodeTypeEnum.HTTP);
//        List<MethodInfo> dubboMethodInfoList = methodsInvokeLink.get(MethodNodeTypeEnum.DUBBO);
        Map<String, List<DiffClassInfoResult>> typeMap = diffCode.getDiffClasses().stream().collect(Collectors.groupingBy(DiffClassInfoResult::getType));
        List<DiffClassInfoResult> addMethods = typeMap.get("ADD");
        List<DiffClassInfoResult> modifyMethods = typeMap.get("MODIFY");
        //变更方法签名集合
        List<String> modifyMethodSigns = new ArrayList<String>();
        //新增类集合
        List<String> addClassNames = new ArrayList<String>();
        List<String> modifyList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(addMethods)) {
            addClassNames = addMethods.stream().map(DiffClassInfoResult::getClassFile).collect(Collectors.toList());
        }
        if (!CollectionUtils.isEmpty(modifyMethods)) {
            modifyMethodSigns = modifyMethods.stream().map(DiffClassInfoResult::getMethodInfos).flatMap(Collection::stream).filter(Objects::nonNull).map(MethodInfoResult::getMethodSign).collect(Collectors.toList());
        }
        modifyList.addAll(addClassNames);
        modifyList.addAll(modifyMethodSigns);
        getDiffApi(methodsInvokeLink, modifyList, apiModify);
        return apiModify;
    }

    /**
     * 得到diff api
     *
     * @param keys              变更类的键值
     * @param apiModify         api修改
     * @param methodsInvokeLink 方法调用链接
     */
    private void getDiffApi(Map<MethodNodeTypeEnum, List<MethodInfo>> methodsInvokeLink, List<String> keys, ApiModify apiModify) {
        if (!CollectionUtils.isEmpty(keys)) {
            //并发获取每个方法的调用方法
            CompletableFuture.allOf(keys.stream()
                    .map(item -> CompletableFuture.runAsync(() -> {
                        methodsInvokeLink.forEach(
                                (k, v) -> {
                                    if (CollectionUtils.isEmpty(v)) {
                                        return;
                                    }
                                    if (MethodNodeTypeEnum.HTTP.equals(k)) {
                                        //计算http接口变更
                                        List<MethodInfo> httpModify = v.stream().filter(h -> JSON.toJSONString(h).contains(item)).collect(Collectors.toList());
                                        List<HttpApiModify> httpApiModifies = OrikaMapperUtils.mapList(httpModify, MethodInfo.class, HttpApiModify.class);
                                        apiModify.getHttpApiModifies().addAll(httpApiModifies);
                                    } else {
                                        List<MethodInfo> modifies = v.stream().filter(h -> JSON.toJSONString(h).contains(item)).collect(Collectors.toList());
                                        List<ApiMethodModify> apiModifies = OrikaMapperUtils.mapList(modifies, MethodInfo.class, ApiMethodModify.class);
                                        if (MethodNodeTypeEnum.DUBBO.equals(k)) {
                                            apiModify.getDubboApiModifies().addAll(apiModifies);
                                        } else if (MethodNodeTypeEnum.CUSTOM_CLASS.equals(k)) {
                                            apiModify.getCustomClassModifies().addAll(apiModifies);
                                        } else if (MethodNodeTypeEnum.CUSTOM_METHOD.equals(k)) {
                                            apiModify.getCustomMethodSignModifies().addAll(apiModifies);
                                        }
                                    }
                                }
                        );

                    }, executor)).toArray(CompletableFuture[]::new)).join();
        }
    }


}

