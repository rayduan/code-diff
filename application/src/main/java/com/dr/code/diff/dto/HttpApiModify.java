package com.dr.code.diff.dto;

import com.dr.code.diff.enums.MethodNodeTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Package: com.dr.code.diff.dto
 * @Description: java类作用描述
 * @Author: rayduan
 * @CreateDate: 2023/2/27 21:15
 * @Version: 1.0
 * <p>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HttpApiModify {


    /**
     * 方法签名,com/dr/code/diff/controller/CodeDiffController.getSvnList(String, String, String)
     */
    private String methodSign;


    /**
     * url映射,如/api/res/1
     */
    private String mappingUrl;



    /**
     * 映射方法,如get,put
     */
    private RequestMethod mappingMethod;


}
