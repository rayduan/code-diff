package com.dr.code.diff.util;

import com.google.common.base.Splitter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Package: com.dr.code.diff.util
 * @Description: java类作用描述
 * @Author: rayduan
 * @CreateDate: 2023/2/23 20:56
 * @Version: 1.0
 * <p>
 */
public class StringUtil {


    /**
     * 获取分割后的最后元素
     *
     * @param str str
     * @return {@link String}
     */
    public static String getSplitLast(String str, String separator) {
        if (StringUtils.isBlank(str)) {
            return str;
        }
        List<String> strings = Splitter.on(separator).omitEmptyStrings().trimResults().splitToList(str);
        if (CollectionUtils.isEmpty(strings)) {
            return str;
        }
        return strings.get(strings.size() - 1);
    }



}