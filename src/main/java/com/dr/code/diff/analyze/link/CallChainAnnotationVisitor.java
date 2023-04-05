package com.dr.code.diff.analyze.link;

import com.dr.code.diff.analyze.bean.RequestInfo;
import jdk.internal.org.objectweb.asm.AnnotationVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Package: com.dr.code.diff.analyze.link
 * @Description: 注解访问
 * @Author: rayduan
 * @CreateDate: 2023/2/23 21:21
 * @Version: 1.0
 * <p>
 */
public class CallChainAnnotationVisitor extends AnnotationVisitor {

    private final RequestInfo requestInfo;

    public CallChainAnnotationVisitor(AnnotationVisitor annotationVisitor, RequestInfo requestInfo) {
        super(Opcodes.ASM5, annotationVisitor);
        this.requestInfo = requestInfo;
    }


    /**
     * 访问数组,这里先访问注解里面的key值
     *
     * @param name 名字
     * @return {@link AnnotationVisitor}
     */
    @Override
    public AnnotationVisitor visitArray(String name) {
        // 主要获取requestMapping的信息
        if (name.equals("value") || name.equals("method")) {
            return new CallChainAnnotationVisitor(super.visitArray(name), this.requestInfo);
        }
        return super.visitArray(name);
    }

    /**
     * 再访问数组里的value
     *
     * @param name  名字
     * @param value 值
     */
    @Override
    public void visit(String name, Object value) {
        // 这里是url
        this.requestInfo.setRequestUrl(null == value ? "" : (String) value);
        super.visit(name, value);
    }

    /**
     * 如果注解里面的值是枚举类型从这里访问
     *
     * @param name       名字
     * @param descriptor 描述符
     * @param value      价值
     */
    public void visitEnum(final String name, final String descriptor, final String value) {
        //这里是请求方法
        this.requestInfo.setMappingMethod(RequestMethod.valueOf(value));
        if (av != null) {
            av.visitEnum(name, descriptor, value);
        }
    }


}
