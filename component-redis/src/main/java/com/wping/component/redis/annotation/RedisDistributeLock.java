package com.wping.component.redis.annotation;

import java.lang.annotation.*;

/***
 * @description: redis 分布式锁注解
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RedisDistributeLock {

    /**
     * 锁的key
     * redis key的拼写规则为 @WpingRedisProperties.getPrefix() + "DLK:" + lockKey + ":" + @DistRedisLockKey
     */
    String lockKey();

    /**
     * 持锁时间
     * 单位秒,默认120秒<br/>
     */
    long leaseTime() default 120;

    /**
     * 没有获取到锁时，等待时间（单位秒-1代表30s）
     */
    long waitTime() default -1;
}
