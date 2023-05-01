package com.dr.code.diff.jacoco.bean;

import lombok.Data;

import java.util.List;

/**
 * @ProjectName: code-diff-parent
 * @Package: com.dr.code.diff.jacoco.bean
 * @Description: java类作用描述
 * @Author: duanrui
 * @CreateDate: 2023/4/16 17:08
 * @Version: 1.0
 * <p>
 * Copyright: Copyright (c) 2023
 */
@Data
public class ReportJacocoParam {

    /**
     * 类目录
     */
    private List<String> classesDirectory;
    /**
     * 源码目录
     */
    private List<String> sourceDirectory;
    /**
     * exec文件目录
     * 0
     */
    private List<String> executionDataFile;


    /**
     * 排除类目录
     */
    private String excludedClassesDirectory;

    /**
     * 0
     * 报告生成目录
     */
    private String reportDirectory;


    /**
     * diff代码文件
     */
    private String diffCodeFile;


    /**
     * 报告名称
     */
    private String reportName;

}
