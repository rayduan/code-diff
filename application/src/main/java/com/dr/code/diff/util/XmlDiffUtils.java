package com.dr.code.diff.util;


import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.Diff;
import org.xmlunit.diff.Difference;




/**
 * @Package: com.dr.code.diff.util
 * @Description: java类作用描述
 * @Author: rayduan
 * @CreateDate: 2023/3/1 15:37
 * @Version: 1.0
 * <p>
 */
public class XmlDiffUtils {

    public static void main(String[] args) {
        XmlDiffUtils.getXmlDiff("/Users/rayduan/app/cmdb/b20bb96a8ca5be1955f9427ccc1aac521d6cb955/cmdb-core/src/main/resources/com/dr/cmdb/core/mapper/TablesModelMapper.xml","/Users/rayduan/app/cmdb/baacfdb9976fe9462096cda0dd1641d4c91158a9/cmdb-core/src/main/resources/com/dr/cmdb/core/mapper/TablesModelMapper.xml");
    }

    public static void getXmlDiff(String oldXmlPath, String newXmlPath) {

        Diff diff = DiffBuilder.compare(Input.fromFile(oldXmlPath))
                .withTest(Input.fromFile(newXmlPath))
                .checkForSimilar()
                .ignoreWhitespace()
                .ignoreComments()
                .build();
        Iterable<Difference> differences = diff.getDifferences();
        differences.forEach(
                e -> {
                    System.out.println(e.getComparison().toString());
                }
        );
    }

//    public static void getJaversXmlDiff(String oldXmlPath, String newXmlPath) {
//        Javers javers = JaversBuilder.javers().build();
//        FileReader oldReader = new FileReader(oldXmlPath);
//        FileReader newReader = new FileReader(newXmlPath);
//        JSONObject json1 = XML.toJSONObject(oldReader.readString());
//        JSONObject json2= XML.toJSONObject(newReader.readString());
//        Diff diff = javers.compare(json1,json2);
//        Changes changes = diff.getChanges();
//        changes.forEach(
//                e -> e.getAffectedObject()
//        );
//
//    }
}
