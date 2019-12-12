package com.wping.component.http.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * describe:
 *
 * @author Wping.gao
 * @date 2019/01/25
 */
@ConfigurationProperties(HttpClientProperties.PREFIX)
public class HttpClientProperties {

    public static final String PREFIX = "http.client";

    // 最大连接数
    private Integer maxTotal = 100;
    // 并发数
    private Integer defaultMaxPerRoute = 20;
    // 创建连接的最长时间
    private Integer connectTimeout = 5000;
    // 从连接池中获取到连接的最长时间
    private Integer connectionRequestTimeout = 5000;
    // 数据传输的最长时间
    private Integer socketTimeout = 30000;
    // 代理主机
    private String proxyHost = "proxy-public.intsit.sfdc.com.cn";

    public Integer getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(Integer maxTotal) {
        this.maxTotal = maxTotal;
    }

    public Integer getDefaultMaxPerRoute() {
        return defaultMaxPerRoute;
    }

    public void setDefaultMaxPerRoute(Integer defaultMaxPerRoute) {
        this.defaultMaxPerRoute = defaultMaxPerRoute;
    }

    public Integer getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public Integer getConnectionRequestTimeout() {
        return connectionRequestTimeout;
    }

    public void setConnectionRequestTimeout(Integer connectionRequestTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
    }

    public Integer getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(Integer socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }
}
