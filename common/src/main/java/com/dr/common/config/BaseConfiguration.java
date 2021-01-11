package com.dr.common.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author rui.duan
 * @version 1.0
 * @className ScmConfiguration
 * @description 系统配置文件
 * @date 2019-07-09 11:18
 */

@Data
@Component
@ConfigurationProperties("base.custom")
public class BaseConfiguration {

    /**
     * 白名单
     */
    private List<String> whiteList;
//
    /**
     * 默认密码
     */
    private String defaultPassword;

    /**
     * 登录url
     */
    private String loginUrl;

    /**
     * 登录成功后页面url
     */
    private String successUrl;

    /**
     * 没有权限的url
     */
    private String unauthorizedUrl;
//
//    /**
//     * 是否开启swagger
//     */
//    @Value(value = "${scm.swagger.enabled:true}")
//    private Boolean swaggerEnabled;
//
//
//    @Value(value = "${bsht.warehouse.url}")
//    protected String warehouseUrl;
//
//    @Value(value = "${bsht.warehouse.partner.id}")
//    protected String partnerID;
//
//    @Value(value ="${bsht.warehouse.partner.key}")
//    protected String partnerKey;
//
//    @Value(value ="${bsht.warehouse.customer.code}")
//    protected String customerCode;
//
//    @Value("${cloud.warehouse.test.list:DEV,TEST}")
//    private String cloudWarehouseTestList;
//
//    @Value(value ="${sg.console.gateway.url}")
//    private String gateway;
//
//    @Value(value = "${scm.mock.enabled:false}")
//    private Boolean mockEnabled;
//
//    @Value(value = "${scm.job.bets.username:jmywsx001}")
//    private String betsUserName;
//
//    @Value(value = "${scm.job.bets.password:JMYW1234}")
//    private String betsPassword;


}
