package com.dr.code.diff.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ProjectName: code-diff-parent
 * @Package: com.dr.code.diff.dto
 * @Description: java类作用描述
 * @Author: duanrui
 * @CreateDate: 2021/6/24 21:11
 * @Version: 1.0
 * <p>
 * Copyright: Copyright (c) 2021
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangeLine {

    /**
     * 变更类型
     */
    private String type;

    private Integer startLineNum;

    private Integer endLineNum;

}
