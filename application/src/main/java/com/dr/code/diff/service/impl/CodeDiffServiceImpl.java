package com.dr.code.diff.service.impl;

import com.dr.code.diff.config.GitConfig;
import com.dr.code.diff.dto.ClassInfoResult;
import com.dr.code.diff.dto.DiffMethodParams;
import com.dr.code.diff.service.CodeDiffService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ProjectName: base-service
 * @Package: com.dr.jenkins.jenkins.service.impl
 * @Description: 获取差异代码
 * @Author: duanrui
 * @CreateDate: 2020/6/20 21:39
 * @Version: 1.0
 * <p>
 * Copyright: Copyright (c) 2020
 */
@Service
@Slf4j
public class CodeDiffServiceImpl implements CodeDiffService {

    @Autowired
    private GitConfig gitConfig;

    @Override
    public List<ClassInfoResult> getDiffCode(DiffMethodParams diffMethodParams) {
        return gitConfig.diffMethods(diffMethodParams);
    }
}
