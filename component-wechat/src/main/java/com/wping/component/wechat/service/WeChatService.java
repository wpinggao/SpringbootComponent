package com.wping.component.wechat.service;

import com.alibaba.fastjson.JSON;
import com.wping.component.http.service.WpingHttpClient;
import com.wping.component.wechat.dto.TokenDto;
import com.wping.component.wechat.dto.UserDto;
import com.wping.component.wechat.properties.WeChatProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class WeChatService {

    private static final Logger logger = LoggerFactory.getLogger(WeChatService.class);

    /**
     * 微信开放平台二维码连接
     */
    public final static String OPEN_QRCODE_URL = "https://open.weixin.qq.com/connect/qrconnect?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_login&state=%s#wechat_redirect";


    /**
     * 开放平台获取access_token地址
     */
    public final static String OPEN_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";


    /**
     * 获取用户信息
     */
    public final static String OPEN_USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s";

    private WeChatProperties weChatProperties;

    private WpingHttpClient wpingHttpClient;

    public WeChatService(WeChatProperties weChatProperties, WpingHttpClient wpingHttpClient) {
        this.weChatProperties = weChatProperties;
        this.wpingHttpClient = wpingHttpClient;
    }

    /**
     * 获取微信扫码登陆二维码
     *
     * @param state 用于保持请求和回调的状态，授权请求后原样带回给第三方。该参数可用于防止csrf攻击（跨站请求伪造攻击），建议第三方带上该参数，可设置为简单的随机数加session进行校验
     * @return
     */
    public String getQrCodeUrl(@Nullable String state) {

        String callbackUrl = null;
        try {
            callbackUrl = URLEncoder.encode(weChatProperties.getRedirectUrl(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }

        //调微信的二维码
        String qrcodeUrl = String.format(OPEN_QRCODE_URL, weChatProperties.getAppid(), callbackUrl, state);

        return qrcodeUrl;

    }

    /**
     * 获取微信登陆用户信息
     * @param code 微信重定向后url参数code
     * @param isHttpProxy 是否http代理访问
     * @return
     */
    public @Nullable UserDto getUserInfo(String code, boolean isHttpProxy) {

        try {
            String tokenUrls = String.format(OPEN_ACCESS_TOKEN_URL, weChatProperties.getAppid(), weChatProperties.getAppsecret(), code);
            String tokenResult = wpingHttpClient.httpSyncGet(tokenUrls, null, isHttpProxy);
            TokenDto tokenDto = JSON.parseObject(tokenResult, TokenDto.class);
            if (tokenDto != null && StringUtils.isNotBlank(tokenDto.getAccess_token()) && StringUtils.isNotBlank(tokenDto.getOpenid())) {
                String userUrls = String.format(OPEN_USER_INFO_URL, tokenDto.getAccess_token(), tokenDto.getOpenid());
                String userResult = wpingHttpClient.httpSyncGet(userUrls, null, isHttpProxy);
                UserDto userDto = JSON.parseObject(userResult, UserDto.class);
                return userDto;
            }
        } catch (Exception e) {
            logger.error("获取微信登陆用户信息异常, code:{}", code, e);
        }
        return null;
    }
}
