package com.wping.component.apisign.annotation;

import java.lang.annotation.*;

/**
 * 签名验证
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface SignVerify {

    //允许重复请求
    boolean resubmit() default true;

}
