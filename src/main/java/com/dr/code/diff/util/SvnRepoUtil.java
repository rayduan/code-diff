package com.dr.code.diff.util;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import org.springframework.util.StringUtils;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.wc.*;

import java.io.File;
import java.util.UUID;

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
public class SvnRepoUtil {

    /**
     * @date:2021/4/5
     * @className:SvnRepoUtil
     * @author:Administrator
     * @description: 获取svn代码仓
     */
    public static void cloneRepository(String repoUrl, String codePath, SVNRevision svnRevision, String userName, String password) {
        try {
            ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
            SVNUpdateClient updateClient = SVNClientManager.newInstance((DefaultSVNOptions) options, userName, password).getUpdateClient();
            updateClient.doCheckout(SVNURL.parseURIEncoded(repoUrl), new File(codePath), svnRevision,svnRevision, SVNDepth.INFINITY, false);
        } catch (SVNException e) {
            e.printStackTrace();
        }
    }


    public static SVNDiffClient getSvnDiffClient(String userName, String password) {
        ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
        //实例化客户端管理类
        return SVNClientManager.newInstance((DefaultSVNOptions) options, userName, password).getDiffClient();
    }

    /**
     * 取远程代码本地存储路径
     *
     * @param repoUrl
     * @param localBaseRepoDir
     * @param version
     * @return
     */
    public static String getSvnLocalDir(String repoUrl, String localBaseRepoDir, String version) {
        StringBuilder localDir = new StringBuilder(localBaseRepoDir);
        if (Strings.isNullOrEmpty(repoUrl)) {
            return "";
        }
        localDir.append("/");
        String repoName = Splitter.on("/")
                .splitToStream(repoUrl).reduce((first, second) -> second).orElse("");
        localDir.append(repoName);
        if(!StringUtils.isEmpty(version)){
            localDir.append("/");
            localDir.append(version);
        }
        //加入uuid防止重复
        localDir.append("/");
        localDir.append(UUID.randomUUID());
        return localDir.toString();
    }


}
