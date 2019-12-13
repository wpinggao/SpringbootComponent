package com.wping.component.redis.service;

import com.wping.component.redis.properties.WpingRedisProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 限流控制类
 */
public class AccessRateLimiter {

    private static final Logger logger = LoggerFactory.getLogger(AccessRateLimiter.class);

    private final RedisTemplate<String, String> redisTemplate;
    private WpingRedisProperties wpingRedisProperties;

    public AccessRateLimiter(RedisTemplate<String, String> redisTemplate, WpingRedisProperties wpingRedisProperties) {
        this.redisTemplate = redisTemplate;
        this.wpingRedisProperties = wpingRedisProperties;
    }

    public enum Unit {
        YEAR,
        MONTH,
        WEEK,
        DAY,
        HOUR,
        MINUTE,
        SECOND
    }

    /**
     * 单位时间内可访问次数控制，返回是否可以访问
     * @param bizType 业务类型
     * @param bizKey 业务key
     * @param duration 周期
     * @param unit 周期单位
     * @param count 单位时间内可访问次数
     * @return ture: 可以访问 false:不可以访问
     */
    public boolean isAccess(String bizType, String bizKey, int duration, Unit unit, int count) {
        StringBuilder builder = new StringBuilder(wpingRedisProperties.getPrefix());
        builder.append(bizType);
        builder.append(":");
        builder.append(unit.name().toLowerCase());
        builder.append(":");
        builder.append(bizKey);
        String key = builder.toString();
        long durationSeconds = 1;
        switch (unit) {
            case YEAR:
                durationSeconds = duration * 356 * 24 * 60 * 60;
                break;
            case MONTH:
                durationSeconds = duration * 30 * 24 * 60 * 60;
                break;
            case WEEK:
                durationSeconds = duration * 7 * 24 * 60 * 60;
                break;
            case DAY:
                durationSeconds = duration * 24 * 60 * 60;
                break;
            case HOUR:
                durationSeconds = duration * 60 * 60;
                break;
            case MINUTE:
                durationSeconds = duration * 60;
                break;
            case SECOND:
                durationSeconds = duration;
                break;
            default:
                break;
        }
        Long total = redisTemplate.opsForValue().increment(key, 1L);
        if (total == 1) {
            redisTemplate.expire(key, durationSeconds, java.util.concurrent.TimeUnit.SECONDS);
        }
        return count < total;
    }

}
