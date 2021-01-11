package com.dr.common.utils.spring;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Map;

/**
 * SpringBean手动注册帮助类
 * @author shenxiangyu
 * @date 2018-12-11
 */
public class SpringBeanRegisterUtils {
    /**
     * 手动动态向Spring容器中注册Bean
     * @param clazz
     * @param beanName
     * @param propName2BeanNameMap
     */
    public static <T extends Object> T register(Class clazz, String beanName, Map<String, String> propName2BeanNameMap) {
        if (clazz == null ) {
            return null;
        }
        if (beanName == null) {
            beanName = getDefaultBeanName(clazz);
        }
        // 将applicationContext转换为ConfigurableApplicationContext
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) SpringCtxUtils.getContext();

        // 获取bean工厂并转换为DefaultListableBeanFactory
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();

        // 通过BeanDefinitionBuilder创建bean定义
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);

        if (propName2BeanNameMap != null) {
            for (String propName : propName2BeanNameMap.keySet()) {
                String propBeanName = propName2BeanNameMap.get(propName);

                // 设置属性，此属性引用已经定义的bean:propBeanName，前提是这里propBeanName已经被spring容器管理了
                beanDefinitionBuilder.addPropertyReference(propName, propBeanName);
            }
        }

        // 注册bean
        defaultListableBeanFactory.registerBeanDefinition(beanName, beanDefinitionBuilder.getRawBeanDefinition());

        T obj = (T) SpringCtxUtils.getBean(beanName);
        return obj;
    }

    private static String getDefaultBeanName(Class clazz) {
        String simpleName = clazz.getSimpleName();
        String defaultBeanName = (new StringBuilder()).append(Character.toLowerCase(simpleName.charAt(0))).append(simpleName.substring(1)).toString();
        return defaultBeanName;
    }

    /**
     * 使用默认beanName的重载方法
     * @param clazz
     * @param propName2BeanNameMap
     * @param <T>
     * @return
     */
    public static <T extends Object> T register(Class clazz, Map<String, String> propName2BeanNameMap) {
        String beanName = getDefaultBeanName(clazz);
        return register(clazz, beanName, propName2BeanNameMap);
    }

    /**
     * 使用默认beanName和空属性配置的重载方法
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T extends Object> T register(Class clazz) {
        String beanName = getDefaultBeanName(clazz);
        return register(clazz, beanName, null);
    }
}
