package com.dr.code.diff.jacoco.report;

import com.dr.code.diff.common.errorcode.BizCode;
import com.dr.code.diff.common.exception.BizException;
import com.dr.code.diff.common.log.LoggerUtil;
import com.dr.code.diff.jacoco.bean.ReportJacocoParam;
import lombok.extern.slf4j.Slf4j;
import org.jacoco.cli.internal.core.analysis.Analyzer;
import org.jacoco.cli.internal.core.analysis.CoverageBuilder;
import org.jacoco.cli.internal.core.analysis.IBundleCoverage;
import org.jacoco.cli.internal.core.internal.diff.JsonReadUtil;
import org.jacoco.cli.internal.core.tools.ExecFileLoader;
import org.jacoco.cli.internal.report.*;
import org.jacoco.cli.internal.report.html.HTMLFormatter;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @ProjectName: code-diff-parent
 * @Package: com.dr.code.diff.jacoco.report
 * @Description: java类作用描述
 * @Author: duanrui
 * @CreateDate: 2023/4/15 19:57
 * @Version: 1.0
 * <p>
 * Copyright: Copyright (c) 2023
 */
@Service
@Slf4j
public class ReportAction {

    public void reportJacoco(ReportJacocoParam reportJacocoParam) {
        //解析exec
        ExecFileLoader execFileLoader = loadExecutionData(reportJacocoParam.getExecutionDataFile());
        if (null == execFileLoader) {
            throw new BizException(BizCode.JACOCO_REPORT_FAIL);
        }
        //对比exec和class类，生成覆盖数据
        IBundleCoverage bundleCoverage = analyzeStructure(reportJacocoParam, execFileLoader);
        if (null == bundleCoverage) {
            throw new BizException(BizCode.JACOCO_REPORT_FAIL);
        }
        //生成报告
        createReport(bundleCoverage, execFileLoader, reportJacocoParam);
    }


    /**
     * 第一步解析exec文件
     */
    public ExecFileLoader loadExecutionData(List<String> executionDataFile) {
        if (CollectionUtils.isEmpty(executionDataFile)) {
            throw new BizException(BizCode.JACOCO_EXEC_NOT_EXIST);
        }
        // 解析exec
        ExecFileLoader execFileLoader = new ExecFileLoader();
        executionDataFile.forEach(e -> {
            try {
                execFileLoader.load(new File(e));
            } catch (IOException ex) {
                throw new BizException(BizCode.JACOCO_REPORT_FAIL.getCode(), ex.getMessage());
            }
        });
        return execFileLoader;
    }


    /**
     * 分析结构
     *
     * @param reportJacocoParam 报告jacoco参数
     * @param execFileLoader    exec文件加载器
     * @return {@link IBundleCoverage}
     */
    public IBundleCoverage analyzeStructure(ReportJacocoParam reportJacocoParam, ExecFileLoader execFileLoader) {
        try {

            CoverageBuilder builder;
            // 如果有增量参数将其设置进去
            if (null != reportJacocoParam.getDiffCodeFile()) {
                builder = new CoverageBuilder(
                        JsonReadUtil.readJsonToString(reportJacocoParam.getDiffCodeFile()), reportJacocoParam.getExcludedClassesDirectory());
            } else {
                builder = new CoverageBuilder(reportJacocoParam.getExcludedClassesDirectory());
            }
            final Analyzer analyzer = new Analyzer(execFileLoader.getExecutionDataStore(), builder);
            List<String> classesDirectory = reportJacocoParam.getClassesDirectory();
            // class类用于类方法的比较，源码只用于最后的着色
            for (String f : classesDirectory) {
                File file = new File(f);
                analyzer.analyzeAll(file);
            }
            return builder.getBundle(reportJacocoParam.getReportName());

        } catch (Exception e) {
            LoggerUtil.error(log, "解析class失败", e);
        }
        return null;
    }

    /**
     * 创建报告
     *
     * @param bundleCoverage    包覆盖
     * @param execFileLoader    exec文件加载器
     * @param reportJacocoParam 报告jacoco参数
     * @throws IOException ioexception
     */
    public void createReport(final IBundleCoverage bundleCoverage, ExecFileLoader execFileLoader, ReportJacocoParam reportJacocoParam) {
        try {
            // Create a concrete report visitor based on some supplied
            // configuration. In this case we use the defaults
            final HTMLFormatter htmlFormatter = new HTMLFormatter();
            final IReportVisitor visitor = htmlFormatter.createVisitor(new FileMultiReportOutput(new File(reportJacocoParam.getReportDirectory())));

            // Initialize the report with all of the execution and session
            // information. At this point the report doesn't know about the
            // structure of the report being created
            visitor.visitInfo(execFileLoader.getSessionInfoStore().getInfos(), execFileLoader.getExecutionDataStore().getContents());

            // Populate the report structure with the bundle coverage information.
            // Call visitGroup if you need groups in your report.
            visitor.visitBundle(bundleCoverage, getSourceLocator(reportJacocoParam.getSourceDirectory()));

            visitor.visitEnd();
        } catch (Exception e) {
            LoggerUtil.error(log, "创建html报告失败", e);
            throw new BizException(BizCode.JACOCO_REPORT_FAIL.getCode(), e.getMessage());
        }

    }


    /**
     * 得到源代码资源
     *
     * @param sourceList 源列表
     * @return {@link ISourceFileLocator}
     */
    private ISourceFileLocator getSourceLocator(List<String> sourceList) {
        final MultiSourceFileLocator multi = new MultiSourceFileLocator(
                4);
        for (String f : sourceList) {
            File file = new File(f);
            multi.add(new DirectorySourceFileLocator(file, "utf-8", 4));
        }
        return multi;
    }


}
