package com.dr.code.diff.analyze.strategy;

import com.dr.code.diff.enums.LinkScopeTypeEnum;
import org.springframework.stereotype.Component;

/**
 * @Package: com.dr.code.diff.analyze.strategy
 * @Description: java类作用描述
 * @Author: rayduan
 * @CreateDate: 2023/5/19 10:29
 * @Version: 1.0
 * <p>
 */
@Component
public class GroupIdMethodFilter implements MethodInvokeFilter {

    @Override
    public LinkScopeTypeEnum filterType() {
        return LinkScopeTypeEnum.GROUP_ONLY_TYPE;
    }

    @Override
    public Boolean isMatch(MethodFilterContext methodFilterContext) {
        return methodFilterContext.getClassName().startsWith(methodFilterContext.getBaseClassName());
    }
}
