package com.wping.component.apisign.configuration;

import com.wping.component.apisign.aspect.SignVerifyAspect;
import com.wping.component.apisign.properties.ApiSignProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ApiSignProperties.class})
@ConditionalOnProperty(prefix = ApiSignProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
public class ApiSignConfiguration {

    @Autowired
    ApiSignProperties apiSignProperties;

    @Bean
    public SignVerifyAspect signVerifyAspect() {
        return new SignVerifyAspect(apiSignProperties);
    }

}
