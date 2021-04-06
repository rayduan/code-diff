package com.dr.code.diff.vo.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @date:2021/1/9
 * @className:CodeDiffParamVO
 * @author:Administrator
 * @description: 增量代码请求参数
 */
@Data
@ApiModel("增量代码获取参数")
public class CodeDiffParamVO {

    /**
     * git 远程仓库地址
     */
    @ApiModelProperty(name = "name", value = "远程仓库地址", dataType = "String", example = "https://github.com/rayduan/code-diff.git")
    private String repoUrl;

    /**
     * git原始分支或tag
     */
    @ApiModelProperty(name = "name", value = "原始分支或tag", dataType = "String", example = "master")
    private String baseVersion;

    /**
     * git现分支或tag
     */
    @ApiModelProperty(name = "name", value = "现分支或tag", dataType = "String", example = "develop")
    private String nowVersion;


}
