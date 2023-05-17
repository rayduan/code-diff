package com.dr.code.diff.vercontrol;

import cn.hutool.core.io.FileUtil;
import com.dr.code.diff.config.CustomizeConfig;
import com.dr.code.diff.dto.*;
import com.dr.code.diff.enums.CodeManageTypeEnum;
import com.dr.code.diff.util.MethodParserUtils;
import com.dr.code.diff.util.XmlDiffUtils;
import com.dr.code.diff.common.log.LoggerUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.diff.DiffEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

/**
 * @ProjectName: code-diff-parent
 * @Package: com.dr.code.diff.vercontrol
 * @Description: 代码差异获取流程类定义
 * @Author: duanrui
 * @CreateDate: 2021/4/5 9:56
 * @Version: 1.0
 * <p>
 * Copyright: Copyright (c) 2021
 */
@Data
@Slf4j
public abstract class AbstractVersionControl {

    protected VersionControlDto versionControlDto;


    @Resource(name = "asyncExecutor")
    private Executor executor;


    @Autowired
    private CustomizeConfig customizeConfig;

    /**
     * 执行handler
     *
     * @return
     */
    public DiffInfo handler(VersionControlDto versionControlDto) {
        this.versionControlDto = versionControlDto;
        getDiffCodeClasses();
        List<DiffClassInfoResult> diffCodeMethods = getDiffCodeMethods();
        return DiffInfo.builder()
                .newProjectPath(versionControlDto.getNewLocalBasePath())
                .oldProjectPath(versionControlDto.getOldLocalBasePath())
                .diffClasses(diffCodeMethods)
                .build();
    }


    /**
     * 下载代码
     *
     * @param methodInvokeDto 方法调用dto
     * @return {@link String}
     */
    public abstract String downloadCode(MethodInvokeDto methodInvokeDto);


    /**
     * @date:2021/4/5
     * @className:VersionControl
     * @author:Administrator
     * @description: 获取差异类
     */
    public abstract void getDiffCodeClasses();

    /**
     * 获取操作类型
     */
    public abstract CodeManageTypeEnum getType();


    /**
     * @date:2021/4/24
     * @className:VersionControl
     * @author:Administrator
     * @description: 获取旧版本文件本地路径
     */
    public abstract String getLocalNewPath(String filePackage);


    /**
     * @date:2021/4/24
     * @className:VersionControl
     * @author:Administrator
     * @description: 获取新版本文件本地路径
     */
    public abstract String getLocalOldPath(String filePackage);

