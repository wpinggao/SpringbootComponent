package com.wping.component.wechat.configuration;

import com.wping.component.http.service.WpingHttpClient;
import com.wping.component.wechat.properties.WeChatProperties;
import com.wping.component.wechat.service.WeChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({WeChatProperties.class})
public class WeChatConfiguration {

    @Autowired
    private WeChatProperties weChatProperties;

    @Autowired
    private WpingHttpClient wpingHttpClient;

    @Bean
    public WeChatService weChatService() {
        return new WeChatService(weChatProperties, wpingHttpClient);
    }

}
