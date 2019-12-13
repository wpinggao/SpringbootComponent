package com.wping.component.redis.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(WpingRedisProperties.PREFIX)
public class WpingRedisProperties {

    public static final String PREFIX = "wping.redis";

    // redis key 前缀，每个系统模块锁前缀不同，防止key键冲突
    private String prefix = "redis:";

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
