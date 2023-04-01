package com.dr.code.diff.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @Package: com.dr.code.diff.dto
 * @Description: java类作用描述
 * @Author: rayduan
 * @CreateDate: 2023/2/27 21:17
 * @Version: 1.0
 * <p>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiModify {


    /**
     * http api修改
     */
    private Set<HttpApiModify> httpApiModifies;

    /**
     * dubbo api修改
     */
    private Set<ApiMethodModify> dubboApiModifies;




    private Set<ApiMethodModify> customClassModifies;




    private Set<ApiMethodModify> customMethodSignModifies;

}
