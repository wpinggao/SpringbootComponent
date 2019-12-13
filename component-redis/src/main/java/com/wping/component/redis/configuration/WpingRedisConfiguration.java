package com.wping.component.redis.configuration;

import com.wping.component.redis.aspect.RedisDistributeLockAspect;
import com.wping.component.redis.properties.WpingRedisProperties;
import com.wping.component.redis.service.AccessRateLimiter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableConfigurationProperties({WpingRedisProperties.class})
public class WpingRedisConfiguration {

    @Autowired
    private WpingRedisProperties wpingRedisProperties;

    @Autowired
    private RedissonClient redissonClient;

    @Bean()
    public RedisDistributeLockAspect redisDistributeLockAspect() {

        return new RedisDistributeLockAspect(wpingRedisProperties, redissonClient);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new JdkSerializationRedisSerializer());
        template.setDefaultSerializer(new JdkSerializationRedisSerializer());
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean
    @ConditionalOnMissingBean
    public AccessRateLimiter accessRateLimiter(StringRedisTemplate redisTemplate) {
        return new AccessRateLimiter(redisTemplate, wpingRedisProperties);
    }


}
