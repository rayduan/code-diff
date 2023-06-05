package com.dr.code.diff.analyze.strategy;

import com.dr.code.diff.enums.LinkScopeTypeEnum;

/**
 * @Package: com.dr.code.diff.analyze.strategy
 * @Description: java类作用描述
 * @Author: rayduan
 * @CreateDate: 2023/5/19 10:18
 * @Version: 1.0
 * <p>
 */
public interface MethodInvokeFilter {

     LinkScopeTypeEnum filterType();
     Boolean isMatch(MethodFilterContext methodFilterContext);
}
