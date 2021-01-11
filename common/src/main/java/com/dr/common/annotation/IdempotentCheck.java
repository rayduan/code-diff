package com.dr.common.annotation;


import java.lang.annotation.*;

/**
 * @author rui.duan
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IdempotentCheck {
    /**
     * 需要等待的时间,单位s
     * @return int
     */
    int expireTime() default 3;
}
