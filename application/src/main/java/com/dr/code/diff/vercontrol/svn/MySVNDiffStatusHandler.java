package com.dr.code.diff.vercontrol.svn;

import com.dr.code.diff.dto.DiffEntryDto;
import org.eclipse.jgit.diff.DiffEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.wc.ISVNDiffStatusHandler;
import org.tmatesoft.svn.core.wc.SVNDiffStatus;
import org.tmatesoft.svn.core.wc.SVNStatusType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @ProjectName: code-diff-parent
 * @Package: com.dr.code.diff.vercontrol
 * @Description: java类作用描述
 * @Author: duanrui
 * @CreateDate: 2021/4/5 18:06
 * @Version: 1.0
 * <p>
 * Copyright: Copyright (c) 2021
 */
public class MySVNDiffStatusHandler implements ISVNDiffStatusHandler {

    public  final static  List<DiffEntryDto> list = Collections.synchronizedList(new ArrayList<DiffEntryDto>());;


    @Override
    public void handleDiffStatus(SVNDiffStatus svnDiffStatus) throws SVNException {
        //首先过滤java文件
        if(!svnDiffStatus.getPath().endsWith(".java")){
            return;
        }
        //过滤测试文件
        if(!svnDiffStatus.getPath().contains("src/main/java")){
            return;
        }
        DiffEntryDto entry = new DiffEntryDto();
        //只计算变更和新增文件
        if(SVNStatusType.STATUS_MODIFIED.equals(svnDiffStatus.getModificationType())){
            entry.setChangeType(DiffEntry.ChangeType.MODIFY);
        }else if(SVNStatusType.STATUS_ADDED.equals(svnDiffStatus.getModificationType())){
            entry.setChangeType(DiffEntry.ChangeType.ADD);
        }else{
            return;
        }
        entry.setNewPath(svnDiffStatus.getPath());
        list.add(entry);
    }
}
