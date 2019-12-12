package com.wping.component.base.configuration;

import com.wping.component.base.helper.ApplicationContextHelper;
import com.wping.component.base.helper.BeanFactoryHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * describe:
 *
 * @author Wping.gao
 * @date 2019/01/25
 */
@Configuration
public class BaseConfiguration {

    @Bean
    public static BeanFactoryHelper beanFactoryHelper() {
        return new BeanFactoryHelper();
    }

    @Bean
    public ApplicationContextHelper applicationContextHelper() {
        return new ApplicationContextHelper();
    }
}
