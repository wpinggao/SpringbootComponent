package com.wping.component.redis.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;

@ConfigurationProperties(WpingRedisCacheProperties.PREFIX)
public class WpingRedisCacheProperties {

    public static final String PREFIX = "wping.redis-cache";

    private boolean enabled = false;
    // 默认缓存时间（默认60秒）
    private Duration defaultCacheTime = Duration.ofSeconds(60);

    private Map<String, CacheName> cacheNames = new LinkedHashMap<>();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Duration getDefaultCacheTime() {
        return defaultCacheTime;
    }

    public void setDefaultCacheTime(Duration defaultCacheTime) {
        this.defaultCacheTime = defaultCacheTime;
    }

    public Map<String, CacheName> getCacheNames() {
        return cacheNames;
    }

    public void setCacheNames(Map<String, CacheName> cacheNames) {
        this.cacheNames = cacheNames;
    }

    public static class CacheName {

        // cache名字
        private String name;
        // cache时间
        private Duration cacheTime;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Duration getCacheTime() {
            return cacheTime;
        }

        public void setCacheTime(Duration cacheTime) {
            this.cacheTime = cacheTime;
        }
    }
}
