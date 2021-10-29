package com.dr.code.diff.util;

import com.dr.code.diff.CodeDiffApplicationTest;
import org.junit.jupiter.api.Test;

/**
 * @ProjectName: base-service
 * @Package: com.dr.codediff.util
 * @Description: java类作用描述
 * @Author: duanrui
 * @CreateDate: 2021/1/8 21:11
 * @Version: 1.0
 * <p>
 * Copyright: Copyright (c) 2021
 */
class MethodParserUtilsTest extends CodeDiffApplicationTest {

    @Test
    void parseMethods() {
        MethodParserUtils methodParserUtils = new MethodParserUtils();
        methodParserUtils.parseMethods("D:\\git-test\\43c9a993fbbb35bbe3221dfc20cf07fedb0bb3a2\\collector\\src\\main\\java\\com\\geely\\collector\\mvc\\APIResponse.java");
//        methodParser.parseMethods("D:\\git-test\\43c9a993fbbb35bbe3221dfc20cf07fedb0bb3a2\\third-sdk\\src\\main\\java\\com\\geely\\gitlab\\service\\GitlabService.java");
    }

}