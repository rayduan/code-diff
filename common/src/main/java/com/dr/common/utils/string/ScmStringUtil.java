package com.dr.common.utils.string;

import org.springframework.util.ObjectUtils;

/**
 * @author rui.duan
 * @version 1.0
 * @className StringUtil
 * @description 字符串处理逻辑
 * @date 2019-06-26 18:09
 */
public class ScmStringUtil {

    public static String escapeChar(String before){
        if(!ObjectUtils.isEmpty(before)){
            before = before.replaceAll("/", "//") ;
            before = before.replaceAll("_", "/_") ;
            before = before.replaceAll("%", "/%") ;
        }
        return before ;
    }

}
