package com.wping.component.distributeid.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(SnowFlakeProperties.PREFIX)
public class SnowFlakeProperties {

    public static final String PREFIX = "wping.snowflake";

    private boolean enabled = false;
    // workerId 工作ID (0~31)
    private long workerId;
    // datacenterId 数据中心ID (0~31)
    private long datacenterId;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(long workerId) {
        this.workerId = workerId;
    }

    public long getDatacenterId() {
        return datacenterId;
    }

    public void setDatacenterId(long datacenterId) {
        this.datacenterId = datacenterId;
    }
}
