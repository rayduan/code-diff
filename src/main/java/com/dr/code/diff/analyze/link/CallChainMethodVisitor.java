package com.dr.code.diff.analyze.link;

import com.dr.code.diff.analyze.bean.AdapterContext;
import com.dr.code.diff.analyze.bean.ClassInfo;
import com.dr.code.diff.analyze.bean.MethodInfo;
import com.dr.code.diff.analyze.bean.RequestInfo;
import com.dr.code.diff.analyze.constant.AnnotationConstant;
import com.dr.code.diff.analyze.strategy.MethodFilterContext;
import com.dr.code.diff.analyze.strategy.MethodInvokeFactory;
import com.dr.code.diff.enums.LinkScopeTypeEnum;
import com.dr.code.diff.enums.MethodNodeTypeEnum;
import com.dr.code.diff.util.StringUtil;
import jdk.internal.org.objectweb.asm.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @Package: com.dr.code.diff.analyze.link
 * @Description: 遍历方法获取其调用关系
 * @Author: rayduan
 * @CreateDate: 2023/2/20 17:11
 * @Version: 1.0
 * <p>
 */
public class CallChainMethodVisitor extends MethodVisitor {

    public static final String JAVA = "java";
    /**
     * 调用方法
     */
    private final List<MethodInfo> callerMethods;

    /**
     * 当前方法
     */
    private final MethodInfo currentMethod;


    /**
     * 请求信息
     */
    private RequestInfo requestInfo;


    /**
     * 适配器上下文
     */
    private final AdapterContext adapterContext;

    public CallChainMethodVisitor(MethodVisitor mv, MethodInfo currentMethod, List<MethodInfo> callerMethods, AdapterContext adapterContext) {
        super(Opcodes.ASM5, mv);
        this.callerMethods = callerMethods;
        this.currentMethod = currentMethod;
        this.adapterContext = adapterContext;
    }

    public void visitParameter(String name, int access) {
        List<String> methodParamNameList = currentMethod.getMethodParamNameList();
        if (CollectionUtils.isEmpty(methodParamNameList)) {
            methodParamNameList = new ArrayList<>();
            currentMethod.setMethodParamNameList(methodParamNameList);
        }
        methodParamNameList.add(name);
        super.visitParameter(name, access);
    }


    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        // 跳过初始化方法
        if (name.equals("<init>") || name.equals("<clinit>")) {
            // Ignore constructor and class initializer
            return;
        }
        //非本包调用过滤
        MethodFilterContext methodFilterContext = adapterContext.getMethodFilterContext();
        if (null != methodFilterContext) {
            if (LinkScopeTypeEnum.GROUP_ONLY_TYPE.equals(methodFilterContext.getLinKScopeTypeEnum())) {
                if (!owner.startsWith(methodFilterContext.getBaseClassName())) {
                    return;
                }
            } else if (LinkScopeTypeEnum.EXCLUDE_JDK_TYPE.equals(methodFilterContext.getLinKScopeTypeEnum())) {
                if (owner.contains(methodFilterContext.getBaseClassName())) {
                    return;
                }
            }

        }

        // 记录被调用方法的信息
        buildInvokeMethod(owner, name, desc);
        super.visitMethodInsn(opcode, owner, name, desc, itf);
    }


    public void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
        // 检查 bootstrapMethodHandle 中的 method descriptor 是否包含 "java/lang/invoke/LambdaMetafactory.metafactory"，
        // 如果是，则说明当前的 invoke dynamic 指令为 Lambda 表达式
        if (bootstrapMethodHandle.getTag() == Opcodes.H_INVOKESTATIC && bootstrapMethodHandle.getOwner().equals("java/lang/invoke/LambdaMetafactory") && bootstrapMethodHandle.getName().equals("metafactory")) {
            // 获取 Lambda 表达式所绑定的方法
            Handle methodHandle = (Handle) bootstrapMethodArguments[1];
            String owner = methodHandle.getOwner();
            String methodName = methodHandle.getName();
            String methodDescriptor = methodHandle.getDesc();
            // 记录被调用方法的信息
            buildInvokeMethod(owner, methodName, methodDescriptor);
        }
        super.visitInvokeDynamicInsn(name, descriptor, bootstrapMethodHandle, bootstrapMethodArguments);

    }

    /**
     * 构建调用方法
     *
     * @param owner            老板
     * @param methodName       方法名称
     * @param methodDescriptor 方法描述符
     */
    private void buildInvokeMethod(String owner, String methodName, String methodDescriptor) {
        ClassInfo classInfo = ClassInfo.builder().className(owner).build();
        Type[] argumentTypes = Type.getArgumentTypes(methodDescriptor);
        List<String> params = Arrays.stream(argumentTypes).map(Type::getClassName)
                .map(e -> StringUtil.getSplitLast(e, ".")).collect(Collectors.toList());
        String methodSign = owner + "#" + methodName + "#" + String.join(",", params);
        MethodInfo methodCaller = MethodInfo.builder()
                .classInfo(classInfo)
                .methodName(methodName)
                .methodParams(params)
                .methodSign(methodSign)
                .abstractFlag(Boolean.FALSE)
                .build();
        this.callerMethods.add(methodCaller);
    }

    /**
     * 访问注释
     *
     * @param descriptor 描述符
     * @param visible    有形
     * @return {@link AnnotationVisitor}
     */
    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        //说明是request接口方法
        if (Arrays.asList(AnnotationConstant.requestAnnotation).contains(descriptor)) {
            //说明方法是http接口方法
            this.currentMethod.setMethodNodeTypeEnum(MethodNodeTypeEnum.HTTP);
            // 这里去获取类的 requestMappingValue
            this.requestInfo = new RequestInfo();
            if (AnnotationConstant.requestAnnotationMap.containsKey(descriptor)) {
                this.requestInfo.setMappingMethod(AnnotationConstant.requestAnnotationMap.get(descriptor));
            }
            return new CallChainAnnotationVisitor(super.visitAnnotation(descriptor, visible), requestInfo);
        }
        return super.visitAnnotation(descriptor, visible);
    }

    /**
     * 访问结束时调用
     */
    @Override
    public void visitEnd() {
        if (null != this.requestInfo) {
            this.currentMethod.setMappingMethod(this.requestInfo.getMappingMethod());
            this.currentMethod.setMappingUrl(this.requestInfo.getRequestUrl());
        }
        //这里记录调用的方法
        this.currentMethod.setCallerMethods(this.callerMethods);
        super.visitEnd();
    }


    /**
     * 访问方法调用时抛出异常捕获
     *
     * @param a 一个
     * @param b b
     */
    @Override
    public void visitMaxs(int a, int b) {
        try {
            super.visitMaxs(a, b);
        } catch (Exception e) {
            return;
        }
    }
}
