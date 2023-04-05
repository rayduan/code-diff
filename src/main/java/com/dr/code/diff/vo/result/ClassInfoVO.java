package com.dr.code.diff.vo.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Package: com.dr.code.diff.analyze.bean
 * @Description: 类信息
 * @Author: rayduan
 * @CreateDate: 2023/2/23 20:26
 * @Version: 1.0
 * <p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassInfoVO {

    /**
     * 类名
     */
    @ApiModelProperty(value = "类名")
    private String className;


    /**
     * 超类名字
     */
    @ApiModelProperty(value = "超类名字")
    private String superClassName;


    /**
     * 接口类名
     */
    @ApiModelProperty(value = "接口类名")
    private List<String> interfacesClassNames;


    /**
     * 抽象标志
     */
    @ApiModelProperty(value = "抽象标志")
    private Boolean abstractFlag = Boolean.FALSE;

    /**
     * 接口标志
     */
    @ApiModelProperty(value = "接口标志")
    private Boolean interfaceFlag = Boolean.FALSE;


    /**
     * 控制器标志
     */
    @ApiModelProperty(value = "控制器标志")
    private Boolean controllerFlag = Boolean.FALSE;


    /**
     * feign标识
     */
    @ApiModelProperty(value = "feign标识")
    private Boolean feignFlag = Boolean.FALSE;
    /**
     * dubbo标志
     */
    @ApiModelProperty(value = "dubbo标志")
    private Boolean dubboFlag = Boolean.FALSE;

    /**
     * 请求url
     */
    @ApiModelProperty(value = "请求url")
    private String requestUrl;

}
