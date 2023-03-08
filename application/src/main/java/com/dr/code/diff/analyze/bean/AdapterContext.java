package com.dr.code.diff.analyze.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
     * 基本包路径
     */
    private String basePackagePath;
}
