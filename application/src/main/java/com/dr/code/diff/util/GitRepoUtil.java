package com.dr.code.diff.util;

import com.dr.common.errorcode.BizCode;
import com.dr.common.exception.BizException;
import com.dr.common.log.LoggerUtil;
import com.dr.common.utils.file.FileUtil;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;

import java.io.File;
import java.io.IOException;

/**
 * @ProjectName: code-diff-parent
 * @Package: com.dr.code.diff.util
 * @Description: java类作用描述
 * @Author: duanrui
 * @CreateDate: 2021/4/5 11:16
 * @Version: 1.0
 * <p>
 * Copyright: Copyright (c) 2021
 */
@Slf4j
public class GitRepoUtil {


    /**
     * 克隆代码到本地
     *
     * @param gitUrl
     * @param codePath
     * @param commitId
     * @return
     * @throws GitAPIException
     * @throws IOException
     */
    public static Git cloneRepository(String gitUrl, String codePath, String commitId,String gitUserName,String gitPassWord) {
        Git git = null;
        try {
            if (!checkGitWorkSpace(gitUrl, codePath)) {
                LoggerUtil.info(log, "本地代码不存在，clone", gitUrl, codePath);
                git = Git.cloneRepository()
                        .setURI(gitUrl)
                        .setCredentialsProvider(new UsernamePasswordCredentialsProvider(gitUserName, gitPassWord))
                        .setDirectory(new File(codePath))
                        .setBranch(commitId)
                        .call();
                // 下载指定commitId/branch
                git.checkout().setName(commitId).call();
            } else {
                LoggerUtil.info(log, "本地代码存在,直接使用", gitUrl, codePath);
                git = Git.open(new File(codePath));
                git.getRepository().getFullBranch();
                //判断是分支还是commitId，分支做更新，commitId无法改变用原有的
                if (git.getRepository().exactRef(Constants.HEAD).isSymbolic()) {
                    //更新代码
                    git.pull().setCredentialsProvider(new UsernamePasswordCredentialsProvider(gitUserName, gitPassWord)).call();
                }
            }
        } catch (IOException | GitAPIException e) {
            e.printStackTrace();
            throw new BizException(BizCode.GIT_OPERATED_FAIlED);
        }
        return git;
    }


    /**
     * 将代码转成树状
     *
     * @param repository
     * @param branchName
     * @return
     */
    public static AbstractTreeIterator prepareTreeParser(Repository repository, String branchName) {
        try {
            RevWalk walk = new RevWalk(repository);
            RevTree tree;
            tree = walk.parseTree(repository.resolve(branchName));
            CanonicalTreeParser TreeParser = new CanonicalTreeParser();
            try (ObjectReader reader = repository.newObjectReader()) {
                TreeParser.reset(reader, tree.getId());
            }
            walk.dispose();
            return TreeParser;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 取远程代码本地存储路径
     *
     * @param gitUrl
     * @param localBaseRepoDir
     * @param version
     * @return
     */
    public static String getLocalDir(String gitUrl, String localBaseRepoDir, String version) {
        StringBuilder localDir = new StringBuilder(localBaseRepoDir);
        if (Strings.isNullOrEmpty(gitUrl)) {
            return "";
        }
        localDir.append("/");
        String repoName = Splitter.on("/")
                .splitToStream(gitUrl).reduce((first, second) -> second)
                .map(e -> Splitter.on(".").splitToStream(e).findFirst().get()).get();
        localDir.append(repoName);
        localDir.append("/");
        localDir.append(version);
        return localDir.toString();
    }



    /**
     * 判断工作目录是否存在，本来可以每次拉去代码时删除再拉取，但是这样代码多的化IO比较大，所以就代码可以复用
     *
     * @param codePath
     * @return
     */
    public static Boolean checkGitWorkSpace(String gitUrl, String codePath) throws IOException {
        Boolean isExist = Boolean.FALSE;
        File RepoGitDir = new File(codePath + "/.git");
        if (!RepoGitDir.exists()) {
            return false;
        }
        Git git = Git.open(new File(codePath));
        if (null == git) {
            return isExist;
        }
        Repository repository = git.getRepository();
        //解析本地代码，获取远程uri,是否是我们需要的git远程仓库
        String repoUrl = repository.getConfig().getString("remote", "origin", "url");
        if (gitUrl.equals(repoUrl)) {
            isExist = Boolean.TRUE;
        } else {
            LoggerUtil.info(log, "本地存在其他仓的代码，先删除");
            FileUtil.removeDir(new File(codePath));
        }
        return isExist;
    }

    /**
     * 获取class文件的地址
     *
     * @param git
     * @param classPackage
     * @return
     */
    public String getClassFile(Git git, String classPackage) {
        StringBuilder builder = new StringBuilder(git.getRepository().getDirectory().getParent());
        return builder.append("/")
                .append(classPackage).toString();
    }

}
