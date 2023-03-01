package com.dr.code.diff.analyze.constant;

import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * @Package: com.dr.code.diff.analyze.constant
 * @Description: 注解常量类
 * @Author: rayduan
 * @CreateDate: 2023/2/23 21:08
 * @Version: 1.0
 * <p>
 */
public class AnnotationConstant {

    // Request的注解
    public static final String[] requestAnnotation = {
            "Lorg/springframework/web/bind/annotation/RequestMapping;",
            "Lorg/springframework/web/bind/annotation/GetMapping;",
            "Lorg/springframework/web/bind/annotation/PostMapping;",
            "Lorg/springframework/web/bind/annotation/DeleteMapping;",
            "Lorg/springframework/web/bind/annotation/PatchMapping;",
            "Lorg/springframework/web/bind/annotation/PutMapping;",
            "Lorg/springframework/web/bind/annotation/MessageMapping;",
            "Lorg/springframework/web/bind/annotation/SubscribeMapping;"};


    /**
     * 请求注释映射
     */
    public static final Map<String, RequestMethod> requestAnnotationMap;
    static {
        requestAnnotationMap = new HashMap<String, RequestMethod>();
        requestAnnotationMap.put("Lorg/springframework/web/bind/annotation/GetMapping;", RequestMethod.GET);
        requestAnnotationMap.put("Lorg/springframework/web/bind/annotation/PostMapping;", RequestMethod.POST);
        requestAnnotationMap.put("Lorg/springframework/web/bind/annotation/DeleteMapping;", RequestMethod.DELETE);
        requestAnnotationMap.put("Lorg/springframework/web/bind/annotation/PatchMapping;", RequestMethod.PATCH);
        requestAnnotationMap.put("Lorg/springframework/web/bind/annotation/PutMapping;", RequestMethod.PUT);

    }


    //dubbo注解
    public static final String[] dubboAnnotation = {
            "Lorg/apache/dubbo/config/annotation/DubboService;",
            "Lcom/alibaba/dubbo/config/annotation/Service;",
            "Lorg/apache/dubbo/config/annotation/Service;",
    };

    //Controller 的注解
    public static final String[] controllerAnnotation = {
            "Lorg/springframework/web/bind/annotation/RestController;",
            "Lorg/springframework/stereotype/Controller;",
    };

    public static final String[] mybatisTagName = {
            "insert",
            "delete",
            "update",
            "select",
    };
    public static final String[] feignAnnotation = {
            "Lorg/springframework/cloud/openfeign/FeignClient;"
    };

}

