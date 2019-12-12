package com.wping.component.base.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class HttpUtils {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    private static final String UTF8 = "UTF-8";

    public static String getQueryString(HttpServletRequest request) {
        String queryString = "";
        // 判断请求参数是否为空
        if (request.getQueryString() != null) {
            queryString = "?" + request.getQueryString();
        }
        return queryString;
    }

    // 获取json数据
    public static String getPostFormData2Json(HttpServletRequest request) {
        String acceptjson = null;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), UTF8))) {
            StringBuilder sb = new StringBuilder();
            String temp;
            while ((temp = br.readLine()) != null) {
                sb.append(temp);
            }
            acceptjson = sb.toString();
        } catch (Exception e) {
            logger.error("通过HttpServletRequest获取post json 异常", e);
        }
        return acceptjson;
    }

    public static Map<String, String> getHeaders(HttpServletRequest request) {

        Map<String, String> headers = new HashMap<>();
        @SuppressWarnings("rawtypes")
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            if ("content-length".equalsIgnoreCase(key) || "host".equalsIgnoreCase(key)
                    || "content-type".equalsIgnoreCase(key)) {
                continue;
            }
            String value = request.getHeader(key);
            headers.put(key, value);
        }
        return headers;
    }


}
