package com.dr.code.diff.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.jgit.api.Git;

/**
 * @Package: com.dr.code.diff.dto
 * @Description: java类作用描述
 * @Author: rayduan
 * @CreateDate: 2023/3/28 18:30
 * @Version: 1.0
 * <p>
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GitInfoDto {
    /**
     * git
     */
    private Git git;


    /**
     * 本地代码基础路径
     */
    private String localBaseRepoDir;

}
