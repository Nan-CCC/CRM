package com.example.enterprisecrm.common.util;

import com.example.enterprisecrm.entity.SysLog;
import com.example.enterprisecrm.entity.User;

/**
 * 存放当前登录用户和请求方法
 * 加入拦截器
 * 在日志切片中使用
 */
public class ThreadLocalUtil {

    /**
     * 保存用户对象的ThreadLocal  在拦截器操作 添加、删除相关数据
     */
    private static final ThreadLocal<SysLog> threadLocal = new ThreadLocal<>();

    /**
     * 添加当前登录用户和请求路径
     *
     */
    public static void addCurrent(String userId,String local){
        SysLog sysLog = new SysLog();
        sysLog.setUser(userId);
        sysLog.setUrl(local);
        threadLocal.set(sysLog);
    }

    /**
     * 获取
     */
    public static SysLog getCurrent(){
        return threadLocal.get();
    }


    /**
     * 删除当前  在拦截器方法执行后移除
     */
    public static void remove(){
        threadLocal.remove();
    }


}

