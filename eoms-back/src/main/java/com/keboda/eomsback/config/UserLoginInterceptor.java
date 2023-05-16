package com.keboda.eomsback.config;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.keboda.eomsback.utils.JwtTokenUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * 拦截器：验证用户是否登录
 */
public class UserLoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 从 request 的 header 中获得 token 值
        String token = request.getHeader("token");
        if (token == null || token.equals("")) {
            return false;
        }
        // 验证 token, JwtUtil 是自己定义的类，里面有个方法验证 token
        JwtTokenUtils.verify(token);

        return true;
    }
}
