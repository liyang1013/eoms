package com.keboda.eomsback.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 *
 * 注册自定义拦截器
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 registration 拦截器
        InterceptorRegistration registration = registry.addInterceptor(new UserLoginInterceptor());

        // 拦截所有的路径
        registration.addPathPatterns("/**");

        // 添加不拦截路径
        registration.excludePathPatterns(
                "/zx/login"
        );
    }
}
