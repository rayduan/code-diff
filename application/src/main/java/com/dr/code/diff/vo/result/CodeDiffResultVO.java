package com.dr.code.diff.vo.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @date:2021/1/9
 * @className:CodeDiffResultVO
 * @author:Administrator
 * @description: 差异代码结果集
 */
@Data
@ApiModel("差异代码结果集")
public class CodeDiffResultVO {


    /**
     * java文件
     */
    @ApiModelProperty(name = "classFile", value = "java文件", dataType = "String", example = "com/dr/code/diff/controller/A.java")
    private String classFile;

    /**
     * 类中的方法
     */
    @ApiModelProperty(name = "methodInfos", value = "类中的方法")
    private List<MethodInfoResultVO> methodInfos;


    /**
     * 修改类型
     */
    @ApiModelProperty(name = "type", value = "修改类型", dataType = "String", example = "ADD")
    private String type;

}
