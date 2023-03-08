package com.dr.code.diff.vercontrol;

import com.dr.code.diff.dto.DiffClassInfoResult;
import com.dr.code.diff.dto.DiffInfo;
import com.dr.code.diff.dto.MethodInvokeDto;
import com.dr.code.diff.dto.VersionControlDto;
import com.dr.code.diff.enums.CodeManageTypeEnum;
import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @ProjectName: cmdb
 * @Package: com.dr.cmdb.application.filedcheck
 * @Description: java类作用描述
 * @Author: duanrui
 * @CreateDate: 2021/3/30 10:10
 * @Version: 1.0
 * <p>
 * Copyright: Copyright (c) 2021
 */
@Component
public class VersionControlHandlerFactory implements CommandLineRunner, ApplicationContextAware {
    private volatile ApplicationContext applicationContext;


    private static Map<String, AbstractVersionControl> handlerMap;


    /**
     * 封装策略
     *
     * @param args
     */
    @Override
    public void run(String... args) {
        Collection<AbstractVersionControl> checkHandlers = this.applicationContext.getBeansOfType(AbstractVersionControl.class).values();
        setHandlerMap(checkHandlers.stream().collect(Collectors.toMap(e -> e.getType().getValue(), Function.identity())));
    }

    /**
     * 设置应用程序上下文
     *
     * @param applicationContext 应用程序上下文
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    /**
     * 设置处理Map
     *
     * @param handlerMap
     */
    private static void setHandlerMap(Map<String, AbstractVersionControl> handlerMap) {
        VersionControlHandlerFactory.handlerMap = handlerMap;
    }

    /**
     * 执行方法校验
     *
     * @param versionControlDto
     */
    public static DiffInfo processHandler(VersionControlDto versionControlDto) {
        CodeManageTypeEnum codeManageTypeEnum = versionControlDto.getCodeManageTypeEnum();
        if (handlerMap.containsKey(codeManageTypeEnum.getValue())) {
            return handlerMap.get(codeManageTypeEnum.getValue()).handler(versionControlDto);
        }
        return null;
    }


    public static String downloadCode(MethodInvokeDto methodInvokeDto) {
        CodeManageTypeEnum codeManageTypeEnum = methodInvokeDto.getCodeManageTypeEnum();
        if (handlerMap.containsKey(codeManageTypeEnum.getValue())) {
            return handlerMap.get(codeManageTypeEnum.getValue()).downloadCode(methodInvokeDto);
        }
        return null;
    }
}
