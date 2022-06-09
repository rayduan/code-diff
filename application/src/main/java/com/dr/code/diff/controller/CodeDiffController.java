package com.dr.code.diff.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dr.code.diff.dto.ClassInfoResult;
import com.dr.code.diff.dto.DiffMethodParams;
import com.dr.code.diff.enums.CodeManageTypeEnum;
import com.dr.code.diff.service.CodeDiffService;
import com.dr.code.diff.vo.result.CodeDiffResultVO;
import com.dr.common.response.UniqueApoResponse;
import com.dr.common.utils.mapper.OrikaMapperUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author rui.duan
 * @version 1.0
 * @className UserController
 * @description 用户管理
 * @date 2019/11/20 6:25 下午
 */
@RestController
@Api(value = "/api/code/diff", tags = "差异代码模块")
@RequestMapping("/api/code/diff")
@Validated
public class CodeDiffController {

    @Autowired
    private CodeDiffService codeDiffService;

    @ApiOperation("git获取差异代码")
    @RequestMapping(value = "git/list", method = RequestMethod.GET)
    public UniqueApoResponse<List<CodeDiffResultVO>> getGitList(
            @ApiParam(required = true, name = "gitUrl", value = "git远程仓库地址")
            @NotEmpty
            @RequestParam(value = "gitUrl") String gitUrl,
            @ApiParam(required = true, name = "baseVersion", value = "git原始分支或tag")
            @NotEmpty
            @RequestParam(value = "baseVersion") String baseVersion,
            @ApiParam(required = true, name = "nowVersion", value = "git现分支或tag")
            @NotEmpty
            @RequestParam(value = "nowVersion") String nowVersion) {
        DiffMethodParams diffMethodParams = DiffMethodParams.builder()
                .repoUrl(StringUtils.trim(gitUrl))
                .baseVersion(StringUtils.trim(baseVersion))
                .nowVersion(StringUtils.trim(nowVersion))
                .codeManageTypeEnum(CodeManageTypeEnum.GIT)
                .build();
        List<ClassInfoResult> diffCodeList = codeDiffService.getDiffCode(diffMethodParams);
        List<CodeDiffResultVO> list = OrikaMapperUtils.mapList(diffCodeList, ClassInfoResult.class, CodeDiffResultVO.class);
        return new UniqueApoResponse<List<CodeDiffResultVO>>().success(list, JSON.toJSONString(list,SerializerFeature.WriteNullListAsEmpty));
    }


    @ApiOperation("svn同分支获取差异代码")
    @RequestMapping(value = "svn/list", method = RequestMethod.GET)
    public UniqueApoResponse<List<CodeDiffResultVO>> getSvnList(
            @NotEmpty
            @ApiParam(required = true, name = "svnUrl", value = "svn远程仓库地址,如svn:192.168.0.1:3690/svn")
            @RequestParam(value = "svnUrl") String svnUrl,
            @NotEmpty
            @ApiParam(required = true, name = "baseVersion", value = "svn原始分支,如：1")
            @RequestParam(value = "baseVersion") String baseVersion,
            @NotEmpty
            @ApiParam(required = true, name = "nowVersion", value = "svn现分支，如：2")
            @RequestParam(value = "nowVersion") String nowVersion) {
        DiffMethodParams diffMethodParams = DiffMethodParams.builder()
                .repoUrl(StringUtils.trim(svnUrl))
                .baseVersion(StringUtils.trim(baseVersion))
                .nowVersion(StringUtils.trim(nowVersion))
                .codeManageTypeEnum(CodeManageTypeEnum.SVN)
                .build();
        List<ClassInfoResult> diffCodeList = codeDiffService.getDiffCode(diffMethodParams);
        List<CodeDiffResultVO> list = OrikaMapperUtils.mapList(diffCodeList, ClassInfoResult.class, CodeDiffResultVO.class);
        return new UniqueApoResponse<List<CodeDiffResultVO>>().success(list, JSON.toJSONString(list,SerializerFeature.WriteNullListAsEmpty));
    }

    @ApiOperation("svn不同分支获取差异代码")
    @RequestMapping(value = "svn/branch/list", method = RequestMethod.GET)
    public UniqueApoResponse<List<CodeDiffResultVO>> getSvnBranchList(
            @ApiParam(required = true, name = "baseSvnUrl", value = "svn原始分支远程仓库地址,如svn:192.168.0.1:3690/svn/truck")
            @NotEmpty
            @RequestParam(value = "baseSvnUrl") String baseSvnUrl,
            @ApiParam(required = true, name = "nowSvnUrl", value = "svn现分支远程仓库地址,如svn:192.168.0.1:3690/svn/feature")
            @NotEmpty
            @RequestParam(value = "nowSvnUrl") String nowSvnUrl
    ) {
        DiffMethodParams diffMethodParams = DiffMethodParams.builder()
                .repoUrl(baseSvnUrl)
                .svnRepoUrl(nowSvnUrl)
                .codeManageTypeEnum(CodeManageTypeEnum.SVN)
                .build();
        List<ClassInfoResult> diffCodeList = codeDiffService.getDiffCode(diffMethodParams);
        List<CodeDiffResultVO> list = OrikaMapperUtils.mapList(diffCodeList, ClassInfoResult.class, CodeDiffResultVO.class);
        return new UniqueApoResponse<List<CodeDiffResultVO>>().success(list, JSON.toJSONString(list,SerializerFeature.WriteNullListAsEmpty));
    }


    @ApiOperation("svn不同分支不同reversion获取差异代码")
    @RequestMapping(value = "svn/branch/reversion/list", method = RequestMethod.GET)
    public UniqueApoResponse<List<CodeDiffResultVO>> getSvnBranchReversionList(
            @NotEmpty
            @ApiParam(required = true, name = "baseSvnUrl", value = "svn原始分支远程仓库地址,如svn:192.168.0.1:3690/svn/truck")
            @RequestParam(value = "baseSvnUrl") String baseSvnUrl,
            @ApiParam(required = true, name = "baseVersion", value = "svn原始分支,如：1")
            @NotEmpty
            @RequestParam(value = "baseVersion") String baseVersion,
            @ApiParam(required = true, name = "nowSvnUrl", value = "svn现分支远程仓库地址,如svn:192.168.0.1:3690/svn/feature")
            @NotEmpty
            @RequestParam(value = "nowSvnUrl") String nowSvnUrl,
            @ApiParam(required = true, name = "nowVersion", value = "svn现分支，如：2")
            @NotEmpty
            @RequestParam(value = "nowVersion") String nowVersion
    ) {
        DiffMethodParams diffMethodParams = DiffMethodParams.builder()
                .repoUrl(baseSvnUrl)
                .svnRepoUrl(nowSvnUrl)
                .baseVersion(baseVersion)
                .nowVersion(nowVersion)
                .codeManageTypeEnum(CodeManageTypeEnum.SVN)
                .build();
        List<ClassInfoResult> diffCodeList = codeDiffService.getDiffCode(diffMethodParams);
        List<CodeDiffResultVO> list = OrikaMapperUtils.mapList(diffCodeList, ClassInfoResult.class, CodeDiffResultVO.class);
        return new UniqueApoResponse<List<CodeDiffResultVO>>().success(list, JSON.toJSONString(list,SerializerFeature.WriteNullListAsEmpty));
    }


}
