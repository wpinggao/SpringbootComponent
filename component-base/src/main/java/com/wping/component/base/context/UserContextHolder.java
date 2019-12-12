package com.wping.component.base.context;

import com.wping.component.base.constatns.CommonConstants;
import com.wping.component.base.dto.WpingToken;

import java.util.HashMap;
import java.util.Map;

public class UserContextHolder {

    public static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<Map<String, Object>>();

    public static void set(String key, Object value) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<String, Object>();
            threadLocal.set(map);
        }
        map.put(key, value);
    }

    public static Object get(String key) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<String, Object>();
            threadLocal.set(map);
        }
        return map.get(key);
    }

    public static String getUserId() {
        Object value = get(CommonConstants.WPING_CLIENT_TOKEN);
        if (value != null) {
            return ((WpingToken)value).getUserId();
        }
        return null;
    }

    public static String getUserName() {
        Object value = get(CommonConstants.WPING_CLIENT_TOKEN);
        if (value != null) {
            return ((WpingToken)value).getUserName();
        }
        return null;
    }

    public static WpingToken getPrototypeToken() {
        Object value = get(CommonConstants.WPING_CLIENT_TOKEN);
        return (value == null ? null : (WpingToken) value);
    }

    public static void setPrototypeToken(WpingToken wpingToken) {
        set(CommonConstants.WPING_CLIENT_TOKEN, wpingToken);
    }

    private static String returnObjectValue(Object value) {
        return value == null ? null : value.toString();
    }

    public static void remove() {
        threadLocal.remove();
    }

}
