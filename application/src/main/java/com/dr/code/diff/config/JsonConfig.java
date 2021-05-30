package com.dr.code.diff.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @ProjectName: code-diff-parent
 * @Package: com.dr.code.diff.config
 * @Description: java类作用描述
 * @Author: duanrui
 * @CreateDate: 2021/5/29 11:49
 * @Version: 1.0
 * <p>
 * Copyright: Copyright (c) 2021
 */
@Configuration
public class JsonConfig implements WebMvcConfigurer {


    /**
     * Configure the {@link HttpMessageConverter HttpMessageConverters} to use for reading or writing
     * to the body of the request or response. If no converters are added, a
     * default list of converters is registered.
     * <p><strong>Note</strong> that adding converters to the list, turns off
     * default converter registration. To simply add a converter without impacting
     * default registration, consider using the method
     * {@link #extendMessageConverters(List)} instead.
     *
     * @param converters initially an empty list of converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                //List字段如果为null,输出为[],而非null
                SerializerFeature.WriteNullListAsEmpty,
                //是否输出值为null的字段,默认为false
                SerializerFeature.WriteMapNullValue,
                //字符串null返回空字符串
                SerializerFeature.WriteNullStringAsEmpty,
                //空布尔值返回false
                SerializerFeature.WriteNullBooleanAsFalse,
                //结果是否格式化,默认为false
                SerializerFeature.PrettyFormat);

        //格式化日期
        fastJsonConfig.setDateFormat("YYYY-MM-dd HH:mm:ss");
        converter.setFastJsonConfig(fastJsonConfig);
        converters.add(0, converter);
    }


}
