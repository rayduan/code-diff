package com.dr.code.diff.vercontrol.git;

import com.dr.code.diff.config.CustomizeConfig;
import com.dr.code.diff.dto.DiffEntryDto;
import com.dr.code.diff.enums.CodeManageTypeEnum;
import com.dr.code.diff.util.GitRepoUtil;
import com.dr.code.diff.util.PathUtils;
import com.dr.code.diff.vercontrol.AbstractVersionControl;
import com.dr.common.utils.mapper.OrikaMapperUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
@Component
public class GitAbstractVersionControl extends AbstractVersionControl {

    @Autowired
    private CustomizeConfig customizeConfig;


    /**
     * 获取操作类型
     */
    @Override
    public CodeManageTypeEnum getType() {
        return CodeManageTypeEnum.GIT;
    }

    @Override
    public void getDiffCodeClasses() {
        try {
            String localBaseRepoDir = PathUtils.getLocalDir(super.versionControlDto.getRepoUrl(), customizeConfig.getGitLocalBaseRepoDir(), super.versionControlDto.getBaseVersion());
            String localNowRepoDir = PathUtils.getLocalDir(super.versionControlDto.getRepoUrl(), customizeConfig.getGitLocalBaseRepoDir(), super.versionControlDto.getNowVersion());
            //原有代码git对象
            Git baseGit = GitRepoUtil.cloneRepository(super.versionControlDto.getRepoUrl(), localBaseRepoDir, super.versionControlDto.getBaseVersion(), customizeConfig.getGitUserName(), customizeConfig.getGitPassWord());
            //现有代码git对象
            Git nowGit = GitRepoUtil.cloneRepository(super.versionControlDto.getRepoUrl(), localNowRepoDir, super.versionControlDto.getNowVersion(), customizeConfig.getGitUserName(), customizeConfig.getGitPassWord());
            AbstractTreeIterator baseTree = GitRepoUtil.prepareTreeParser(baseGit.getRepository(), super.versionControlDto.getBaseVersion());
            AbstractTreeIterator nowTree = GitRepoUtil.prepareTreeParser(nowGit.getRepository(), super.versionControlDto.getNowVersion());
            //获取两个版本之间的差异代码
            List<DiffEntry> diff = null;
            diff = nowGit.diff().setOldTree(baseTree).setNewTree(nowTree).setShowNameAndStatusOnly(true).call();
            //过滤出有效的差异代码
            Collection<DiffEntry> validDiffList = diff.stream()
                    //只计算java文件
                    .filter(e -> e.getNewPath().endsWith(".java"))
                    //排除测试文件
                    .filter(e -> e.getNewPath().contains("src/main/java"))
                    //只计算新增和变更文件
                    .filter(e -> DiffEntry.ChangeType.ADD.equals(e.getChangeType()) || DiffEntry.ChangeType.MODIFY.equals(e.getChangeType()))
                    .collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(validDiffList)) {
                List<DiffEntryDto> diffEntrys = OrikaMapperUtils.mapList(validDiffList, DiffEntry.class, DiffEntryDto.class);
                super.versionControlDto.setDiffClasses(diffEntrys);
            }
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }


    /**
     * @param filePackage
     * @date:2021/4/24
     * @className:VersionControl
     * @author:Administrator
     * @description: 获取旧版本文件本地路径
     */
    @Override
    public String getLocalNewPath(String filePackage) {
        String localDir = PathUtils.getLocalDir(super.versionControlDto.getRepoUrl(), customizeConfig.getGitLocalBaseRepoDir(), "");
        return PathUtils.getClassFilePath(localDir,versionControlDto.getNowVersion(),filePackage);
    }

    /**
     * @param filePackage
     * @date:2021/4/24
     * @className:VersionControl
     * @author:Administrator
     * @description: 获取新版本文件本地路径
     */
    @Override
    public String getLocalOldPath(String filePackage) {
        String localDir = PathUtils.getLocalDir(super.versionControlDto.getRepoUrl(), customizeConfig.getGitLocalBaseRepoDir(), "");
        return PathUtils.getClassFilePath(localDir,versionControlDto.getBaseVersion(),filePackage);
    }
}
