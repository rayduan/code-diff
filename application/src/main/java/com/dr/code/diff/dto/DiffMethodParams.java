package com.dr.code.diff.dto;

import com.dr.code.diff.enums.CodeManageTypeEnum;
import com.dr.code.diff.enums.GitUrlTypeEnum;
import lombok.Builder;
import lombok.Data;

/**
 * @ProjectName: base-service
 * @Package: com.dr.jenkins.jenkins.dto
 * @Description: 差异代码参数
 * @Author: duanrui
 * @CreateDate: 2020/6/20 21:41
 * @Version: 1.0
 * <p>
 * Copyright: Copyright (c) 2020
 */
@Data
@Builder
public class DiffMethodParams {


    /**
     * git 远程仓库地址
     */
    private String repoUrl;

    /**
     * git原始分支或tag
     */
    private String baseVersion = "";

    /**
     * git现分支或tag
     */
    private String nowVersion = "";


    /**
     * 专用于svn新分支
     */
    private String svnRepoUrl;

    /**
     * 版本控制类型
     */
    private CodeManageTypeEnum codeManageTypeEnum;

}
