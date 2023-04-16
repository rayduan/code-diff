package com.dr.code.diff.vo.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Set;

/**
 * @date:2021/1/9
 * @className:CodeDiffResultVO
 * @author:Administrator
 * @description: 差异代码结果集
 */
@Data
public class DeduceApiVO {



    /**
     * http api修改
     */
    @ApiModelProperty(value = "影响的http接口")
    private Set<HttpApiModifyVO> httpApiModifies;

    /**
     * dubbo api修改
     */
    @ApiModelProperty(value = "影响的dubbo接口")
    private Set<ApiMethodModifyVO> dubboApiModifies;


    @ApiModelProperty(value = "影响的自定义类")
    private Set<ApiMethodModifyVO> customClassModifies;



    @ApiModelProperty(value = "影响的自定义方法")
    private Set<ApiMethodModifyVO> customMethodSignModifies;
}
