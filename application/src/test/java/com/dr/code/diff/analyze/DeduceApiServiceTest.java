package com.dr.code.diff.analyze;

import com.alibaba.fastjson.JSON;
import com.dr.code.diff.CodeDiffApplicationTest;
import com.dr.code.diff.dto.ApiModify;
import com.dr.code.diff.dto.DiffMethodParams;
import com.dr.code.diff.enums.CodeManageTypeEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Package: com.dr.code.diff.analyze
 * @Description: 接口推导测试类
 * @Author: rayduan
 * @CreateDate: 2023/2/27 21:37
 * @Version: 1.0
 * <p>
 */
class DeduceApiServiceTest extends CodeDiffApplicationTest {

    @Autowired
    private DeduceApiService deduceApiService;

    @Test
    void deduceApi() {
        DiffMethodParams diffMethodParams = DiffMethodParams.builder()
                .repoUrl("https://gitee.com/Dray/code-diff.git")
                .baseVersion("3b097f2e86fce46066189c740a0157d052682851")
                .nowVersion("5eca0308727bded21576c8756e9f93b53fd03109")
                .codeManageTypeEnum(CodeManageTypeEnum.GIT)
                .build();
        ApiModify apiModify = deduceApiService.deduceApi(diffMethodParams);
        System.out.println("变更的接口："+ JSON.toJSONString(apiModify));
    }
}