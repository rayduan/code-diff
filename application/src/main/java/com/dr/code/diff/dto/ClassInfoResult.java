/*******************************************************************************
 * Copyright (c) 2009, 2019 Mountainminds GmbH & Co. KG and Contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Marc R. Hoffmann - initial API and implementation
 *
 *******************************************************************************/
package com.dr.code.diff.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author dr
 */
@Builder
@Data
public class ClassInfoResult {
    /**
     * java文件
     */
    private String classFile;
    /**
     * 类名
     */
    private String className;
    /**
     * 包名
     */
    private String packages;


    /**
     * 模块名称
     */
    private String moduleName;

    /**
     * 类中的方法
     */
    private List<MethodInfoResult> methodInfos;

    /**
     * 新增的行数
     */
    private List<int[]> addLines;

    /**
     * 删除的行数
     */
    private List<int[]> delLines;

    /**
     * 修改类型
     */
    private String type;



    /**
     * 变更行
     */
    private List<ChangeLine> lines;

}
