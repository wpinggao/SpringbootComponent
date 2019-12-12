package com.wping.component.authorizer.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(AuthorizerProperties.PREFIX)
public class AuthorizerProperties {

    public static final String PREFIX = "wping.authorizer";

    private boolean enabled = true;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
