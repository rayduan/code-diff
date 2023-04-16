package com.dr.code.diff.service;

import com.dr.code.diff.analyze.bean.MethodInfo;
import com.dr.code.diff.dto.*;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: base-service
 * @Package: com.dr.jenkins.jenkins.service
 * @Description: java类作用描述
 * @Author: duanrui
 * @CreateDate: 2020/6/20 21:39
 * @Version: 1.0
 * <p>
 * Copyright: Copyright (c) 2020
 */
public interface CodeDiffService {


    /**
     * @date:2021/1/9
     * @className:CodeDiffService
     * @author:Administrator
     * @description: 获取差异代码
     */
    DiffInfo getDiffCode(DiffMethodParams diffMethodParams);



    /**
     * 得到静态方法调用
     *
     * @param methodInvokeParam 方法调用参数
     * @return {@link ApiModify}
     */
    Map<String,List<MethodInfo>> getStaticMethodInvoke(MethodInvokeParam methodInvokeParam);


}
