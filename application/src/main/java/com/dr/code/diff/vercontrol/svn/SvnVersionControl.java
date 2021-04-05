package com.dr.code.diff.vercontrol.svn;

import com.dr.code.diff.config.CustomizeConfig;
import com.dr.code.diff.enums.CodeManageTypeEnum;
import com.dr.code.diff.util.SvnRepoUtil;
import com.dr.code.diff.vercontrol.VersionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.wc.SVNDiffClient;
import org.tmatesoft.svn.core.wc.SVNRevision;

/**
 * @ProjectName: code-diff-parent
 * @Package: com.dr.code.diff.vercontrol
 * @Description: svn差异代码获取
 * @Author: duanrui
 * @CreateDate: 2021/4/5 9:56
 * @Version: 1.0
 * <p>
 * Copyright: Copyright (c) 2021
 */
@Component
public class SvnVersionControl extends VersionControl {

    @Autowired
    private CustomizeConfig customizeConfig;


    /**
     * 获取操作类型
     */
    @Override
    public CodeManageTypeEnum getType() {
        return CodeManageTypeEnum.SVN;
    }

    @Override
    public void getDiffCodeClasses() {
        try {
            MySVNDiffStatusHandler.list.clear();
            String localBaseRepoDir = SvnRepoUtil.getLocalDir(super.versionControlDto.getRepoUrl(), customizeConfig.getSvnLocalBaseRepoDir(), super.versionControlDto.getBaseVersion());
            String localNowRepoDir = SvnRepoUtil.getLocalDir(super.versionControlDto.getRepoUrl(), customizeConfig.getSvnLocalBaseRepoDir(), super.versionControlDto.getNowVersion());
            SvnRepoUtil.cloneRepository(super.versionControlDto.getRepoUrl(), localBaseRepoDir, super.versionControlDto.getBaseVersion(), customizeConfig.getSvnUserName(), customizeConfig.getSvnPassWord());
            SvnRepoUtil.cloneRepository(super.versionControlDto.getRepoUrl(), localNowRepoDir, super.versionControlDto.getNowVersion(), customizeConfig.getSvnUserName(), customizeConfig.getSvnPassWord());
            SVNDiffClient svnDiffClient = SvnRepoUtil.getSVNDiffClient(customizeConfig.getSvnUserName(), customizeConfig.getSvnPassWord());
            svnDiffClient.doDiffStatus(SVNURL.parseURIEncoded(super.versionControlDto.getRepoUrl()), SVNRevision.create(Long.parseLong(super.versionControlDto.getBaseVersion())), SVNURL.parseURIEncoded(super.versionControlDto.getRepoUrl()), SVNRevision.create(Long.parseLong(super.versionControlDto.getNowVersion())), SVNDepth.INFINITY, true, new MySVNDiffStatusHandler());
            //将差异代码设置进集合
            super.versionControlDto.setDiffClasses(MySVNDiffStatusHandler.list);
        } catch (SVNException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBaseDir() {
        return SvnRepoUtil.getLocalDir(super.versionControlDto.getRepoUrl(), customizeConfig.getSvnLocalBaseRepoDir(),"");
    }
}
