package com.dr.code.diff.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ProjectName: cmdb
 * @Package: com.dr.cmdb.application.config
 * @Description: 自定义参数
 * @Author: duanrui
 * @CreateDate: 2021/3/18 9:49
 * @Version: 1.0
 * <p>
 * Copyright: Copyright (c) 2021
 */

@Component
@ConfigurationProperties(prefix = "custom.link.start")
@Data
public class CustomizeLinkStartConfig {



    /**
     * 自定义链接类名
     */
    private List<String> classNameList;

    /**
     * 自定义链接方法签名
     */
    private List<String> methodSignList;
}
