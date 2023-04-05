package com.dr.code.diff;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
* @date:2020/3/13
* @className:JenkinsApplication
* @author: duanrui
* @description: jenkins服务
*
*/
@SpringBootApplication
@ComponentScan(value = "com.dr.**")
public class CodeDiffApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodeDiffApplication.class, args);
    }

}
