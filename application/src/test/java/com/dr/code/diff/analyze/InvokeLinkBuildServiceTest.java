package com.dr.code.diff.analyze;

import com.alibaba.fastjson.JSON;
import com.dr.code.diff.CodeDiffApplicationTest;
import com.dr.code.diff.analyze.bean.MethodInfo;
import com.dr.code.diff.enums.MethodNodeTypeEnum;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Package: com.dr.code.diff.analyze
 * @Description: 获取全量调用链测试类
 * @Author: rayduan
 * @CreateDate: 2023/2/24 17:13
 * @Version: 1.0
 * <p>
 */
class InvokeLinkBuildServiceTest extends CodeDiffApplicationTest {
    @Autowired
    private InvokeLinkBuildService invokeLinkBuildService;

    @Test
    void getMethodsInvokeLink() {
        List<String> dirs = new ArrayList<String>();
        dirs.add("D:\\IdeaProjects\\code-diff\\application");
        List<String> excludeFiles = null;
        Map<MethodNodeTypeEnum, List<MethodInfo>> methodsInvokeLink = invokeLinkBuildService.getMethodsInvokeLink(dirs, excludeFiles);
        System.out.println(JSON.toJSON(methodsInvokeLink));
    }


    @Test
    void getMethodsInvokeLink2() {
        List<String> dirs = new ArrayList<String>();
        dirs.add("/Users/rayduan/app/code-diff/master");
        List<String> excludeFiles = null;
        Map<MethodNodeTypeEnum, List<MethodInfo>> methodsInvokeLink = invokeLinkBuildService.getMethodsInvokeLink(dirs, excludeFiles);
        System.out.println(JSON.toJSON(methodsInvokeLink));
    }


}