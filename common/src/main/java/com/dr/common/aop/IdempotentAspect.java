package com.dr.common.aop;


import com.dr.common.annotation.IdempotentCheck;
import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author rui.duan
 * @version 1.0
 * @className IdempotentAspect
 * @description 幂等校验切面
 * @date 2019-06-10 19:23
 */
@Aspect
@Component
public class IdempotentAspect {


//    private IUcsCache ucsCache;

    @Pointcut("@annotation(com.dr.common.annotation.IdempotentCheck)")
    public void idempotentCheck() {}

    @Before("idempotentCheck()")
    public void doBefore(JoinPoint joinPoint) {
        //拿到注解类的类名
        String targetName = joinPoint.getTarget().getClass().getName();
        //拿到注解类的方法名
        String methodName = joinPoint.getSignature().getName();
        //拿到注解类的参数
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = null;
        String args ="";
        try {
            targetClass = Class.forName(targetName);
            Method[] methods = targetClass.getMethods();
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    Class[] clazzs = method.getParameterTypes();
                    if (clazzs.length == arguments.length) {
                        if(null != arguments && arguments.length >0){
                            args =  ArrayUtils.toString(arguments, "-");
                        }
                        int expireTime = method.getAnnotation(IdempotentCheck.class).expireTime();
                        //拼接key，同一类的同一方法的相同参数3秒内不能重复调用
                        StringBuilder key  = new StringBuilder(targetName)
                                .append(methodName)
                                .append(args);
//                        if(ucsCache.containsKey(key.toString())){
//                            throw new ScmBizException(BaseCode.NOT_REPEAT_COMMIT);
//                        }else{
//                            //接口加锁
//                            ucsCache.set(key.toString(), AuthUtil.getUserInfo().getToken(),expireTime);
//                        }

                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过反射机制 获取被切参数名以及参数值
     *
     * @param cls
     * @param clazzName
     * @param methodName
     * @param args
     * @return
     * @throws NotFoundException
     */
    private Map<String, Object> getFieldsName(Class cls, String clazzName, String methodName, Object[] args) throws NotFoundException {
        Map<String, Object> map = new HashMap<String, Object>();

        ClassPool pool = ClassPool.getDefault();
        ClassClassPath classPath = new ClassClassPath(cls);
        pool.insertClassPath(classPath);

        CtClass cc = pool.get(clazzName);
        CtMethod cm = cc.getDeclaredMethod(methodName);
        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        if (attr == null) {
            // exception
        }
        // String[] paramNames = new String[cm.getParameterTypes().length];
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
        for (int i = 0; i < cm.getParameterTypes().length; i++) {
            //paramNames即参数名
            map.put(attr.variableName(i + pos), args[i]);
        }
        return map;
    }

}
