package com.dr.code.diff.util;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import org.springframework.util.StringUtils;

/**
 * @ProjectName: code-diff-parent
 * @Package: com.dr.code.diff.util
 * @Description: java类作用描述
 * @Author: duanrui
 * @CreateDate: 2021/4/24 21:17
 * @Version: 1.0
 * <p>
 * Copyright: Copyright (c) 2021
 */
public class PathUtils {

    /**
     * @date:2021/4/5
     * @className:VersionControl
     * @author:Administrator
     * @description: 获取类本地地址
     */
    public static String getClassFilePath(String baseDir, String classPath) {
        StringBuilder builder = new StringBuilder(baseDir);
        builder.append("/");
        builder.append(classPath);
        return builder.toString();
    }


}
