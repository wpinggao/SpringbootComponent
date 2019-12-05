package com.wping.component.apisign.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(ApiSignProperties.PREFIX)
public class ApiSignProperties {

    public static final String PREFIX = "wping.apisign";

    /**
     * SignVerifyAspect 切面开关 true:开启 false:关闭
     */
    private boolean enabled = true;
    /**
     * 是否开启验签开关 true:开启 false:关闭
     */
    private Boolean checkSignOpen = true;
    /**
     * 时间有效期(秒为单位)
     */
    private Integer secondsOfExpiryTime = 1800;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getCheckSignOpen() {
        return checkSignOpen;
    }

    public void setCheckSignOpen(Boolean checkSignOpen) {
        this.checkSignOpen = checkSignOpen;
    }

    public Integer getSecondsOfExpiryTime() {
        return secondsOfExpiryTime;
    }

    public void setSecondsOfExpiryTime(Integer secondsOfExpiryTime) {
        this.secondsOfExpiryTime = secondsOfExpiryTime;
    }
}
