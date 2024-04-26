package com.example.enterprisecrm.common.Log;

/*
自定义用户操作日志切面
 */

import com.alibaba.fastjson2.JSONObject;
import com.example.enterprisecrm.common.util.ThreadLocalUtil;
import com.example.enterprisecrm.entity.SysLog;
import com.example.enterprisecrm.service.SysLogService;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//定义切面类
@Aspect
//组件
@Component
//用于在类中自动生成一个名为 log 的日志对象
@Slf4j
public class LogAspect {
    @Autowired
    private SysLogService service;

    @Pointcut("@annotation(com.example.enterprisecrm.common.Log.MyLog)")
    public void logPointCut(){
        log.info("-------->配置织入点");
    }

    @AfterReturning(pointcut = "logPointCut()")
    public void doAfterReturning(JoinPoint joinPoint){
        handleLog(joinPoint,null);
    }

    /**
     * 拦截异常
     */
    @AfterThrowing(value = "logPointCut()",throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint,Exception e){
        handleLog(joinPoint,e);
    }

    private void handleLog(final JoinPoint joinPoint, final Exception e) {
        // 获得MyLog注解
        MyLog controllerLog = getAnnotationLog(joinPoint);
        if (controllerLog == null) {
            return;
        }
        SysLog sysLog = new SysLog();
        // 操作状态（0正常 1异常）
        sysLog.setStatus(0);
        // 操作时间
        sysLog.setTime(new Date());
        if (e != null) {
            sysLog.setStatus(1);
            // IotLicenseException为本系统自定义的异常类，读者若要获取异常信息，请根据自身情况变通
            sysLog.setErrorMsg(StringUtils.substring(e.getMessage(),0,2000));
        }

        // UserUtils.getUri();获取方法上的路径 如：/login，本文实现方法如下：
        // 1、在拦截器中 String uri = request.getRequestURI();
        // 2、用ThreadLocal存放uri，UserUtils.setUri(uri);
        // 3、UserUtils.getUri();
        SysLog current = ThreadLocalUtil.getCurrent();
        sysLog.setUrl(current.getUrl());
        if(current.getUser()!=null){
            sysLog.setUser(current.getUser());
        }
        // 处理注解上的参数
        getControllerMethodDescription(joinPoint, controllerLog, sysLog);
        // 保存数据库
        int insert = service.insert(sysLog);
    }

    /**
     * 是否存在注解，存在就获取，不存在null
     */
    private MyLog getAnnotationLog(JoinPoint joinPoint){
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature=(MethodSignature)signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            return method.getAnnotation(MyLog.class);
        }
        return null;
    }

    /**
     * 获取Controller层上MyLog注解中对方法的描述信息
     */
    private void getControllerMethodDescription(JoinPoint joinPoint, MyLog myLog, SysLog log) {
        // 设置业务类型   返回索引？可能，就是数字
        log.setBusinessType(String.valueOf(myLog.businessType().ordinal()));
        // 设置模块标题，eg:登录
        log.setTitle(myLog.title());
        // 对方法上的参数进行处理，处理完：userName=xxx,password=xxx
        String optParam = getAnnotationValue(joinPoint, myLog.optParam());
        log.setParam(optParam);
    }

    /**
     * 对方法上的参数进行处理
     */
    private String getAnnotationValue(JoinPoint joinPoint, String name) {
        String paramName = name;
        // 获取方法中所有的参数
        Map<String, Object> params = getParams(joinPoint);
        // 参数是否是动态的:#{paramName}
        if (paramName.matches("^#\\{\\D*\\}")) {
            // 获取参数名,去掉#{ }
            paramName = paramName.replace("#{", "").replace("}", "");
            // 是否是复杂的参数类型:对象.参数名
            if (paramName.contains(".")) {
                String[] split = paramName.split("\\.");
                // 获取方法中对象的内容
                Object object = getValue(params, split[0]);
                // 转换为JsonObject
                String jsonString = JSONObject.toJSONString(object);
                return jsonString;
            } else {// 简单的动态参数直接返回
                StringBuilder str = new StringBuilder();
                String[] paraNames = paramName.split(",");
                for (String paraName : paraNames) {

                    String val = String.valueOf(getValue(params, paraName));
                    // 组装成 userName=xxx,password=xxx,
                    str.append(paraName).append("=").append(val).append(",");
                }
                // 去掉末尾的,
                if (str.toString().endsWith(",")) {
                    String substring = str.substring(0, str.length() - 1);
                    return substring;
                } else {
                    return str.toString();
                }
            }
        }
        // 非动态参数直接返回
        return name;
    }

    /**
     * 获取方法上的所有参数，返回Map类型, eg: 键："userName",值:xxx  键："password",值:xxx
     */
    public Map<String, Object> getParams(JoinPoint joinPoint) {
        Map<String, Object> params = new HashMap<>(8);
        // 通过切点获取方法所有参数值["zhangsan", "123456"]
        Object[] args = joinPoint.getArgs();
        // 通过切点获取方法所有参数名 eg:["userName", "password"]
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] names = signature.getParameterNames();
        for (int i = 0; i < args.length; i++) {
            params.put(names[i], args[i]);
        }
        return params;
    }

    /**
     * 从map中获取键为paramName的值，不存在放回null
     */
    private Object getValue(Map<String, Object> map, String paramName) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getKey().equals(paramName)) {
                return entry.getValue();
            }
        }
        return null;
    }

}
