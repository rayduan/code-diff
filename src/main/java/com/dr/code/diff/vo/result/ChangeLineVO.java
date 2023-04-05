package com.dr.code.diff.vo.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ProjectName: code-diff-parent
 * @Package: com.dr.code.diff.vo.result
 * @Description: java类作用描述
 * @Author: duanrui
 * @CreateDate: 2021/6/24 21:32
 * @Version: 1.0
 * <p>
 * Copyright: Copyright (c) 2021
 */
@Data
@ApiModel("差异代码变更行")
public class ChangeLineVO {
    /**
     * 变更类型
     */
    @ApiModelProperty(name = "type", value = "变更类型，如insert,replace", dataType = "String")
    private String type;

    @ApiModelProperty(name = "startLineNum", value = "变更开始行号")
    private Integer startLineNum;

    @ApiModelProperty(name = "endLineNum", value = "变更结束行号")
    private Integer endLineNum;
}
