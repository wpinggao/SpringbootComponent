package com.wping.component.http.configuration;

import com.wping.component.http.properties.HttpClientProperties;
import com.wping.component.http.service.WpingHttpClient;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * describe:
 *
 * @author Wping.gao
 * @date 2019/01/25
 */
@Configuration
@EnableConfigurationProperties({HttpClientProperties.class})
public class HttpClientConfiguration {

    @Autowired
    private HttpClientProperties httpClientProperties;

    /**
     * 首先实例化一个连接池管理器，设置最大连接数、并发连接数
     *
     * @return
     */
    @Bean(name = "httpClientConnectionManager")
    public PoolingHttpClientConnectionManager getHttpClientConnectionManager() {
        PoolingHttpClientConnectionManager httpClientConnectionManager = new PoolingHttpClientConnectionManager();
        //最大连接数
        httpClientConnectionManager.setMaxTotal(httpClientProperties.getMaxTotal());
        //并发数
        httpClientConnectionManager.setDefaultMaxPerRoute(httpClientProperties.getDefaultMaxPerRoute());
        return httpClientConnectionManager;
    }

    /**
     * 实例化连接池，设置连接池管理器。
     * 这里需要以参数形式注入上面实例化的连接池管理器
     *
     * @param httpClientConnectionManager
     * @return
     */
    @Bean(name = "httpClientBuilder")
    public HttpClientBuilder getHttpClientBuilder(@Qualifier("httpClientConnectionManager") PoolingHttpClientConnectionManager httpClientConnectionManager) {
        return HttpClients.custom().setConnectionManager(httpClientConnectionManager);
    }

    /**
     * 注入连接池，用于获取httpClient
     *
     * @param httpClientBuilder
     * @return
     */
    @Bean(name = "closeableHttpClient")
    public CloseableHttpClient getCloseableHttpClient(@Qualifier("httpClientBuilder") HttpClientBuilder httpClientBuilder) {
        return httpClientBuilder.build();
    }

    /**
     * 使用builder构建一个默认RequestConfig对象
     *
     * @return
     */
    @Bean(name = "requestConfig")
    public RequestConfig requestConfig() {
        return RequestConfig.custom()
                .setConnectTimeout(httpClientProperties.getConnectTimeout())
                .setConnectionRequestTimeout(httpClientProperties.getConnectionRequestTimeout())
                .setSocketTimeout(httpClientProperties.getSocketTimeout())
                .build();
    }

    /**
     * 使用builder构建一个代理http 80 端口RequestConfig对象
     *
     * @return
     */
    @Bean(name = "requestConfigProxyHttp")
    public RequestConfig requestConfigProxyHttp() {
        return RequestConfig.custom()
                .setConnectTimeout(httpClientProperties.getConnectTimeout())
                .setConnectionRequestTimeout(httpClientProperties.getConnectionRequestTimeout())
                .setSocketTimeout(httpClientProperties.getSocketTimeout())
                .setProxy(new HttpHost(httpClientProperties.getProxyHost(), 80))
                .build();
    }

    /**
     * 使用builder构建一个代理https 443 端口RequestConfig对象
     *
     * @return
     */
    @Bean(name = "requestConfigProxyHttps")
    public RequestConfig requestConfigProxyHttps() {
        return RequestConfig.custom()
                .setConnectTimeout(httpClientProperties.getConnectTimeout())
                .setConnectionRequestTimeout(httpClientProperties.getConnectionRequestTimeout())
                .setSocketTimeout(httpClientProperties.getSocketTimeout())
                .setProxy(new HttpHost(httpClientProperties.getProxyHost(), 443))
                .build();
    }

    @Bean(destroyMethod = "destroy", name = "wpingHttpClient")
    @ConditionalOnMissingBean
    public WpingHttpClient wpingHttpClient(@Qualifier("closeableHttpClient") CloseableHttpClient closeableHttpClient,
                                               @Qualifier("requestConfig") RequestConfig requestConfig,
                                               @Qualifier("requestConfigProxyHttp") RequestConfig requestConfigProxyHttp,
                                               @Qualifier("requestConfigProxyHttps") RequestConfig requestConfigProxyHttps) {
        return new WpingHttpClient(closeableHttpClient, requestConfig, requestConfigProxyHttp, requestConfigProxyHttps);
    }

}
