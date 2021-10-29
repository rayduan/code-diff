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
     public static void main(String[] args) {
        String jsonStr1 = " {\n" +
                "    \"a\": [1,2,3],\n" +
                "    \"b\": 2,\n" +
                "    \"c\": [\n" +
                "        {\n" +
                "            \"d\": 1,\n" +
                "            \"f\": 2\n" +
                "        },\n" +
                "        {\n" +
                "            \"m\": 3,\n" +
                "            \"n\": 4\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        String jsonStr2 = "\n" +
                "{\n" +
                "    \"b\": 2,\n" +
                "    \"c\": [\n" +
                "        {\n" +
                "            \"m\": 3,\n" +
                "            \"n\": 4\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        Configuration conf = Configuration.builder()
                .options(Option.AS_PATH_LIST).build();

//        Filter cheapFictionFilter = Filter.filter(
//                Criteria.where("category").
//        );
        ReadContext ctx = JsonPath.using(conf).parse(jsonStr1);
//        ReadContext ctx = JsonPath.parse(jsonStr1);
//        List<String> jsonPaths = ctx.read("$..*");
        List<String> jsonPaths = ctx.read("$..*[?(@.stddev())]");

        ReadContext ctx1 = JsonPath.parse(jsonStr2);
        ReadContext ctx2 = JsonPath.parse(jsonStr1);
        jsonPaths.forEach(e -> {
            Object read = ctx1.read(e);
            if(read instanceof LinkedHashMap || read instanceof JSONArray){
                return;
            }
            Object read1 = ctx2.read(e);
            if(!read1.equals(read)){
                throw new RuntimeException();
            }

        });

        jsonPaths.forEach(
                System.out::println
        );
}