package com.dr.code.diff.analyze.strategy;

import com.dr.code.diff.enums.LinkScopeTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Package: com.dr.code.diff.analyze.strategy
 * @Description: java类作用描述
 * @Author: rayduan
 * @CreateDate: 2023/6/5 13:49
 * @Version: 1.0
 * <p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MethodFilterContext {

    private LinkScopeTypeEnum linKScopeTypeEnum;

    private String className;

    private String baseClassName;

}
