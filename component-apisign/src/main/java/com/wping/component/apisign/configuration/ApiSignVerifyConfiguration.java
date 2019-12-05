package com.wping.component.apisign.configuration;

import com.wping.component.apisign.properties.ApiSignProperties;
import com.wping.component.apisign.service.SignVerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ApiSignProperties.class})
public class ApiSignVerifyConfiguration {

    @Autowired
    ApiSignProperties apiSignProperties;

    @Bean
    public SignVerifyService signVerifyService() {
        return new SignVerifyService(apiSignProperties);
    }



}
