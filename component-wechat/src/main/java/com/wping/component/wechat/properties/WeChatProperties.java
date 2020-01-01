package com.wping.component.wechat.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(WeChatProperties.PREFIX)
public class WeChatProperties {

    public static final String PREFIX = "prototype.wechat";

    /**
     * appid
     */
    private String appid;

    /**
     * appsecret
     */
    private String appsecret;

    /**
     * redirectUrl
     */
    private String redirectUrl;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppsecret() {
        return appsecret;
    }

    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
