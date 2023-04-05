package com.dr.code.diff.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Package: com.dr.code.diff.dto
 * @Description: java类作用描述
 * @Author: rayduan
 * @CreateDate: 2023/2/27 21:17
 * @Version: 1.0
 * <p>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiMethodModify {


    /**
     * 方法签名,com/dr/code/diff/controller/CodeDiffController.getSvnList(String, String, String)
     */
    private String methodSign;

    /**
     * 类名
     */
    private String className;


    /**
     * 方法名称
     */
    private String methodName;


    /**
     * 方法参数
     */
    private List<String> methodParams;


}
