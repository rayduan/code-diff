package com.dr.code.diff.util;

import java.io.File;

/**
 * @Package: com.netease.precisiontesting.common.utils.file
 * @Description: java类作用描述
 * @Author: rayduan
 * @CreateDate: 2023/4/19 13:57
 * @Version: 1.0
 * <p>
 */
public class FileUtils {
    /**
     * 搜索文件是否存在对应后缀的文件
     *
     * @param directory 目录
     * @param suffix    后缀
     * @return boolean
     */
    public static boolean searchFile(File directory,String suffix) {
        if (directory == null || !directory.isDirectory()) {
            return false;
        }
        File[] files = directory.listFiles();
        if (files == null) {
            return false;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                if (searchFile(file,suffix)) {
                    return true;
                }
            } else if (file.getName().endsWith(suffix)) {
                return true;
            }
        }
        return false;
    }
}
