package com.dr.code.diff.service.impl;

import com.dr.code.diff.analyze.InvokeLinkBuildService;
import com.dr.code.diff.analyze.MavenCmdInvokeService;
import com.dr.code.diff.analyze.bean.MethodInfo;
import com.dr.code.diff.common.errorcode.BizCode;
import com.dr.code.diff.common.exception.BizException;
import com.dr.code.diff.dto.*;
import com.dr.code.diff.enums.MethodNodeTypeEnum;
import com.dr.code.diff.service.CodeDiffService;
import com.dr.code.diff.util.FileUtils;
import com.dr.code.diff.vercontrol.VersionControlHandlerFactory;
import com.dr.code.diff.common.utils.mapper.OrikaMapperUtils;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: base-service
 * @Package: com.dr.jenkins.jenkins.service.impl
 * @Description: 获取差异代码
 * @Author: duanrui
 * @CreateDate: 2020/6/20 21:39
 * @Version: 1.0
 * <p>
 * Copyright: Copyright (c) 2020
 */
@Service
@Slf4j
public class CodeDiffServiceImpl implements CodeDiffService {

    @Autowired
    private MavenCmdInvokeService mavenCmdInvokeService;

    @Autowired
    private InvokeLinkBuildService invokeLinkBuildService;

    /**
     * @param diffMethodParams
     * @date:2021/1/9
     * @className:CodeDiffService
     * @author:Administrator
     * @description: 获取差异代码
     */
    @Override
    public DiffInfo getDiffCode(DiffMethodParams diffMethodParams) {
        VersionControlDto dto = OrikaMapperUtils.map(diffMethodParams, VersionControlDto.class);
        return VersionControlHandlerFactory.processHandler(dto);
    }


    /**
     * 得到静态方法调用
     *
     * @param methodInvokeParam 方法调用参数
     * @return methodInvokeParam
     */
    @Override
    public Map<String, List<MethodInfo>> getStaticMethodInvoke(MethodInvokeParam methodInvokeParam) {
        MethodInvokeDto dto = OrikaMapperUtils.map(methodInvokeParam, MethodInvokeDto.class);
        //下载代码
        String path = VersionControlHandlerFactory.downloadCode(dto);
        if (StringUtils.isBlank(path)) {
            throw new BizException(BizCode.GIT_OPERATED_FAIlED);
        }
        //这里存在风险，如果代码是分支，且有更新，这里必须要重新编译，先不考虑这个
        boolean compileFlag = FileUtils.searchFile(new File(path), ".class");
        if (compileFlag) {
            log.info("代码已经编译，直接使用");
        } else {
            //编译代码
            mavenCmdInvokeService.compileCode(path);
        }
        //获取调用链
        Map<MethodNodeTypeEnum, List<MethodInfo>> methodsInvokeLink = invokeLinkBuildService.getMethodsInvokeLink(Lists.newArrayList(path), null);
        if (CollectionUtils.isEmpty(methodsInvokeLink)) {
            return Collections.emptyMap();
        }
        Map<String, List<MethodInfo>> linksMap = new HashMap<>();
        methodsInvokeLink.forEach((k, v) -> linksMap.put(k.getValue(), v));
        return linksMap;
    }
}
