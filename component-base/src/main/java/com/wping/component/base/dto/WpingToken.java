package com.wping.component.base.dto;

import java.io.Serializable;

public class WpingToken implements Serializable {
    private static final long serialVersionUID = -6058007988428962819L;

    private String userId;
    private String userName;

    public WpingToken(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
