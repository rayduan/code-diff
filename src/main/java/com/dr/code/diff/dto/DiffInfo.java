package com.dr.code.diff.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @Package: com.dr.code.diff.dto
 * @Description: java类作用描述
 * @Author: rayduan
 * @CreateDate: 2023/2/27 21:28
 * @Version: 1.0
 * <p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiffInfo {


    /**
     * 老项目路径
     */
    private String oldProjectPath;
    /**
     * 新项目路径
     */
    private String newProjectPath;

    /**
     * diff类
     */
    private List<DiffClassInfoResult> diffClasses;
}
