package com.dr.code.diff.vo.result;

import lombok.Data;

import java.util.Set;

/**
 * @date:2021/1/9
 * @className:CodeDiffResultVO
 * @author:Administrator
 * @description: 差异代码结果集
 */
@Data
public class DeduceApiVO {



    /**
     * http api修改
     */
    private Set<HttpApiModifyVO> httpApiModifies;

    /**
     * dubbo api修改
     */
    private Set<DubboApiModifyVO> dubboApiModifies;
}
