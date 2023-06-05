package com.dr.code.diff.analyze.link;

import com.dr.code.diff.analyze.bean.AdapterContext;
import com.dr.code.diff.analyze.bean.ClassInfo;
import com.dr.code.diff.analyze.bean.MethodInfo;
import com.dr.code.diff.analyze.bean.RequestInfo;
import com.dr.code.diff.analyze.constant.AnnotationConstant;
import com.dr.code.diff.analyze.constant.SysConstant;
import com.dr.code.diff.enums.MethodNodeTypeEnum;
import com.dr.code.diff.util.StringUtil;
import jdk.internal.org.objectweb.asm.*;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Package: com.dr.code.diff.analyze.link
 * @Description: 遍历class文件
 * @Author: rayduan
 * @CreateDate: 2023/2/20 17:11
 * @Version: 1.0
 * <p>
 */
public class CallChainClassVisitor extends ClassVisitor {


    public static final String JAVA_LANG_OBJECT = "java/lang/Object";

    /**
     * 类信息
     */
    private ClassInfo classInfo;

    /**
     * 类中的方法
     */
    private final List<MethodInfo> methods;


    /**
     * 请求信息
     */
    private RequestInfo requestInfo;


    /**
     * 适配器上下文
     */
    private final AdapterContext adapterContext;


    /**
     * 调用链访问者构造函数
     *
     * @param cv 类访问者
     */
    public CallChainClassVisitor(ClassVisitor cv, List<MethodInfo> list, AdapterContext adapterContext) {
        super(Opcodes.ASM5, cv);
        this.methods = list;
        this.adapterContext = adapterContext;
    }

    /**
     * 访问类
     *
     * @param version    版本
     * @param access     访问
     * @param name       类全限定名
     * @param signature  签名
     * @param superName  超类
     * @param interfaces 接口
     */
    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        ClassInfo.ClassInfoBuilder classInfoBuilder = ClassInfo.builder()
                .className(name)
                .controllerFlag(Boolean.FALSE)
                .abstractFlag(Boolean.FALSE)
                .interfaceFlag(Boolean.FALSE)
                .feignFlag(Boolean.FALSE)
                .dubboFlag(Boolean.FALSE);
        // 如果是interface
        if ((access & Opcodes.ACC_INTERFACE) == Opcodes.ACC_INTERFACE) {
            classInfoBuilder.interfaceFlag(Boolean.TRUE);
        }
        // 如果是abstract类
        if ((access & Opcodes.ACC_ABSTRACT) == Opcodes.ACC_ABSTRACT) {
            classInfoBuilder.abstractFlag(Boolean.TRUE);
        }
        if(!JAVA_LANG_OBJECT.equals(superName)){
            //说明是抽象类的实现类
            classInfoBuilder.superClassName(superName);
        }
        if (null != interfaces && interfaces.length > 0) {
            classInfoBuilder.interfacesClassNames(new ArrayList<>(Arrays.asList(interfaces)));
        }
        if (null != adapterContext && !CollectionUtils.isEmpty(adapterContext.getDubboClasses())) {
            if (adapterContext.getDubboClasses().contains(name)) {
                classInfoBuilder.dubboFlag(Boolean.TRUE);
            }
        }
        this.classInfo = classInfoBuilder.build();
        super.visit(version, access, name, signature, superName, interfaces);

    }


    /**
     * 访问方法
     *
     * @param access     访问
     * @param name       名字
     * @param descriptor 描述符
     * @param signature  签名
     * @param exceptions 异常
     * @return {@link MethodVisitor}
     */
    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor,
                                     String signature, String[] exceptions) {
        // 跳过初始化方法
        if (name.equals("<init>") || name.equals("<clinit>")) {
            // Ignore constructor and class initializer
            return null;
        }
        Type[] argumentTypes = Type.getArgumentTypes(descriptor);
        List<String> params = Arrays.stream(argumentTypes).map(Type::getClassName)
                .map(e -> StringUtil.getSplitLast(e, ".")).collect(Collectors.toList());
        String methodSign = classInfo.getClassName() + SysConstant.SPILT_CHAR + name + SysConstant.SPILT_CHAR + String.join(",", params);
        MethodInfo.MethodInfoBuilder builder = MethodInfo
                .builder()
                .methodName(name)
                .methodParams(params)
                .methodSign(methodSign)
                .classInfo(this.classInfo)
                .abstractFlag(Boolean.FALSE);
        // 如果是interface,这里也标记方法为无法直接调用的方法
        if ((access & Opcodes.ACC_INTERFACE) == Opcodes.ACC_INTERFACE) {
            builder.abstractFlag(Boolean.TRUE);
        }
        // 如果是abstract类
        if ((access & Opcodes.ACC_ABSTRACT) == Opcodes.ACC_ABSTRACT) {
            builder.abstractFlag(Boolean.TRUE);
        }
        //如果是dubbo的接口类
        if (this.classInfo.getDubboFlag()) {
            builder.methodNodeTypeEnum(MethodNodeTypeEnum.DUBBO);
        }
        MethodInfo currentMethod = builder.build();
        List<MethodInfo> callerMethods = new ArrayList<>();
        this.methods.add(currentMethod);
        return new CallChainMethodVisitor(super.visitMethod(access, name, descriptor, signature, exceptions), currentMethod, callerMethods, adapterContext);
    }


    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        //说明是controller层
        if (Arrays.asList(AnnotationConstant.controllerAnnotation).contains(descriptor)) {
            this.classInfo.setControllerFlag(Boolean.TRUE);
        }
        if (Arrays.asList(AnnotationConstant.feignAnnotation).contains(descriptor)) {
            this.classInfo.setFeignFlag(Boolean.TRUE);
        }
        if (Arrays.asList(AnnotationConstant.dubboAnnotation).contains(descriptor)) {
            this.classInfo.setDubboFlag(Boolean.TRUE);
        }
        //只有controller的注解才能生效
        if (this.classInfo.getControllerFlag() || this.classInfo.getFeignFlag()) {
            if (Arrays.asList(AnnotationConstant.requestAnnotation).contains(descriptor)) {
                this.requestInfo = new RequestInfo();
                // 这里去获取类的 requestMappingValue
                return new CallChainAnnotationVisitor(super.visitAnnotation(descriptor, visible), this.requestInfo);
            }
        }

        return super.visitAnnotation(descriptor, visible);
    }

    @Override
    public void visitEnd() {
        if (null != this.requestInfo) {
            this.classInfo.setRequestUrl(null == this.requestInfo.getRequestUrl() ? "" : this.requestInfo.getRequestUrl());
        }
        super.visitEnd();
    }


}
