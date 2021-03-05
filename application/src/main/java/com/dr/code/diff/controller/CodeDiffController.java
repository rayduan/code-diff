package com.dr.code.diff.controller;

import com.alibaba.fastjson.JSONArray;
import com.dr.code.diff.dto.ClassInfoResult;
import com.dr.code.diff.dto.DiffMethodParams;
import com.dr.code.diff.service.CodeDiffService;
import com.dr.code.diff.vo.result.CodeDiffResultVO;
import com.dr.common.response.UniqueApoResponse;
import com.dr.common.utils.mapper.OrikaMapperUtils;
import com.dr.common.utils.string.BaseStringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
public class CodeDiffController {

    @Autowired
    private CodeDiffService codeDiffService;

    @ApiOperation("获取差异代码")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public UniqueApoResponse<List<CodeDiffResultVO>> getList(
            @ApiParam(required = true, name = "gitUrl", value = "git远程仓库地址")
            @RequestParam(value = "gitUrl") String gitUrl,
            @ApiParam(required = true, name = "baseVersion", value = "git原始分支或tag")
            @RequestParam(value = "baseVersion") String baseVersion,
            @ApiParam(required = true, name = "nowVersion", value = "git现分支或tag")
            @RequestParam(value = "nowVersion") String nowVersion) {
        DiffMethodParams diffMethodParams = DiffMethodParams.builder()
                .gitUrl(gitUrl)
                .baseVersion(baseVersion)
                .nowVersion(nowVersion)
                .build();
        List<ClassInfoResult> diffCodeList = codeDiffService.getDiffCode(diffMethodParams);
        List<CodeDiffResultVO> codeDiffResultVOS = OrikaMapperUtils.mapList(diffCodeList, ClassInfoResult.class, CodeDiffResultVO.class);
        return new UniqueApoResponse<List<CodeDiffResultVO>>().success(codeDiffResultVOS, BaseStringUtil.escapeChar(JSONArray.toJSON(codeDiffResultVOS).toString()));
    }


}
