package com.wping.component.apisign.request;

import java.io.Serializable;

public class ApiBaseReqVo implements Serializable {

    private static final long serialVersionUID = -8613473027589962331L;
    // 机构代码
    private String appId;

    // 业务数据
    private String bizData;

    // 签名信息
    private String sign;

    // 时间戳 System.currentTimeMillis() 例如:1555312871228
    private Long timestamp;

    // 唯一流水号(少于32位)【防止重复提交】(备注：针对查询接口，流水号只用于日志落地，便于后期日志核查； 针对办理类接口需校验流水号在有效期内的唯一性，以避免重复请求)
    private String nonce;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getBizData() {
        return bizData;
    }

    public void setBizData(String bizData) {
        this.bizData = bizData;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }
}
