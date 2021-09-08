package com.dr.code.diff.vercontrol.git;

import com.alibaba.fastjson.JSON;
import com.dr.code.diff.config.CustomizeConfig;
import com.dr.code.diff.dto.ChangeLine;
import com.dr.code.diff.dto.DiffEntryDto;
import com.dr.code.diff.enums.CodeManageTypeEnum;
import com.dr.code.diff.enums.GitUrlTypeEnum;
import com.dr.code.diff.util.GitRepoUtil;
import com.dr.code.diff.util.PathUtils;
import com.dr.code.diff.vercontrol.AbstractVersionControl;
import com.dr.common.errorcode.BizCode;
import com.dr.common.exception.BizException;
import com.dr.common.log.LoggerUtil;
import com.dr.common.utils.mapper.OrikaMapperUtils;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.diff.EditList;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.util.io.DisabledOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
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
@Slf4j
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
            String localBaseRepoDir = GitRepoUtil.getLocalDir(super.versionControlDto.getRepoUrl(), customizeConfig.getGitLocalBaseRepoDir(), super.versionControlDto.getBaseVersion());
            String localNowRepoDir = GitRepoUtil.getLocalDir(super.versionControlDto.getRepoUrl(), customizeConfig.getGitLocalBaseRepoDir(), super.versionControlDto.getNowVersion());
            Git baseGit = null, nowGit = null;
            GitUrlTypeEnum gitUrlTypeEnum = GitRepoUtil.judgeUrlType(super.versionControlDto.getRepoUrl());
            switch (Objects.requireNonNull(gitUrlTypeEnum)) {
                case HTTP: {
                    //原有代码git对象
                    baseGit = GitRepoUtil.httpCloneRepository(super.versionControlDto.getRepoUrl(), localBaseRepoDir, super.versionControlDto.getBaseVersion(), customizeConfig.getGitUserName(), customizeConfig.getGitPassWord());
                    //现有代码git对象
                    nowGit = GitRepoUtil.httpCloneRepository(super.versionControlDto.getRepoUrl(), localNowRepoDir, super.versionControlDto.getNowVersion(), customizeConfig.getGitUserName(), customizeConfig.getGitPassWord());
                    break;
                }
                case SSH: {
                    localBaseRepoDir += GitUrlTypeEnum.SSH.getValue();
                    localNowRepoDir += GitUrlTypeEnum.SSH.getValue();
                    //原有代码git对象
                    baseGit = GitRepoUtil.sshCloneRepository(super.versionControlDto.getRepoUrl(), localBaseRepoDir, super.versionControlDto.getBaseVersion(), customizeConfig.getGitSshPrivateKey());
                    //现有代码git对象
                    nowGit = GitRepoUtil.sshCloneRepository(super.versionControlDto.getRepoUrl(), localNowRepoDir, super.versionControlDto.getNowVersion(), customizeConfig.getGitSshPrivateKey());
                    break;
                }
                default: {
                    LoggerUtil.error(log, "未知类型仓库地址");
                    throw new BizException(BizCode.UNKNOWN_REPOSITY_URL);
                }
            }
            super.versionControlDto.setNewLocalBasePath(localNowRepoDir);
            super.versionControlDto.setOldLocalBasePath(localBaseRepoDir);
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

            if (CollectionUtils.isEmpty(validDiffList)) {
                LoggerUtil.info(log, "没有需要对比的类");
                return;
            }
            List<DiffEntryDto> diffEntries = OrikaMapperUtils.mapList(validDiffList, DiffEntry.class, DiffEntryDto.class);

            Map<String, DiffEntryDto> diffMap = diffEntries.stream().collect(Collectors.toMap(DiffEntryDto::getNewPath, Function.identity()));
            LoggerUtil.info(log, "需要对比的差异类为：", JSON.toJSON(diffEntries));
            DiffFormatter diffFormatter = new DiffFormatter(DisabledOutputStream.INSTANCE);
            diffFormatter.setRepository(nowGit.getRepository());
            diffFormatter.setContext(0);
            //此处是获取变更行，有群友需求新增行或变更行要在类中打标记，此处忽略删除行
            for (DiffEntry diffClass : validDiffList) {
                //获取变更行
                EditList edits = diffFormatter.toFileHeader(diffClass).toEditList();
                if (CollectionUtils.isEmpty(edits)) {
                    continue;
                }
                //获取出新增行和变更行
                List<Edit> list = edits.stream().filter(e -> Edit.Type.INSERT.equals(e.getType()) || Edit.Type.REPLACE.equals(e.getType())).collect(Collectors.toList());
                List<ChangeLine> lines = new ArrayList<>(list.size());
                list.forEach(
                        edit -> {
                            ChangeLine build = ChangeLine.builder().startLineNum(edit.getBeginB()).endLineNum(edit.getEndB()).type(edit.getType().name()).build();
                            lines.add(build);
                        }
                );
                if (diffMap.containsKey(diffClass.getNewPath())) {
                    DiffEntryDto diffEntryDto = diffMap.get(diffClass.getNewPath());
                    diffEntryDto.setLines(lines);
                }
            }
            //设置变更行
            super.versionControlDto.setDiffClasses(new ArrayList<DiffEntryDto>(diffMap.values()));
        } catch (IOException |
                GitAPIException e) {
            e.printStackTrace();
            throw new BizException(BizCode.GET_DIFF_CLASS_ERROR);
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
        return PathUtils.getClassFilePath(super.versionControlDto.getNewLocalBasePath(), filePackage);
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
        return PathUtils.getClassFilePath(super.versionControlDto.getOldLocalBasePath(), filePackage);
    }
}
