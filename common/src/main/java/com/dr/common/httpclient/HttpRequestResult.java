package com.dr.common.httpclient;

import lombok.Data;

/**
 * @Description: http 请求结果 对象
 * @Author: luoxiaofei
 * @Date: 2019-06-14 10:06
 **/
@Data
public class HttpRequestResult {

    /**
     * 返回结果code
     */
    private Integer code;

    /**
     * 返回描述
     */
    private String msg;

}

