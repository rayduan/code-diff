package com.dr.code.diff.vercontrol;

import com.dr.code.diff.CodeDiffApplicationTest;
import com.dr.code.diff.dto.VersionControlDto;
import com.dr.code.diff.vercontrol.svn.SvnVersionControl;
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
class SvnVersionControlTest extends CodeDiffApplicationTest {

    @Autowired
    private SvnVersionControl svnVersionControl;

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