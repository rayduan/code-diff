package com.dr.common.utils.file;

import com.dr.common.log.LoggerUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * @ProjectName: code-diff
 * @Package: com.dr.common.utils.file
 * @Description: 文件处理类
 * @Author: duanrui
 * @CreateDate: 2021/1/9 22:56
 * @Version: 1.0
 * <p>
 * Copyright: Copyright (c) 2021
 */
@Slf4j
public class FileUtil {
    /**
     * 删除目录下所有文件
     *
     * @param dir
     */
    public static void removeDir(File dir) {
        if (null == dir) {
            return;
        }
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                removeDir(file);
            } else {
                System.out.println(file + ":" + file.delete());
            }
        }
        LoggerUtil.info(log,dir + ":" + dir.delete());
    }
}
