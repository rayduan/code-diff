package com.dr.common.utils.string;

import org.springframework.util.ObjectUtils;

/**
 * @author rui.duan
 * @version 1.0
 * @className StringUtil
 * @description 字符串处理逻辑
 * @date 2019-06-26 18:09
 */
public class BaseStringUtil {

    public static String escapeChar(String before){
        if(!ObjectUtils.isEmpty(before)){
            before = before.replaceAll("/", "//") ;
            before = before.replaceAll("_", "/_") ;
            before = before.replaceAll("%", "/%") ;
        }
        return before ;
    }

    /**
     * solr检索时，转换特殊字符
     *
     * @param s 需要转义的字符串
     * @return 返回转义后的字符串
     */
    public static String escapeQueryChars(String s) {
        if(ObjectUtils.isEmpty(s)){
            return s;
        }
        StringBuilder sb = new StringBuilder();
        //查询字符串一般不会太长，挨个遍历也花费不了多少时间
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            // These characters are part of the query syntax and must be escaped
            if (c == '\\' || c == '+' || c == '-' || c == '!' || c == '(' || c == ')'
                    || c == ':' || c == '^'	|| c == '[' || c == ']' || c == '\"'
                    || c == '{' || c == '}' || c == '~' || c == '*' || c == '?'
                    || c == '|' || c == '&' || c == ';' || c == '/' || c == '.'
                    || c == '$' || Character.isWhitespace(c)) {
                sb.append('\\');
            }
            sb.append(c);
        }
        return sb.toString();
    }

}
