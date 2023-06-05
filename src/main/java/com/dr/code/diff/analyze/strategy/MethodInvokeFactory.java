package com.dr.code.diff.analyze.strategy;

import com.dr.code.diff.enums.LinkScopeTypeEnum;
import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Collection;
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
public class MethodInvokeFactory implements CommandLineRunner, ApplicationContextAware {
    private volatile ApplicationContext applicationContext;


    private static Map<String, MethodInvokeFilter> handlerMap;


    /**
     * 封装策略
     *
     * @param args
     */
    @Override
    public void run(String... args) {
        Collection<MethodInvokeFilter> checkHandlers = this.applicationContext.getBeansOfType(MethodInvokeFilter.class).values();
        setHandlerMap(checkHandlers.stream().collect(Collectors.toMap(e -> e.filterType().getValue(), Function.identity())));
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
    private static void setHandlerMap(Map<String, MethodInvokeFilter> handlerMap) {
        MethodInvokeFactory.handlerMap = handlerMap;
    }

    /**
     * 执行方法过滤器
     *
     * @param methodFilterContext
     */
    public static Boolean processHandler(MethodFilterContext methodFilterContext) {
        if (null == methodFilterContext) {
            return Boolean.FALSE;
        }
        LinkScopeTypeEnum linKScopeTypeEnum = methodFilterContext.getLinKScopeTypeEnum();
        if (handlerMap.containsKey(linKScopeTypeEnum.getValue())) {
            return handlerMap.get(linKScopeTypeEnum.getValue()).isMatch(methodFilterContext);
        }
        return null;
    }


}
