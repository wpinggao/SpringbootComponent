package com.wping.component.redis.annotation;

import java.lang.annotation.*;

/***
 * @description: redis 分布式锁后缀
 *
 **/
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RedisDistributeLockSuffix {

    /**
     * key的拼接顺序规则
     */
    int order() default 0;
}
