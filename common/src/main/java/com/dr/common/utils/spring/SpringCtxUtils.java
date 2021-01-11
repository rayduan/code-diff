package com.dr.common.utils.spring;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public final class SpringCtxUtils implements ApplicationContextAware {
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringCtxUtils.context = applicationContext;
    }

    public static ApplicationContext getContext() {
        return context;
    }

    /**
     * 获取对象
     *
     * @param beanIdOrName bean name
     * @return Object 一个以所给名字注册的bean的实例
     * @throws BeansException 异常
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanIdOrName) throws BeansException {
        return StringUtils.isEmpty(beanIdOrName) ? null : (T) context.getBean(beanIdOrName);
    }

    /**
     * 获取类型为requiredType的对象
     *
     * @param clazz {@link Class}
     * @return T
     * @throws BeansException 异常
     */
    public static <T> T getBean(Class<T> clazz) throws BeansException {
        return clazz == null ? null : (T) context.getBean(clazz);
    }

    /**
     * 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true
     *
     * @param name bean name
     * @return boolean
     */
    public static boolean containsBean(String name) {
        return context.containsBean(name);
    }

    /**
     * 判断以给定名字注册的bean定义是一个singleton还是一个prototype。 如果与给定名字相应的bean定义没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException）
     *
     * @param name bean name
     * @return boolean
     * @throws NoSuchBeanDefinitionException 异常
     */
    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return context.isSingleton(name);
    }

    /**
     * @param name bean name
     * @return Class 注册对象的类型
     * @throws NoSuchBeanDefinitionException 异常
     */
    public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        return context.getType(name);
    }

    /**
     * 如果给定的bean名字在bean定义中有别名，则返回这些别名
     *
     * @param name bean name
     * @return stringsort array
     * @throws NoSuchBeanDefinitionException 异常
     */
    public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
        return context.getAliases(name);
    }

    /**
     * <pre>
     *     静默获取bean,无视异常
     * </pre>
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBeanQuietly(Class<T> clazz) {
        try{
            return clazz == null ? null : (T) context.getBean(clazz);
        }catch (Exception e){
            return null;
        }
    }
}
