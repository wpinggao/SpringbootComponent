package com.wping.component.dislogtracing.utils;


import com.wping.component.base.utils.RandomUtils;

public class CommonUtils {

    public static String genTraceId(){
        return RandomUtils.generateLowerString(16);
    }

}
