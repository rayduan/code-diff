package com.dr.code.diff.vercontrol;

import com.dr.code.diff.dto.VersionControlDto;
import lombok.Data;

/**
 * @ProjectName: code-diff-parent
 * @Package: com.dr.code.diff.vercontrol
 * @Description: 代码差异获取流程类定义
 * @Author: duanrui
 * @CreateDate: 2021/4/5 9:56
 * @Version: 1.0
 * <p>
 * Copyright: Copyright (c) 2021
 */
@Data
public abstract class VersionControl {

    protected VersionControlDto versionControlDto;


    public void handler(){
        getDiffCodeClasses();
        getDiffCodeMethods();
    }

    /**
    * @date:2021/4/5
    * @className:VersionControl
    * @author:Administrator
    * @description: 获取差异类
    *
    */
    public abstract void getDiffCodeClasses();

    /**
    * @date:2021/4/5
    * @className:VersionControl
    * @author:Administrator
    * @description: 
    *
    */
    public void getDiffCodeMethods(){

    }


}
