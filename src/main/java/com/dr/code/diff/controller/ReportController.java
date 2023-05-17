package com.dr.code.diff.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dr.code.diff.analyze.DeduceApiService;
import com.dr.code.diff.analyze.bean.MethodInfo;
import com.dr.code.diff.common.response.ApiResponse;
import com.dr.code.diff.common.response.UniqueApoResponse;
import com.dr.code.diff.common.utils.mapper.OrikaMapperUtils;
import com.dr.code.diff.config.CustomizeConfig;
import com.dr.code.diff.dto.ApiModify;
import com.dr.code.diff.dto.DiffClassInfoResult;
import com.dr.code.diff.dto.DiffMethodParams;
import com.dr.code.diff.dto.MethodInvokeParam;
import com.dr.code.diff.enums.CodeManageTypeEnum;
import com.dr.code.diff.jacoco.bean.RemoteJacocoServer;
import com.dr.code.diff.jacoco.bean.ReportJacocoParam;
import com.dr.code.diff.jacoco.dump.DumpAction;
import com.dr.code.diff.jacoco.report.ReportAction;
import com.dr.code.diff.service.CodeDiffService;
import com.dr.code.diff.vo.param.RemoteJacocoServerParamVO;
import com.dr.code.diff.vo.param.ReportJacocoParamVO;
import com.dr.code.diff.vo.result.CodeDiffResultVO;
import com.dr.code.diff.vo.result.DeduceApiVO;
import com.dr.code.diff.vo.result.MethodInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author rui.duan
 * @version 1.0
 * @className UserController
 * @description 用户管理
 * @date 2019/11/20 6:25 下午
 */
@RestController
@Api(value = "/api/jacoco/action", tags = "jacoco报告服务")
@RequestMapping("/api/jacoco/action")
@Validated
public class ReportController {

    @Autowired
    private CodeDiffService codeDiffService;


    @Autowired
    private DumpAction dumpAction;


    @Autowired
    private ReportAction reportAction;

    @Autowired
    private CustomizeConfig customizeConfig;

    @ApiOperation("git获取差异代码生成json文件")
    @RequestMapping(value = "diff", method = RequestMethod.GET)
    public ApiResponse<String> getGitListToFile(
            @ApiParam(required = true, name = "gitUrl", value = "git远程仓库地址")
            @NotEmpty
            @RequestParam(value = "gitUrl") String gitUrl,
            @ApiParam(required = true, name = "baseVersion", value = "git原始分支或tag")
            @NotEmpty
            @RequestParam(value = "baseVersion") String baseVersion,
            @ApiParam(required = true, name = "nowVersion", value = "git现分支或tag")
            @NotEmpty
            @RequestParam(value = "nowVersion") String nowVersion,
            @ApiParam(required = true, name = "diffName", value = "生成差异代码名称a.json")
            @NotEmpty
            @RequestParam(value = "diffName") String diffName) {
        DiffMethodParams diffMethodParams = DiffMethodParams.builder()
                .repoUrl(StringUtils.trim(gitUrl))
                .baseVersion(StringUtils.trim(baseVersion))
                .nowVersion(StringUtils.trim(nowVersion))
                .codeManageTypeEnum(CodeManageTypeEnum.GIT)
                .build();
        List<DiffClassInfoResult> diffCodeList = codeDiffService.getDiffCode(diffMethodParams).getDiffClasses();
        List<CodeDiffResultVO> list = OrikaMapperUtils.mapList(diffCodeList, DiffClassInfoResult.class, CodeDiffResultVO.class);
        String diffCodePath = customizeConfig.getJacocoRootPath() + "/diff/" + diffName;
        if (!CollectionUtils.isEmpty(list)) {
            FileUtil.writeUtf8String(JSONUtil.toJsonStr(list), diffCodePath);
        }
        return new ApiResponse<String>().success(diffCodePath);
    }


    @ApiOperation("dump生成exec文件")
    @RequestMapping(value = "dump", method = RequestMethod.POST)
    public ApiResponse<List<String>> dump(
            @ApiParam(value = "远程服务器信息", required = true)
            @RequestBody List<RemoteJacocoServerParamVO> list) {
        List<RemoteJacocoServer> remoteJacocoServers = OrikaMapperUtils.mapList(list, RemoteJacocoServerParamVO.class, RemoteJacocoServer.class);
        List<String> execs = dumpAction.dumpExecs(remoteJacocoServers);
        return new ApiResponse<List<String>>().success(execs);
    }

    @ApiOperation("report生成html文件")
    @RequestMapping(value = "report", method = RequestMethod.POST)
    public ApiResponse response(
            @ApiParam(value = "生成报告参数", required = true)
            @RequestBody ReportJacocoParamVO reportJacocoParamVO) {
        ReportJacocoParam reportJacocoParam = OrikaMapperUtils.map(reportJacocoParamVO, ReportJacocoParam.class);
        reportAction.reportJacoco(reportJacocoParam);
        return new ApiResponse<>().success();
    }


}
