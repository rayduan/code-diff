package com.dr.code.diff.config;

import com.dr.code.diff.dto.ClassInfoResult;
import com.dr.code.diff.dto.DiffMethodParams;
import com.dr.code.diff.dto.MethodInfoResult;
import com.dr.code.diff.util.MethodParserUtils;
import com.dr.common.log.LoggerUtil;
import com.dr.common.utils.file.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

/**
 * @author rui.duan
 * @version 1.0
 * @className GitConfig
 * @description git配置类
 * @date 2021/01/11 2:30 下午
 */
@Configuration
@Slf4j
public class GitConfig {

    /**
     * git账号
     */
    @Value(value = "${git.userName}")
    private String gitUserName;
    /**
     * git密码
     */
    @Value(value = "${git.password}")
    private String gitPassWord;

    /**
     * git下载代码到本地的根目录
     */
    @Value(value = "${git.local.base.dir}")
    private String localBaseRepoDir;


    @Resource(name = "asyncExecutor")
    private Executor executor;

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
    public Git cloneRepository(String gitUrl, String codePath, String commitId) {
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
                if(git.getRepository().exactRef(Constants.HEAD).isSymbolic()){
                    //更新代码
                    git.pull().setCredentialsProvider(new UsernamePasswordCredentialsProvider(gitUserName, gitPassWord)).call();
                }
            }

        } catch (IOException | GitAPIException e) {
            e.printStackTrace();
        }
        return git;
    }

    /**
     * 判断工作目录是否存在，本来可以每次拉去代码时删除再拉取，但是这样代码多的化IO比较大，所以就代码可以复用
     *
     * @param codePath
     * @return
     */
    public Boolean checkGitWorkSpace(String gitUrl, String codePath) throws IOException {
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
     * 获取差异方法
     *
     * @param diffMethodParams
     * @return
     */
    public List<ClassInfoResult> diffMethods(DiffMethodParams diffMethodParams) {
        try {
            //原有代码git对象
            Git baseGit = cloneRepository(diffMethodParams.getGitUrl(), localBaseRepoDir + diffMethodParams.getBaseVersion(), diffMethodParams.getBaseVersion());
            //现有代码git对象
            Git nowGit = cloneRepository(diffMethodParams.getGitUrl(), localBaseRepoDir + diffMethodParams.getNowVersion(), diffMethodParams.getNowVersion());
            AbstractTreeIterator baseTree = prepareTreeParser(baseGit.getRepository(), diffMethodParams.getBaseVersion());
            AbstractTreeIterator nowTree = prepareTreeParser(nowGit.getRepository(), diffMethodParams.getNowVersion());
            //获取两个版本之间的差异代码
            List<DiffEntry> diff = nowGit.diff().setOldTree(baseTree).setNewTree(nowTree).setShowNameAndStatusOnly(true).call();
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
                return null;
            }
            /**
             * 多线程获取旧代码和新代码的差异类及差异方法
             */
            List<CompletableFuture<ClassInfoResult>> priceFuture = validDiffList.stream().map(item -> getClassMethods(getClassFile(baseGit, item.getNewPath()), getClassFile(nowGit, item.getNewPath()), item)).collect(Collectors.toList());
            return priceFuture.stream().map(CompletableFuture::join).filter(Objects::nonNull).collect(Collectors.toList());
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取class文件的地址
     *
     * @param git
     * @param classPackage
     * @return
     */
    private String getClassFile(Git git, String classPackage) {
        StringBuilder builder = new StringBuilder(git.getRepository().getDirectory().getParent());
        return builder.append("/")
                .append(classPackage).toString();
    }

    /**
     * 获取类的增量方法
     *
     * @param oldClassFile 旧类的本地地址
     * @param mewClassFile 新类的本地地址
     * @param diffEntry    差异类
     * @return
     */
    private CompletableFuture<ClassInfoResult> getClassMethods(String oldClassFile, String mewClassFile, DiffEntry diffEntry) {
        //多线程获取差异方法，此处只要考虑增量代码太多的情况下，每个类都需要遍历所有方法，采用多线程方式加快速度
        return CompletableFuture.supplyAsync(() -> {
            String className = diffEntry.getNewPath().split("\\.")[0].split("src/main/java/")[1];
            //新增类直接标记，不用计算方法
            if (DiffEntry.ChangeType.ADD.equals(diffEntry.getChangeType())) {
                return ClassInfoResult.builder()
                        .classFile(className)
                        .type(DiffEntry.ChangeType.ADD.name())
                        .build();
            }
            List<MethodInfoResult> diffMethods;
            //获取新类的所有方法
            List<MethodInfoResult> newMethodInfoResults = MethodParserUtils.parseMethods(mewClassFile);
            //如果新类为空，没必要比较
            if (CollectionUtils.isEmpty(newMethodInfoResults)) {
                return null;
            }
            //获取旧类的所有方法
            List<MethodInfoResult> oldMethodInfoResults = MethodParserUtils.parseMethods(oldClassFile);
            //如果旧类为空，新类的方法所有为增量
            if (CollectionUtils.isEmpty(oldMethodInfoResults)) {
                diffMethods = newMethodInfoResults;
            } else {   //否则，计算增量方法
                List<String> md5s = oldMethodInfoResults.stream().map(MethodInfoResult::getMd5).collect(Collectors.toList());
                diffMethods = newMethodInfoResults.stream().filter(m -> !md5s.contains(m.getMd5())).collect(Collectors.toList());
            }
            //没有增量方法，过滤掉
            if (CollectionUtils.isEmpty(diffMethods)) {
                return null;
            }
            ClassInfoResult result = ClassInfoResult.builder()
                    .classFile(className)
                    .methodInfos(diffMethods)
                    .type(DiffEntry.ChangeType.MODIFY.name())
                    .build();
            return result;
        }, executor);
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
}
