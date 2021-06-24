package com.dr.code.diff.vo.result;

import com.dr.code.diff.dto.ChangeLine;
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
     * 模块名称
     */
    @ApiModelProperty(name = "moduleName", value = "模块名,请把模块名和目录保持一致", dataType = "String", example = "common")
    private String moduleName;

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

    /**
     * 变更行
     */
    @ApiModelProperty(name = "lines", value = "变更行信息")
    private List<ChangeLineVO> lines;

}
