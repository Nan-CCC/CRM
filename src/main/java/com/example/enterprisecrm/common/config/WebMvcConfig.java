package com.example.enterprisecrm.common.config;

import com.example.enterprisecrm.common.interceptor.JWTInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
        @Bean
        public JWTInterceptor jwtInterceptor(){
            return new JWTInterceptor();
        }

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(jwtInterceptor())
                    .addPathPatterns("/ex") // 拦截接口  /** 拦截除了放开外所有接口
                    .excludePathPatterns("/user/login");
        }
    }


