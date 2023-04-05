package com.dr.code.diff.util;

import com.dr.code.diff.CodeDiffApplicationTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * @Package: com.dr.code.diff.util
 * @Description: java类作用描述
 * @Author: rayduan
 * @CreateDate: 2023/3/1 15:48
 * @Version: 1.0
 * <p>
 */
class XmlDiffUtilsTest extends CodeDiffApplicationTest {

    @Test
    void getXmlDiff() {
        XmlDiffUtils.getXmlDiffMethod("/Users/rayduan/app/cmdb/b20bb96a8ca5be1955f9427ccc1aac521d6cb955/cmdb-core/src/main/resources/com/dr/cmdb/core/mapper/TablesModelMapper.xml","/Users/rayduan/app/cmdb/baacfdb9976fe9462096cda0dd1641d4c91158a9/cmdb-core/src/main/resources/com/dr/cmdb/core/mapper/TablesModelMapper.xml",new HashSet<>());
    }
}