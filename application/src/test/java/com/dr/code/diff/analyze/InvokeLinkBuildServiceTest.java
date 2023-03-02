package com.dr.code.diff.analyze;

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
        dirs.add("/Users/rayduan/app/cmdb/b20bb96a8ca5be1955f9427ccc1aac521d6cb955");
        List<String> excludeFiles = null;
        Map<MethodNodeTypeEnum, List<MethodInfo>> methodsInvokeLink = invokeLinkBuildService.getMethodsInvokeLink(dirs, excludeFiles);
        System.out.println(methodsInvokeLink);
    }
}