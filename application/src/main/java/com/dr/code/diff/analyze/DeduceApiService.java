package com.dr.code.diff.analyze;

import com.alibaba.fastjson.JSON;
import com.dr.code.diff.analyze.bean.MethodInfo;
import com.dr.code.diff.dto.*;
import com.dr.code.diff.enums.MethodNodeTypeEnum;
import com.dr.code.diff.service.CodeDiffService;
import com.dr.common.log.LoggerUtil;
import com.dr.common.utils.mapper.OrikaMapperUtils;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
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

    /**
     * 推断api
     *
     * @param diffMethodParams diff方法参数
     * @return {@link ApiModify}
     */
    public ApiModify deduceApi(DiffMethodParams diffMethodParams) {
        ApiModify apiModify = ApiModify.builder().httpApiModifies(new HashSet<>()).dubboApiModifies(new HashSet<>()).build();
        //先获取源码,获取差异代码
        DiffInfo diffCode = codeDiffService.getDiffCode(diffMethodParams);
        //然后编译源码
        String pomPath = diffCode.getNewProjectPath() + "pom.xml";
        LoggerUtil.info(log, "开始编译项目", pomPath);
        mavenCmdInvokeService.compileCode(pomPath);
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
        List<MethodInfo> httpMethodInfoList = methodsInvokeLink.get(MethodNodeTypeEnum.HTTP);
        List<MethodInfo> dubboMethodInfoList = methodsInvokeLink.get(MethodNodeTypeEnum.DUBBO);
        Map<String, List<DiffClassInfoResult>> typeMap = diffCode.getDiffClasses().stream().collect(Collectors.groupingBy(DiffClassInfoResult::getType));
        List<DiffClassInfoResult> addMethods = typeMap.get("ADD");
        List<DiffClassInfoResult> modifyMethods = typeMap.get("MODIFY");
        //变更方法签名集合
        List<String> modifyMethodSigns = new ArrayList<String>();
        //新增类集合
        List<String> addClassNames = new ArrayList<String>();
        if (!CollectionUtils.isEmpty(addMethods)) {
            addClassNames = addMethods.stream().map(DiffClassInfoResult::getClassFile).collect(Collectors.toList());
        }
        if (!CollectionUtils.isEmpty(modifyMethods)) {
            modifyMethodSigns = modifyMethods.stream().map(DiffClassInfoResult::getMethodInfos).flatMap(Collection::stream).filter(Objects::nonNull).map(MethodInfoResult::getMethodSign).collect(Collectors.toList());
        }
        getDiffApi(httpMethodInfoList, dubboMethodInfoList, modifyMethodSigns, apiModify);
        getDiffApi(httpMethodInfoList, dubboMethodInfoList, addClassNames, apiModify);
        return apiModify;

    }

    /**
     * 得到diff api
     *
     * @param httpMethodInfoList  http方法信息列表
     * @param dubboMethodInfoList dubbo方法信息列表
     * @param keys                变更类的键值
     * @param apiModify           api修改
     */
    private void getDiffApi(List<MethodInfo> httpMethodInfoList, List<MethodInfo> dubboMethodInfoList, List<String> keys, ApiModify apiModify) {
        if (!CollectionUtils.isEmpty(keys)) {
            keys.forEach(e -> {
                        //计算http接口变更
                        if (!CollectionUtils.isEmpty(httpMethodInfoList)) {
                            List<MethodInfo> httpModify = httpMethodInfoList.stream().filter(h -> JSON.toJSONString(h).contains(e)).collect(Collectors.toList());
                            List<HttpApiModify> httpApiModifies = OrikaMapperUtils.mapList(httpModify, MethodInfo.class, HttpApiModify.class);
                            apiModify.getHttpApiModifies().addAll(httpApiModifies);
                        }
                        //计算dubbo接口变更
                        if (!CollectionUtils.isEmpty(dubboMethodInfoList)) {
                            List<MethodInfo> dubboModify = dubboMethodInfoList.stream().filter(h -> JSON.toJSONString(h).contains(e)).collect(Collectors.toList());
                            List<DubboApiModify> dubboApiModifies = OrikaMapperUtils.mapList(dubboModify, MethodInfo.class, DubboApiModify.class);
                            apiModify.getDubboApiModifies().addAll(dubboApiModifies);
                        }
                    }
            );
        }
    }


}

