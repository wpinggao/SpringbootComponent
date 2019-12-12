package com.wping.component.base.utils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

public class IpAddressUtils {

    public static String getUserRealIP(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }

        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
                //根据网卡取本机配置的IP
                ipAddress = getLocalIp();
            }
        }

        // 如果是多级代理，那么取第一个ip为客户端ip
        if (ipAddress != null && ipAddress.length() > 15 && ipAddress.contains(",")) {
            String[] ips = ipAddress.split(",");
            for (String strIp : ips) {
                if (!"unknown".equalsIgnoreCase(strIp)) {
                    ipAddress = strIp;
                    break;
                }
            }
        }

        return ipAddress;
    }

    /**
     * 获取计算机名
     **/
    public static String getComputerName() {
        Map<String, String> map = System.getenv();
        String computerName = map.get("COMPUTERNAME");
        return computerName;
    }

    public static String getLocalIp() {

        String ipAddress = "127.0.0.1";
        try {
            InetAddress inet = InetAddress.getLocalHost();
            ipAddress = inet.getHostAddress();
        } catch (UnknownHostException e) {
        }
        return ipAddress;

    }
}
