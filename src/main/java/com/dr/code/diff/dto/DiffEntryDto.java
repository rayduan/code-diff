package com.dr.code.diff.dto;

import lombok.Data;
import org.eclipse.jgit.diff.DiffEntry;

import java.util.List;

/**
 * @ProjectName: code-diff-parent
 * @Package: com.dr.code.diff.dto
 * @Description: java类作用描述
 * @Author: duanrui
 * @CreateDate: 2021/4/5 18:34
 * @Version: 1.0
 * <p>
 * Copyright: Copyright (c) 2021
 */
@Data
public class DiffEntryDto {


    /**
     * 文件包名
     */
    protected String newPath;

    /**
     * 文件变更类型
     */
    private DiffEntry.ChangeType changeType;


    /**
     * 变更行
     */
    private List<ChangeLine> lines;
}
