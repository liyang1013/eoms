package com.keboda.eomsback.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * flux系密码修改工具,得到加密字符串覆盖数据库
 */
public class FluxPwdUtils {

    private final static String username = "JX2302304";

    private final static String password = "JX2302304";

    private final static String passwordSalt = "ST1678338626440SR121112077108036030";


    public static void MD5Signature() throws Exception{

        String token = changeToAa(username) + "~" + password;
        String orgin =  passwordSalt+"userPassword"+token+passwordSalt;

        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] bytes = md.digest(orgin.getBytes(StandardCharsets.UTF_8));

        StringBuilder hexString = new StringBuilder();
        String stmp;

        for(int n = 0; n < bytes.length; ++n) {
            stmp = Integer.toHexString(bytes[n] & 255);
            if (stmp.length() == 1) {
                hexString.append("0").append(stmp);
            } else {
                hexString.append(stmp);
            }
        }

        System.out.println(hexString.toString().toUpperCase());
    }

    public static String changeToAa(String strValue) {
        String newStrValue = null;
        if (strValue != null) {
            char[] strValueChars = strValue.toCharArray();
            char[] newStrValueChars = new char[strValueChars.length];
            int i = 0;
            int diffVal = 32;
            int countTmp = 0;
            for(int j = strValueChars.length - 1; j >= 0; --j) {
                char charTmp = strValueChars[j];
                if (charTmp >= 'A' && charTmp <= 'Z') {
                    charTmp = (char)(charTmp + diffVal);
                }
                if (charTmp >= 'a' && charTmp <= 'z') {
                    ++countTmp;
                    if (countTmp % 2 == 1) {
                        charTmp = (char)(charTmp - diffVal);
                    }
                }
                newStrValueChars[i] = charTmp;
                ++i;
            }
            newStrValue = new String(newStrValueChars);
        }
        return newStrValue;
    }
}
