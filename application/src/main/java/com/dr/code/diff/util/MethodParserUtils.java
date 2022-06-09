package com.dr.code.diff.util;

import cn.hutool.crypto.SecureUtil;
import com.dr.code.diff.dto.MethodInfoResult;
import com.dr.common.errorcode.BizCode;
import com.dr.common.exception.BizException;
import com.dr.common.log.LoggerUtil;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ProjectName: base-service
 * @Package: com.dr.codediff.util
 * @Description: 解析获取类的方法
 * @Author: duanrui
 * @CreateDate: 2021/1/8 21:06
 * @Version: 1.0
 * <p>
 * Copyright: Copyright (c) 2021
 */
@Slf4j
public class MethodParserUtils {


    /**
     * 解析类获取类的所有方法
     *
     * @param classFile
     * @return
     */
    public static List<MethodInfoResult> parseMethods(String classFile) {
        List<MethodInfoResult> list = new ArrayList<>();
        try (FileInputStream in = new FileInputStream(classFile)) {
            JavaParser javaParser = new JavaParser();
            CompilationUnit cu = javaParser.parse(in).getResult().orElseThrow(() -> new BizException(BizCode.PARSE_JAVA_FILE));
            //由于jacoco不会统计接口覆盖率，没比较计算接口的方法，此处排除接口类
            final List<?> types = cu.getTypes();
            boolean isInterface = types.stream().filter(t -> t instanceof ClassOrInterfaceDeclaration).anyMatch(t -> ((ClassOrInterfaceDeclaration) t).isInterface());
            if (isInterface) {
                return list;
            }
            cu.accept(new MethodVisitor(), list);
            return list;
        } catch (IOException e) {
            LoggerUtil.error(log, "读取class类失败", e);
            throw new BizException(BizCode.PARSE_JAVA_FILE);
        }

    }

    /**
     * javaparser工具类核心方法，主要通过这个类遍历class文件的方法，此方法主要是获取出代码的所有方法，然后再去对比方法是否存在差异
     */
    private static class MethodVisitor extends VoidVisitorAdapter<List<MethodInfoResult>> {
        @Override
        public void visit(MethodDeclaration n, List<MethodInfoResult> list) {
            //删除注释
            n.removeComment();
            //计算方法体的hash值，疑问，空格，特殊转义字符会影响结果，导致相同匹配为差异？建议提交代码时统一工具格式化
            String md5 = SecureUtil.md5(n.toString());
            NodeList<Parameter> parameters = n.getParameters();
            List<String> params = parameters.stream().map(e -> e.getType().toString().trim()).collect(Collectors.toList());
            MethodInfoResult result = MethodInfoResult.builder()
                    .md5(md5)
                    .methodName(n.getNameAsString())
                    .parameters(params)
                    .build();
            list.add(result);
            super.visit(n, list);
        }

    }
}
