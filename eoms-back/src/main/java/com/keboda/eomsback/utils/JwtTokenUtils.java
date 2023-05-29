package com.keboda.eomsback.utils;

import cn.hutool.core.util.NumberUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;

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

    public static DecodedJWT verify(String token){
        if(StringUtils.isEmpty(token)){
            throw new RuntimeException("token不能为空");
        }
        //获取登录用户真正的密码
        String password = "password";
        JWTVerifier build = JWT.require(Algorithm.HMAC256(password)).build();
        return build.verify(token);
    }
}
