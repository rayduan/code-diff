package com.dr.code.diff.vercontrol;

import com.dr.code.diff.CodeDiffApplicationTest;
import com.dr.code.diff.vercontrol.svn.SvnAbstractVersionControl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ProjectName: code-diff-parent
 * @Package: com.dr.code.diff.vercontrol
 * @Description: java类作用描述
 * @Author: duanrui
 * @CreateDate: 2021/4/5 14:03
 * @Version: 1.0
 * <p>
 * Copyright: Copyright (c) 2021
 */
class SvnAbstractVersionControlTest extends CodeDiffApplicationTest {

    @Autowired
    private SvnAbstractVersionControl svnVersionControl;

    @Test
    void getDiffCodeClasses() {
//        VersionControlDto build = VersionControlDto.builder()
//                .repoUrl("svn://192.168.75.130/svn/code/code-diif")
//                .baseVersion("3")
//                .nowVersion("5")
//                .build();
//        svnVersionControl.setVersionControlDto(build);
//        svnVersionControl.getDiffCodeClasses();
    }
}