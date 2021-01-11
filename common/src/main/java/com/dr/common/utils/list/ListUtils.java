package com.dr.common.utils.list;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author rui.duan
 * @version 1.0
 * @className ListUtils
 * @description 集合去重处理
 * @date 2019/11/21 4:09 下午
 */
public class ListUtils {
    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
