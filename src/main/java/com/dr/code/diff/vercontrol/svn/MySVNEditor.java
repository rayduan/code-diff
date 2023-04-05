package com.dr.code.diff.vercontrol.svn;

import org.tmatesoft.svn.core.SVNCommitInfo;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNPropertyValue;
import org.tmatesoft.svn.core.io.ISVNEditor;
import org.tmatesoft.svn.core.io.diff.SVNDiffWindow;

import java.io.OutputStream;

/**
 * @ProjectName: code-diff-parent
 * @Package: com.dr.code.diff.vercontrol.svn
 * @Description: java类作用描述
 * @Author: duanrui
 * @CreateDate: 2021/4/5 19:06
 * @Version: 1.0
 * <p>
 * Copyright: Copyright (c) 2021
 */
public class MySVNEditor implements ISVNEditor {
    @Override
    public void targetRevision(long l) throws SVNException {

    }

    @Override
    public void openRoot(long l) throws SVNException {

    }

    @Override
    public void deleteEntry(String s, long l) throws SVNException {

    }

    @Override
    public void absentDir(String s) throws SVNException {

    }

    @Override
    public void absentFile(String s) throws SVNException {

    }

    @Override
    public void addDir(String s, String s1, long l) throws SVNException {

    }

    @Override
    public void openDir(String s, long l) throws SVNException {

    }

    @Override
    public void changeDirProperty(String s, SVNPropertyValue svnPropertyValue) throws SVNException {

    }

    @Override
    public void closeDir() throws SVNException {

    }

    @Override
    public void addFile(String s, String s1, long l) throws SVNException {

    }

    @Override
    public void openFile(String s, long l) throws SVNException {

    }

    @Override
    public void changeFileProperty(String s, String s1, SVNPropertyValue svnPropertyValue) throws SVNException {

    }

    @Override
    public void closeFile(String s, String s1) throws SVNException {

    }

    @Override
    public SVNCommitInfo closeEdit() throws SVNException {
        return null;
    }

    @Override
    public void abortEdit() throws SVNException {

    }

    @Override
    public void applyTextDelta(String s, String s1) throws SVNException {

    }

    @Override
    public OutputStream textDeltaChunk(String s, SVNDiffWindow svnDiffWindow) throws SVNException {
        return null;
    }

    @Override
    public void textDeltaEnd(String s) throws SVNException {

    }
}
