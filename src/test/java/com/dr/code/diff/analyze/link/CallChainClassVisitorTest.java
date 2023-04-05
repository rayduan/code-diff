package com.dr.code.diff.analyze.link;

import com.dr.code.diff.CodeDiffApplicationTest;
import com.dr.code.diff.analyze.bean.MethodInfo;
import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassWriter;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * @Package: com.dr.code.diff.analyze.link
 * @Description: java类作用描述
 * @Author: rayduan
 * @CreateDate: 2023/2/24 14:53
 * @Version: 1.0
 * <p>
 */
class CallChainClassVisitorTest extends CodeDiffApplicationTest {

    @Test
    void visitMethod() throws IOException {
        String sourceFilePath = "/Users/rayduan/IdeaProjects/code-diff/application/target/classes/com/dr/code/diff/analyze/InvokeLinkBuildService.class";
        File fileReader = new File(sourceFilePath);
        ClassReader cr = new ClassReader(Files.newInputStream(fileReader.toPath()));
        List<MethodInfo> list = new ArrayList<>();
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        CallChainClassVisitor cv = new CallChainClassVisitor(cw, list, null);
        cr.accept(cv, ClassReader.SKIP_FRAMES);
        System.out.println(list);
    }

    @Test
    void visitMethod2() throws IOException {
        String sourceFilePath = "/Users/rayduan/IdeaProjects/code-diff/application/target/classes/com/dr/code/diff/util/MethodParserUtils.class";
        File fileReader = new File(sourceFilePath);
        ClassReader cr = new ClassReader(Files.newInputStream(fileReader.toPath()));
        List<MethodInfo> list = new ArrayList<>();
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        CallChainClassVisitor cv = new CallChainClassVisitor(cw, list, null);
        cr.accept(cv, ClassReader.SKIP_FRAMES);
        System.out.println(list);
    }

    @Test
    void visitMethod3() throws IOException {
        String sourceFilePath = "/Users/rayduan/IdeaProjects/relation-demo/target/classes/com/dr/test/MyClass.class";
        File fileReader = new File(sourceFilePath);
        ClassReader cr = new ClassReader(Files.newInputStream(fileReader.toPath()));
        List<MethodInfo> list = new ArrayList<>();
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        CallChainClassVisitor cv = new CallChainClassVisitor(cw, list, null);
        cr.accept(cv, ClassReader.SKIP_FRAMES);
        System.out.println(list);
    }

}