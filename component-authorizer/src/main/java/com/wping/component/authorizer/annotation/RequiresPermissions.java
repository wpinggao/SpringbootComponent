package com.wping.component.authorizer.annotation;

import com.wping.component.authorizer.enums.Logical;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * describe: 用户权限注解
 *
 * @author Wping.gao
 * @date 2019/5/14 17:15
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface RequiresPermissions {

    String[] value();

    Logical logical() default Logical.AND;
}
