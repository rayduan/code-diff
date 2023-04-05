package com.dr.code.diff.analyze;

import com.dr.code.diff.CodeDiffApplicationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Package: com.dr.code.diff.analyze
 * @Description: maven编译类测试类
 * @Author: rayduan
 * @CreateDate: 2023/2/27 21:01
 * @Version: 1.0
 * <p>
 */
class MavenCmdInvokeServiceTest extends CodeDiffApplicationTest {

    @Autowired
    private MavenCmdInvokeService mavenCmdInvoke;

    @Test
    void operationMavenCmd() {
        mavenCmdInvoke.operationMavenCmd("/Users/xx/app/code-diff/5eca0308727bded21576c8756e9f93b53fd03109","clean install -Dmaven.test.skip=true");
    }
    @Test
    void compileCode() {
        mavenCmdInvoke.compileCode("/Users/rayduan/app/code-diff/5eca0308727bded21576c8756e9f93b53fd03109");
    }
}