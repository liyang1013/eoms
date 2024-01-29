package com.keboda.eomsback.utils;

public class StringUtils {


    /**
     * 格式化字符串为boolean
     * @param str {string}
     * @return
     */
    public static Boolean formatBoolean(String str){

        return str != null && str.equalsIgnoreCase("y");
    }
}
