package com.dr.code.diff.vo.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @date:2021/1/9
 * @className:MethodInfoResultVO
 * @author:Administrator
 * @description: 方法对象
 */
@Data
@ApiModel("方法对象")
public class MethodInfoResultVO {


//    /**
//     * 方法的md5
//     */
//    @ApiModelProperty(name = "md5", value = "方法的md5", dataType = "string", example = "13E2BFB69F7D987A6DB4272400C94E9B")
//    public String md5;
    /**
     * 方法名
     */
    @ApiModelProperty(name = "methodName", value = "方法名", dataType = "string", example = "getAll")
    public String methodName;
    /**
     * 方法参数
     */
    @ApiModelProperty(name = "parameters", value = "parameters", dataType = "string")
    public List<String> parameters;


}
