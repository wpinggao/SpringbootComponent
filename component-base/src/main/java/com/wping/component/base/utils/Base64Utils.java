package com.wping.component.base.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;

public class Base64Utils {

    private static final Logger logger = LoggerFactory.getLogger(Base64Utils.class);

    public static String base64Decode(String base64Value) {
        try {
            byte[] decodedCookieBytes = Base64.getDecoder().decode(base64Value);
            return new String(decodedCookieBytes, "UTF-8");
        }
        catch (Exception e) {
            logger.error("Unable to Base64 decode value:{}", base64Value, e);
            return null;
        }
    }

    /**
     * Encode the value using Base64.
     * @param value the String to Base64 encode
     * @return the Base64 encoded value
     */
    public static String base64Encode(String value) {
        try {
            byte[] encodedCookieBytes = Base64.getEncoder().encode(value.getBytes());
            return new String(encodedCookieBytes, "UTF-8");
        } catch (Exception e) {
            logger.error("Unable to Base64 encode value:{}", value, e);
            return null;
        }
    }

}
