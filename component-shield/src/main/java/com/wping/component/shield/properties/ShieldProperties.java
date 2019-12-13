package com.wping.component.shield.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * describe:
 *
 * @author Wping.gao
 * @date 2019/01/21
 */
@ConfigurationProperties(ShieldProperties.PREFIX)
public class ShieldProperties {

    public static final String PREFIX = "wping.shield";

    private String xssFilterUrlPatterns = "/*";
    private Integer xssFilterOrder = -1;

    public String getXssFilterUrlPatterns() {
        return xssFilterUrlPatterns;
    }

    public void setXssFilterUrlPatterns(String xssFilterUrlPatterns) {
        this.xssFilterUrlPatterns = xssFilterUrlPatterns;
    }

    public Integer getXssFilterOrder() {
        return xssFilterOrder;
    }

    public void setXssFilterOrder(Integer xssFilterOrder) {
        this.xssFilterOrder = xssFilterOrder;
    }
}
