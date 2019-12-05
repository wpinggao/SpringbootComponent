package com.wping.component.apisign.utils;

import com.alibaba.fastjson.JSON;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;

public class MD5SignUtils {

    /**
     * 签名字段
     */
    public static final String SIGN_FIELD_NAME = "sign";

    /**
     * 生成签名
     * <p>
     * 算法说明：
     * 1、对请求参数按首字母进行字典排序
     * 2、遍历排序或的KEY，以【key=value + 连接字符】格式拼装字符串 + secret
     * 3、对第2步获取的字符串进行MD5加密
     *
     * @param paramMap 请求参数
     * @param secret   公共密钥，配置中心获取
     * @return
     */
    public static String create(Map<String, Object> paramMap, String secret) {
        return MD5Utils.md5(getSortParams(paramMap), secret);
    }

    /**
     * 验证签名
     *
     * @param paramMap  请求参数
     * @param secret    公共密钥，配置中心获取
     * @param signature 签名字符串
     * @return
     */
    public static boolean verify(Map<String, Object> paramMap, String secret, String signature) {
        String ciphertext = create(paramMap, secret);
        return ciphertext.equals(signature);
    }

    public static String getSortParams(Map<String, Object> params) {
        Map<String, Object> map = new TreeMap<>(
                new Comparator<String>() {
                    public int compare(String obj1, String obj2) {
                        // 升序排序
                        return obj1.compareTo(obj2);
                    }
                });
        for (String key : params.keySet()) {
            if (SIGN_FIELD_NAME.equals(key)) {
                continue;
            }
            map.put(key, params.get(key));
        }

        Set<String> keySet = map.keySet();
        Iterator<String> iter = keySet.iterator();
        StringBuilder sb = new StringBuilder();
        while (iter.hasNext()) {
            String key = iter.next();
            Object value = map.get(key);
            if (value != null) {
                sb.append(key).append("=").append(value).append("&");
            }

        }
        if (sb.length() > 0) {
            return sb.substring(0, sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * 生成机构秘钥
     *
     * @return
     */
    public static String generateAppSecret() {
        return RandomUtils.generateString(32);
    }

    public static Map<String, Object> bean2MapObject(Object object) {
        if (object == null) {
            return null;
        }

        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(object.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(object);

                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    public static void main(String[] args) {

        String dataStr = "{\"name\":\"abc\"}";
        Long timestamp = System.currentTimeMillis();
        //String nonce = String.valueOf(timestamp) + RandomUtils.generateString(5);
        String nonce = "0123456";
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("appId", "dss");
        requestMap.put("bizData", dataStr);
        requestMap.put("timestamp", timestamp);
        requestMap.put("nonce", nonce);
        String appSecret = generateAppSecret();
        String md5Sign = create(requestMap, appSecret);
        requestMap.put("sign", md5Sign);
        System.out.println("加签后的请求数据:" + JSON.toJSONString(requestMap));

        boolean signVerifyPass = verify(requestMap, appSecret, md5Sign);

        System.out.println(signVerifyPass ? "成功" : "失败");
    }
}