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

    public static final String PREFIX = "/";
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


    /**
     * 连接路径
     *
     * @param path     路径
     * @param filename 文件名
     * @return {@link String}
     */
    public static String connectPath(String path, String filename) {
        if (StringUtils.isBlank(path)) {
            return filename;
        }
        if (path.endsWith(PREFIX)) {
            if (filename.startsWith(PREFIX)) {
                return path + filename.substring(1);
            }
            return path + filename;
        } else {
            if (filename.startsWith(PREFIX)) {
                return path + filename;
            }
            return path + PREFIX + filename;
        }
    }


}
