package com.dr.code.diff.jacoco.bean;

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
public class RemoteJacocoServer {

    /**
     * 主机
     */
    private String host;


    /**
     * 端口
     */
    private Integer port;


    /**
     * exec文件名称
     */
    private String execFileName;


}