    /**
     * @date:2021/4/5
     * @className:VersionControl
     * @author:Administrator
     * @description: 获取差异方法
     */
    public List<DiffClassInfoResult> getDiffCodeMethods() {
        if (CollectionUtils.isEmpty(versionControlDto.getDiffClasses())) {
            return null;
        }
        LoggerUtil.info(log, "需要对比的差异类数", versionControlDto.getDiffClasses().size());
        List<CompletableFuture<DiffClassInfoResult>> priceFuture = versionControlDto.getDiffClasses().stream()
                .map(item -> getClassMethods(getLocalOldPath(item.getNewPath()), getLocalNewPath(item.getNewPath()), item))
                .collect(Collectors.toList());
        CompletableFuture.allOf(priceFuture.toArray(new CompletableFuture[0])).join();
        List<DiffClassInfoResult> list = priceFuture.stream().map(CompletableFuture::join).filter(Objects::nonNull).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(list)) {
            LoggerUtil.info(log, "计算出最终差异类数", list.size());
        }
        return list;
    }


    /**
     * 获取类的增量方法
     *
     * @param oldClassFile 旧类的本地地址
     * @param mewClassFile 新类的本地地址
     * @param diffEntry    差异类
     * @return
     */
    private CompletableFuture<DiffClassInfoResult> getClassMethods(String oldClassFile, String mewClassFile, DiffEntryDto diffEntry) {
        //多线程获取差异方法，此处只要考虑增量代码太多的情况下，每个类都需要遍历所有方法，采用多线程方式加快速度
        return CompletableFuture.supplyAsync(() -> {
            String moduleName = diffEntry.getNewPath().split("/")[0];
            if ("src".equals(moduleName)) {
                moduleName = "";
            }
            //mybatis的xml变更
            if (mewClassFile.endsWith(".xml")) {
                String xmlDiffClassName = XmlDiffUtils.getXmlDiffClassName(mewClassFile);
                if (StringUtils.isBlank(xmlDiffClassName)) {
                    return null;
                }
                HashSet<MethodInfoResult> methodSet = new HashSet<>();
                //如果旧的mapper不存在，说明是新增的，java mapper会识别到，这里不用对比
                if (StringUtils.isBlank(oldClassFile) || !FileUtil.exist(oldClassFile)) {
                    return null;
                }
                XmlDiffUtils.getXmlDiffMethod(oldClassFile, mewClassFile, methodSet);
                if (CollectionUtils.isEmpty(methodSet)) {
                    return null;
                }
                methodSet.forEach(e ->
                        e.setMethodSign(xmlDiffClassName + "#" + e.getMethodName() + "#" + String.join(",", e.getParameters())));
                return DiffClassInfoResult.builder()
                        .classFile(xmlDiffClassName)
                        .methodInfos(new ArrayList<>(methodSet))
                        .type(DiffEntry.ChangeType.MODIFY.name())
                        .moduleName(moduleName)
                        .build();
            }
            String className;
            if (diffEntry.getNewPath().contains(customizeConfig.getRootCodePath())) {
                className = diffEntry.getNewPath().split(customizeConfig.getRootCodePath())[1].split("\\.")[0];
            } else {
                className = "";
            }
            //新增类直接标记，不用计算方法
//            if (DiffEntry.ChangeType.ADD.equals(diffEntry.getChangeType())) {
//                return DiffClassInfoResult.builder()
//                        .classFile(className)
//                        .type(DiffEntry.ChangeType.ADD.name())
//                        .moduleName(moduleName)
//                        .lines(diffEntry.getLines())
//                        .build();
//            }
            List<MethodInfoResult> diffMethods;
            //获取新类的所有方法
            List<MethodInfoResult> newMethodInfoResults = MethodParserUtils.parseMethods(mewClassFile, customizeConfig.getRootCodePath());
            //如果新类为空，没必要比较
            if (CollectionUtils.isEmpty(newMethodInfoResults)) {
                return null;
            }
            //获取旧类的所有方法
            List<MethodInfoResult> oldMethodInfoResults = null;
            if (DiffEntry.ChangeType.MODIFY.equals(diffEntry.getChangeType())) {
                oldMethodInfoResults = MethodParserUtils.parseMethods(oldClassFile, customizeConfig.getRootCodePath());
            }
            //如果旧类为空，新类的方法所有为增量
            if (CollectionUtils.isEmpty(oldMethodInfoResults)) {
                diffMethods = newMethodInfoResults;
            } else {   //否则，计算增量方法
                List<String> md5s = oldMethodInfoResults.stream().map(MethodInfoResult::getMd5).collect(Collectors.toList());
                diffMethods = newMethodInfoResults.stream().filter(m -> !md5s.contains(m.getMd5())).collect(Collectors.toList());
            }
            //没有增量方法，过滤掉
            if (CollectionUtils.isEmpty(diffMethods)) {
                return null;
            }
            diffMethods.forEach(e ->
                    e.setMethodSign(className + "#" + e.getMethodName() + "#" + String.join(",", e.getParameters()))
            );
            return DiffClassInfoResult.builder()
                    .classFile(className)
                    .methodInfos(diffMethods)
                    .type(diffEntry.getChangeType().name())
                    .moduleName(moduleName)
                    .lines(diffEntry.getLines())
                    .build();
        }, executor);
    }


}
