package com.dr.code.diff.config;

import com.dr.common.errorcode.BaseCode;
import com.dr.common.exception.BizException;
import com.dr.common.log.LoggerUtil;
import com.dr.common.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * controller统一参数异常切面通知
 *
 * @author 异常处理
 */
@ControllerAdvice
@Slf4j
public class ExceptionAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ApiResponse handleException(Exception e) {
        LoggerUtil.error(log, "未知异常", e);
        ApiResponse response = new ApiResponse(BaseCode.SYSTEM_FAILD);
        response.setData(e.getMessage());
        return response;
    }

    @ExceptionHandler(BizException.class)
    @ResponseBody
    public ApiResponse handleBizException(HttpServletResponse resp, BizException e) {
        LoggerUtil.info(log, "服务业务异常", e.getCode(), e.getMsg());
        return new ApiResponse(e);
    }


    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public ApiResponse handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        LoggerUtil.error(log, "请求必传参数为空", e, e.getMessage());
        return new ApiResponse(BaseCode.PARAM_ERROR);
    }


    /***
     * 拦截HttpMessageNotReadableException
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ApiResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        LoggerUtil.error(log, "请求必传参数为空", e, e.getMessage());
        return new ApiResponse(BaseCode.PARAM_ERROR);
    }


    /**
     * 拦截MethodArgumentNotValidException
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ApiResponse handleBindException(MethodArgumentNotValidException e) {
        StringBuilder builder = new StringBuilder();
        e.getBindingResult().getFieldErrors().stream().forEach(x -> {
            builder.append(x.getField()).append(":").append(x.getDefaultMessage()).append(";");
        });
        LoggerUtil.error(log, "请求参数校验异常", e, builder.toString());
        return new ApiResponse(BaseCode.PARAM_ERROR.getCode(), builder.toString());
    }

    /**
     * 拦截BindException
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ApiResponse handleBindException(BindException e) {
        StringBuilder builder = new StringBuilder();
        e.getFieldErrors().forEach(x -> builder.append(x.getField())
                .append(":")
                .append(x.getDefaultMessage())
                .append(";"));
        LoggerUtil.error(log, "请求参数校验异常", e, builder.toString());
        return new ApiResponse(BaseCode.PARAM_ERROR.getCode(), builder.toString());
    }

}
