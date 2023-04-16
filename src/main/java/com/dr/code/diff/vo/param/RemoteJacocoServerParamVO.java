package com.dr.code.diff.vo.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ProjectName: code-diff-parent
 * @Package: com.dr.code.diff.analyze.bean
 * @Description: java类作用描述
 * @Author: duanrui
 * @CreateDate: 2023/4/16 16:38
 * @Version: 1.0
 * <p>
 * Copyright: Copyright (c) 2023
 */

@Data
@ApiModel
public class RemoteJacocoServerParamVO {

    /**
     * 主机
     */
    @ApiModelProperty( value = "jacoco agent服务地址", example = "127.0.0.1")
    private String host;


    /**
     * 端口
     */
    @ApiModelProperty( value = "jacoco agent服务端口", example = "6300")
    private Integer port;


    /**
     * exec文件名称
     */
    @ApiModelProperty( value = "生成exec文件的名称", example = "code-diff.exec")
    private String execFileName;


}
