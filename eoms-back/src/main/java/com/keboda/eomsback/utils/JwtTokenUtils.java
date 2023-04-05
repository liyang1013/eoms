package com.keboda.eomsback.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;

public class JwtTokenUtils {
    private JwtTokenUtils() {
        throw new IllegalStateException("Utility class");
    }
    /**
     * 生成token
     * @return
     */
    public static String getToken(String username,String password){
        return  JWT.create()
                .withAudience(username)
                .withSubject("token")
                .withExpiresAt(DateUtils.addDay(new Date(),1))
                .sign(Algorithm.HMAC256(password));
    }
}
