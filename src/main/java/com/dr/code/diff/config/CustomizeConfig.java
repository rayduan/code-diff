package com.dr.code.diff.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

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
@Data
@Configuration
public class CustomizeConfig {


    /**
     * git账号
     */
    @Value(value = "${git.userName}")
    private String gitUserName;
    /**
     * git密码
     */
    @Value(value = "${git.password}")
    private String gitPassWord;

    /**
     * git下载代码到本地的根目录
     */
    @Value(value = "${git.local.base.dir}")
    private String gitLocalBaseRepoDir;


    /**
     * git账号
     */
    @Value(value = "${svn.userName}")
    private String svnUserName;
    /**
     * git密码
     */
    @Value(value = "${svn.password}")
    private String svnPassWord;

    /**
     * git下载代码到本地的根目录
     */
    @Value(value = "${svn.local.base.dir}")
    private String svnLocalBaseRepoDir;

    @Value(value = "${git.ssh.priKey}")
    private String gitSshPrivateKey;

    /**
     * maven的地址
     */
    @Value(value = "${maven.home}")
    private String mavenHome;

    /**
     * 根代码路径
     */
    @Value(value = "${root.code.path}")
    private String rootCodePath;

    /**
     * jacoco exec路径
     */
    @Value(value = "${jacoco.root.path}")
    private String jacocoRootPath;


    @Value(value = "${custom.link.type:1}")
    private Integer linkType;


    @Value(value = "${custom.dubbo.xml.path}")
    private String dubboXmlPath;
}
