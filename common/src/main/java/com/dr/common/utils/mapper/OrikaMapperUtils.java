package com.dr.common.utils.mapper;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.Type;
import ma.glasnost.orika.metadata.TypeFactory;

import java.util.List;

/**
 * Orika作为JavaBean深拷贝实现工具.
 * <p>
 * 对Orika的功能做简单封装，实现BeanClassA 与 BeanClassB相互深度复制的功能.
 * 不使用ApacheCommonBeanUtils、SpringBeanUtils和Dozer是因为他们的实现基本上都是基于属性反射查找机制，性能差距及内存消耗较大.
 * {@see http://blog.sokolenko.me/2013/05/dozer-vs-orika-vs-manual.html}
 *
 * @Author : Max.
 * @Created : 2018-08-31
 * @E-mail : goday.max@gmail.com
 * @Version : 1.0
 *
 */
public class OrikaMapperUtils {
    private static MapperFacade mapper;

    static {
        MapperFactory factory = new DefaultMapperFactory.Builder().build();
        mapper = factory.getMapperFacade();
    }

    private OrikaMapperUtils() {
    }

    /**
     * 简单的复制出新类型对象.
     * <p>
     * 内部实现是通过source.getClass() 获得源Class
     */
    public static <S, D> D map(S source, Class<D> destinationClass) {
        return mapper.map(source, destinationClass);
    }

    /**
     * 直接复制两个对象的属性
     * <p>
     */
    public static <S, D> void map(S sourceObject, D destinationObject){
         mapper.map(sourceObject, destinationObject);
    }

    /**
     * 极致性能的复制出新类型对象.
     * <p>
     * 预先通过{@link OrikaMapperUtils#getType(Class)} 静态获取并缓存Type类型，在此处传入
     */
    public static <S, D> D map(S source, Type<S> sourceType, Type<D> destinationType) {
        return mapper.map(source, sourceType, destinationType);
    }


    /**
     * 简单的复制出新对象列表到ArrayList
     * <p>
     * 不建议使用{@link MapperFacade#mapAsList(Object[], Class)}} 接口, sourceClass需要在遍历每一个元素的时候反射，实在有点慢
     */
    public static <S, D> List<D> mapList(Iterable<S> sourceList, Class<S> sourceClass, Class<D> destinationClass) {
        return mapper.mapAsList(sourceList, TypeFactory.valueOf(sourceClass), TypeFactory.valueOf(destinationClass));
    }

    /**
     * 极致性能的复制出新类型对象到ArrayList.
     * <p>
     * 预先通过{@link OrikaMapperUtils#getType(Class)} 静态获取并缓存Type类型，在此处传入
     */
    public static <S, D> List<D> mapList(Iterable<S> sourceList, Type<S> sourceType, Type<D> destinationType) {
        return mapper.mapAsList(sourceList, sourceType, destinationType);
    }

    /**
     * 简单复制出新对象列表到数组.
     * <p>
     * 内部实现是通过source.getComponentType() 获得源Class
     *
     * @param destination      要复制到的目标数组
     * @param source           待复制的源数据数组
     * @param destinationClass 要复制到的目标数组数据元素Class
     */
    public static <S, D> D[] mapArray(final D[] destination, final S[] source, final Class<D> destinationClass) {
        return mapper.mapAsArray(destination, source, destinationClass);
    }

    /**
     * 极致性能的复制出新类型对象到数组.
     * <p>
     * 需要预先通过{@link OrikaMapperUtils#getType(Class)} 静态获取并缓存转换所需Type类型，在此处传入
     *
     * @param destination     要复制到的目标数组
     * @param source          待复制的源数据数组
     * @param sourceType      待复制的源数据数组实例类型
     * @param destinationType 要复制到的目标数组类型
     */
    public static <S, D> D[] mapArray(D[] destination, S[] source, Type<S> sourceType, Type<D> destinationType) {
        return mapper.mapAsArray(destination, source, sourceType, destinationType);
    }

    /**
     * 预先获取orika转换所需要的{@link Type}，避免每次复制都做转换.
     */
    public static <E> Type<E> getType(final Class<E> rawType) {
        return TypeFactory.valueOf(rawType);
    }
}
