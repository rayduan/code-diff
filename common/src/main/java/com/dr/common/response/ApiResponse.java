package com.dr.common.response;


import com.dr.common.errorcode.BaseCode;
import com.dr.common.errorcode.Code;
import com.dr.common.exception.BaseException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> implements Serializable {
    private int code;
    private String msg;
    private T data;

    public ApiResponse(Code err) {
        this.code = err.getCode();
        this.msg = err.getFixTips();
    }

    public ApiResponse(BaseException ex) {
        this.code = ex.getCode();
        this.msg = ex.getMsg();
    }

    public ApiResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ApiResponse<T> success() {
        this.code = BaseCode.SUCCESS.getCode();
        this.msg = BaseCode.SUCCESS.getInfo();
        return this;
    }

    public ApiResponse<T> success(T data) {
        this.code = BaseCode.SUCCESS.getCode();
        this.msg = BaseCode.SUCCESS.getInfo();
        this.data = data;
        return this;
    }

    public ApiResponse<T> error(Code code, T data) {
        this.code = code.getCode();
        this.msg = code.getInfo();
        this.data = data;
        return this;
    }

    public ApiResponse<T> error(Code code) {
        this.code = code.getCode();
        this.msg = code.getInfo();
        this.data = null;
        return this;
    }

    public ApiResponse<T> error(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = null;
        return this;
    }
}
