package com.dr.common.exception;


import com.dr.common.errorcode.Code;

public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private int code;
    private String msg;

    public BaseException(Code code) {
        super(code.getFixTips());
        this.code = code.getCode();
        this.msg = code.getFixTips();
    }

    public BaseException(Code code, Throwable e) {
        super(code.getFixTips(), e);
        this.code = code.getCode();
        this.msg = code.getFixTips();
    }

    public BaseException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BaseException(int code, String msg, Throwable e) {
        super(msg, e);
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public BaseException(){
        super();
    }
}
