package com.wping.component.apisign.service;


import com.wping.component.apisign.dto.AppInfo;

public interface ApiSignService {

    /**
     * 通过appId获取ApiSign信息
     *
     * @param appId
     * @return
     */
    AppInfo loadApiSignByAppId(String appId);

    /**
     * 校验存储中(redis、db、ehcache等)appId + nonce是否存在，存在返回true, 不存在则设置缓存以及过期时间，返回false
     *
     * @param nonce
     * @param secondsOfExpiryTime
     * @return
     */
    Boolean checkExistOrSetNonce(String appId, String nonce, Integer secondsOfExpiryTime);
}
