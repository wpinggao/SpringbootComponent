package com.wping.component.jwt.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * describe:
 *
 * @author Wping.gao
 * @date 2019/01/25
 */
@ConfigurationProperties(WpingJwtProperties.PREFIX)
public class WpingJwtProperties {

    public static final String PREFIX = "wping.jwt";

    // 秘钥(64位)
    private String secret = "yqmmeNj0Frf0vXf7eVhxFVzmfq2yBwqkXBmJUjG5gibvzwFSR9k2W8yz2T0YlC0d";

    // 时间有效期(秒为单位)
    private Integer expiryTime = 60 * 60 * 24 * 7;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Integer getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Integer expiryTime) {
        this.expiryTime = expiryTime;
    }
}