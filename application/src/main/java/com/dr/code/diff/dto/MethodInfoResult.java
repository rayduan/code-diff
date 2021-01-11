package com.dr.code.diff.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author dr
 */
@Builder
@Data
public class MethodInfoResult {
    /**
     * 方法的md5
     */
    public String md5;
    /**
     * 方法名
     */
    public String methodName;
    /**
     * 方法参数
     */
    public String parameters;

}
