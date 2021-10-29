package com.dr.code.diff.util;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.ReadContext;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: code-diff-parent
 * @Package: com.dr.code.diff.util
 * @Description: java类作用描述
 * @Author: duanrui
 * @CreateDate: 2021/10/29 20:55
 * @Version: 1.0
 * <p>
 * Copyright: Copyright (c) 2021
 */
public class JsonUtils {

    public static void main(String[] args) {
        String jsonStr1 = " {\n" +
                "    \"a\": [1,2,3],\n" +
                "    \"b\": 2,\n" +
                "    \"c\": [\n" +
                "        {\n" +
                "            \"m\": 3,\n" +
                "            \"n\": 4\n" +
                "        },\n" +
                "        {\n" +
                "            \"d\": 1,\n" +
                "            \"f\": 2\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        String jsonStr2 = "\n" +
                "{\n" +
                "    \"a\": [1,3],\n" +
                "    \"b\": 2,\n" +
                "    \"c\": [\n" +
                "        {\n" +
                "            \"m\": 3,\n" +
                "            \"n\": 4\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        Boolean subJson = isSubJson(jsonStr1, jsonStr2);
        System.out.println(subJson);
    }

    /**
     * 判断第二个json串是不是第一个json串的子集
     *
     * @param actualJson
     * @param expectJson
     * @return
     */
    public static Boolean isSubJson(String actualJson, String expectJson) {
        Configuration conf = Configuration.builder()
                .options(Option.AS_PATH_LIST).build();
        ReadContext ctx = JsonPath.using(conf).parse(expectJson);
        //获取期望json的全目录
        List<String> jsonPaths = ctx.read("$..*");
        if (CollectionUtils.isEmpty(jsonPaths)) {
            return Boolean.FALSE;
        }
        //排除非叶子结点
        List<String> leafPaths = getPathList(jsonPaths);
        ReadContext actualJsonCtx = JsonPath.parse(actualJson);
        ReadContext expectJsonCtx = JsonPath.parse(expectJson);
        //遍历子集，判断子集的value是否等于父集的
        for (String path : leafPaths) {
            Object actualVal = actualJsonCtx.read(path);
            Object expectVal = expectJsonCtx.read(path);
            if (!expectVal.equals(actualVal)) {
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }

    /**
     * 排除非叶子节点
     *
     * @param list
     * @return
     */
    private static List<String> getPathList(List<String> list) {
        List<String> removeList = new ArrayList<>(list.size());
        for (int i = 0; i < list.size(); i++) {
            for (String s : list) {
                if (s.startsWith(list.get(i)) && list.get(i).length() < s.length()) {
                    removeList.add(list.get(i));
                }
            }
        }
        list.removeAll(removeList);
        return list;
    }
}
