package com.wping.component.redis.aspect;

import com.wping.component.redis.annotation.RedisDistributeLock;
import com.wping.component.redis.annotation.RedisDistributeLockSuffix;
import com.wping.component.redis.constants.RedisKeyConstants;
import com.wping.component.redis.exception.RedisDistributeLockException;
import com.wping.component.redis.properties.WpingRedisProperties;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

/***
 * @description: redis分布式锁切面
 *
 * @author Xiaojie.li
 * @date 2019/3/13
 **/
@Aspect
public class RedisDistributeLockAspect {

    private static final Logger logger = LoggerFactory.getLogger(RedisDistributeLockAspect.class);

    private WpingRedisProperties wpingRedisProperties;
    private RedissonClient redissonClient;

    public RedisDistributeLockAspect(WpingRedisProperties wpingRedisProperties, RedissonClient redissonClient) {
        this.wpingRedisProperties = wpingRedisProperties;
        this.redissonClient = redissonClient;
    }

    @Around("@annotation(redisDistributeLock)")
    public Object lock(ProceedingJoinPoint point, RedisDistributeLock redisDistributeLock) throws Throwable {
        RLock lock = null;
        Object object = null;
        String fullLockKey = null;
        boolean isLocked = false;
        try {
            fullLockKey = getLockKey(point, redisDistributeLock.lockKey());
            lock = redissonClient.getLock(fullLockKey);
            if (lock != null) {
                isLocked = lock.tryLock(redisDistributeLock.waitTime(), redisDistributeLock.leaseTime(), TimeUnit.SECONDS);
                if (isLocked) {
                    logger.debug("获取锁成功, fullLockKey:{}", fullLockKey);
                    object = point.proceed();
                } else {
                    //logger.error("获取锁失败, fullLockKey:{}", fullLockKey);
                    throw new RedisDistributeLockException("获取锁失败, fullLockKey:" + fullLockKey);
                }
            } else {
                logger.error("获取RLock失败, lockKey:{}", redisDistributeLock.lockKey());
                throw new RedisDistributeLockException("获取RLock失败, lockKey:" + redisDistributeLock.lockKey());
            }
        } catch (Exception e) {
            if (e instanceof RedisDistributeLockException) {
                throw (RedisDistributeLockException) e;
            } else {
                logger.error("获取锁异常, fullLockKey:{}", fullLockKey, e);
                throw new RedisDistributeLockException("获取锁异常, fullLockKey:" + fullLockKey);
            }
        } finally {
            if (lock != null && isLocked && lock.isHeldByCurrentThread()) {
                lock.unlock();
                logger.debug("释放锁成功, fullLockKey:{}", fullLockKey);
            }
        }
        return object;
    }

    /**
     * 获取包括方法参数上的key
     * redis key的拼写规则为 @wpingRedisProperties.getPrefix() + "DLK:" + lockKey + ":" + @DistRedisLockKey
     */
    private String getLockKey(ProceedingJoinPoint point, String lockKey) {
        try {
            lockKey = wpingRedisProperties.getPrefix() + RedisKeyConstants.DISTRIBUTE_LOCK + lockKey + ":";
            Object[] args = point.getArgs();
            if (args != null && args.length > 0) {
                MethodSignature methodSignature = (MethodSignature) point.getSignature();
                Annotation[][] parameterAnnotations = methodSignature.getMethod().getParameterAnnotations();
                SortedMap<Integer, String> keys = new TreeMap<>();
                for (int i = 0; i < parameterAnnotations.length; i++) {
                    RedisDistributeLockSuffix redisLockKey = getAnnotation(RedisDistributeLockSuffix.class, parameterAnnotations[i]);
                    if (redisLockKey != null) {
                        Object arg = args[i];
                        if (arg != null) {
                            keys.put(redisLockKey.order(), arg.toString());
                        }
                    }
                }
                if (keys.size() > 0) {
                    StringBuilder lockKeyBuilder = new StringBuilder(lockKey);
                    for (String key : keys.values()) {
                        lockKeyBuilder.append(key);
                    }
                    lockKey = lockKeyBuilder.toString();
                }
            }

            return lockKey;
        } catch (Exception e) {
            logger.error("getLockKey error.", e);
        }
        return null;
    }

    /**
     * 获取注解类型
     */
    private static <T extends Annotation> T getAnnotation(final Class<T> annotationClass, final Annotation[] annotations) {
        if (annotations != null && annotations.length > 0) {
            for (final Annotation annotation : annotations) {
                if (annotationClass.equals(annotation.annotationType())) {
                    return (T) annotation;
                }
            }
        }
        return null;
    }

}
