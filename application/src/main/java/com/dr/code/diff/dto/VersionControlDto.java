package com.dr.code.diff.dto;

import lombok.Builder;
import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * @ProjectName: code-diff-parent
 * @Package: com.dr.code.diff.dto
 * @Description: java类作用描述
 * @Author: duanrui
 * @CreateDate: 2021/4/5 10:10
 * @Version: 1.0
 * <p>
 * Copyright: Copyright (c) 2021
 */
@Data
@Builder
public class VersionControlDto {

    /**
     *  远程仓库地址
     */
    private String repoUrl;

    /**
     * git原始分支或tag/svn 版本
     */
    private String baseVersion;

    /**
     * git现分支或tag、svn 版本
     */
    private String nowVersion;


    private Object baseRepo;

    private Object nowRepo;

    private List<Object> diffClasses;
}
