package com.wping.component.apisign.dto;

/**
 * 机构信息
 */
public class AppInfo {

    // 机构ID
    private String appId;

    // 机构名
    private String appName;

    // 机构秘钥
    private String appSecret;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
}