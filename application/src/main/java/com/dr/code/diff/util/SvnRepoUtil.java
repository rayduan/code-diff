package com.dr.code.diff.util;

import com.dr.common.errorcode.BizCode;
import com.dr.common.exception.BizException;
import com.dr.common.log.LoggerUtil;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

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
public class SvnRepoUtil {

    /**
    * @date:2021/4/5
    * @className:SvnRepoUtil
    * @author:Administrator
    * @description: 获取svn代码仓
    *
    */
    public static SVNRepository cloneRepository(String repoUrl, String codePath, String userName, String password) throws SVNException {
        SVNWCUtil.createDefaultAuthenticationManager(new File(codePath), userName, password.toCharArray());
        SVNWCUtil.createDefaultOptions(true);
        return SVNRepositoryFactory.create(SVNURL.parseURIEncoded(repoUrl));
    }




}
