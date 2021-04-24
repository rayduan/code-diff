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
     * @description:  获取类本地地址
     *
     */
    public static String getClassFilePath(String baseDir, String version, String classPath) {
        StringBuilder builder = new StringBuilder(baseDir);
        if(!StringUtils.isEmpty(version)){
            builder.append("/");
            builder.append(version);
        }
        builder.append("/");
        builder.append(classPath);
        return builder.toString();
    }


    /**
     * 取远程代码本地存储路径
     *
     * @param repoUrl
     * @param localBaseRepoDir
     * @param version
     * @return
     */
    public static String getLocalDir(String repoUrl, String localBaseRepoDir, String version) {
        StringBuilder localDir = new StringBuilder(localBaseRepoDir);
        if (Strings.isNullOrEmpty(repoUrl)) {
            return "";
        }
        localDir.append("/");
        String repoName = Splitter.on("/")
                .splitToStream(repoUrl).reduce((first, second) -> second)
                .map(e -> Splitter.on(".").splitToStream(e).findFirst().get()).get();
        localDir.append(repoName);
        if(!StringUtils.isEmpty(version)){
            localDir.append("/");
            localDir.append(version);
        }
        return localDir.toString();
    }
}
