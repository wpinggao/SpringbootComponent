package com.wping.component.distributeid.configuration;

import com.wping.component.distributeid.service.SnowFlakeService;
import com.wping.component.distributeid.properties.SnowFlakeProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * describe:
 *
 * @author Wping.gao
 * @date 2018/12/06
 */
@Configuration
@EnableConfigurationProperties({SnowFlakeProperties.class})
@ConditionalOnProperty(prefix = SnowFlakeProperties.PREFIX, name = "enabled", havingValue = "true")
public class SnowFlakeConfiguration {

    @Autowired
    private SnowFlakeProperties snowFlakeProperties;

    @Bean
    public SnowFlakeService snowFlakeService() {
        return new SnowFlakeService(snowFlakeProperties.getWorkerId(), snowFlakeProperties.getDatacenterId());
    }
}
