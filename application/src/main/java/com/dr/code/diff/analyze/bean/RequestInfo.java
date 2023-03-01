package com.dr.code.diff.analyze.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Package: com.dr.code.diff.analyze.bean
 * @Description: java类作用描述
 * @Author: rayduan
 * @CreateDate: 2023/2/24 15:39
 * @Version: 1.0
 * <p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestInfo {
    private String requestUrl;
    private RequestMethod mappingMethod;
}
