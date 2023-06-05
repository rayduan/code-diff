package com.dr.code.diff.analyze.bean;

import com.dr.code.diff.analyze.strategy.MethodFilterContext;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 适配器上下文
 *
 * @author rayduan
 * @Package: com.dr.code.diff.analyze.bean
 * @Description: java类作用描述
 * @Author: rayduan
 * @CreateDate: 2023/3/8 17:25
 * @Version: 1.0
 * <p>
 * @date 2023/03/08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdapterContext {

    /**
     * 调用链过滤上下文
     */
    private MethodFilterContext methodFilterContext;

    /**
     * dubbo xml指定的类
     */
    private List<String> dubboClasses;
}
