package com.wping.component.jwt.configuration;

import com.wping.component.jwt.properties.WpingJwtProperties;
import com.wping.component.jwt.service.WpingJwtService;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.UnsupportedEncodingException;

/**
 * describe:
 *
 * @author Wping.gao
 * @date 2019/01/25
 */
@Configuration
@EnableConfigurationProperties({WpingJwtProperties.class})
public class WpingJwtConfiguration {

    @Autowired
    private WpingJwtProperties wpingJwtProperties;

    @Bean
    public WpingJwtService wpingJwtService() {
        try {
            return new WpingJwtService(wpingJwtProperties, Keys.hmacShaKeyFor(wpingJwtProperties.getSecret().getBytes("UTF-8")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
