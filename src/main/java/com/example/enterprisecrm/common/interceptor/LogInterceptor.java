package com.example.enterprisecrm.common.interceptor;

import com.example.enterprisecrm.common.util.JwtUtil;
import com.example.enterprisecrm.common.util.ThreadLocalUtil;
import com.example.enterprisecrm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogInterceptor implements HandlerInterceptor {
    //请求执行前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        try{
            String url = request.getRequestURI();
            String authorization = request.getHeader("Authorization");
            if(authorization!=null){
                String userId = JwtUtil.getToken(authorization);
                ThreadLocalUtil.addCurrent(userId,url);
            }
            else {
                ThreadLocalUtil.addCurrent(null,url);
            }
        }catch (Exception e){
            throw new Exception("LogInterceptor获取有误");
        }
        return true;
    }

    //接口访问结束后
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtil.remove();
    }

}



