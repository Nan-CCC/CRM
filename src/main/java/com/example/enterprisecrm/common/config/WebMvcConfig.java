package com.example.enterprisecrm.common.config;

import com.example.enterprisecrm.common.interceptor.JWTInterceptor;
import com.example.enterprisecrm.common.interceptor.LogInterceptor;
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

        @Bean LogInterceptor logInterceptor(){return new LogInterceptor();
        }
        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            //order越小，越先执行，否则按照register配置的顺序执行
            //日志
            registry.addInterceptor(logInterceptor())
                    .addPathPatterns("/**")//拦截所有接口
                    .order(1);
            //jwt
            registry.addInterceptor(jwtInterceptor())
                    .addPathPatterns("/ex") // 拦截接口  /** 拦截除了放开外所有接口
                    .excludePathPatterns("/user/login")
                    .order(2);
        }
    }


