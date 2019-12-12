package com.wping.component.authorizer.configuration;

import com.wping.component.authorizer.aspect.RequiresPermissionsAspect;
import com.wping.component.authorizer.aspect.RequiresRolesAspect;
import com.wping.component.authorizer.properties.AuthorizerProperties;
import com.wping.component.authorizer.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({AuthorizerProperties.class})
public class AuthorizerConfiguration {

    @Autowired
    AuthorizerProperties authorizerProperties;

    @Bean()
    public RequiresRolesAspect requiresRolesAspect() {
        return new RequiresRolesAspect(authorizerProperties, authorizationService());
    }


    @Bean()
    public RequiresPermissionsAspect requiresPermissionsAspect() {
        return new RequiresPermissionsAspect(authorizerProperties, authorizationService());
    }

    @Bean()
    public AuthorizationService authorizationService() {
        return new AuthorizationService(authorizerProperties);
    }

}
