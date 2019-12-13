package com.wping.component.shield.configuration;

import com.wping.component.shield.aspect.PermitFileCategoryAspect;
import com.wping.component.shield.aspect.PermitFileTypeAspect;
import com.wping.component.shield.filter.XssFilter;
import com.wping.component.shield.properties.ShieldProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * describe:
 *
 * @author Wping.gao
 * @date 2019/01/21
 */
@Configuration
@EnableConfigurationProperties({ShieldProperties.class})
public class ShieldConfiguration {

    @Autowired
    private ShieldProperties shieldProperties;

    @Bean
    public FilterRegistrationBean xssFilterRegistrationBean() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new XssFilter());
        filterRegistration.addUrlPatterns(shieldProperties.getXssFilterUrlPatterns().split(","));
        filterRegistration.setName("xssFilter");
        filterRegistration.setOrder(shieldProperties.getXssFilterOrder());
        return filterRegistration;
    }

    @Bean
    public PermitFileCategoryAspect permitFileCategoryAspect() {
        return new PermitFileCategoryAspect();
    }

    @Bean
    public PermitFileTypeAspect permitFileTypeAspect() {
        return new PermitFileTypeAspect();
    }
}
