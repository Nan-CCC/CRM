package com.example.enterprisecrm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.enterprisecrm.common.result.Result;
import com.example.enterprisecrm.common.result.ResultUtil;
import com.example.enterprisecrm.common.util.JwtUtil;
import com.example.enterprisecrm.entity.User;
import com.example.enterprisecrm.service.servicelmpl.UserServicelmpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

//用于标记一个类，表明该类是一个控制器，并且其下的方法都将返回数据作为响应
@RestController
@RequestMapping("user")
@AllArgsConstructor
@Api("UserApi")
public class UserController {
    @Autowired
    UserServicelmpl userServicelmpl;
    RedisTemplate redisTemplate;
    @PostMapping("/login")
    @ApiOperation(value = "登录")
    public Result login(@RequestParam String id, @RequestParam String password){
        User login = userServicelmpl.login(id, password);
        if(login!=null){
            String token = JwtUtil.createToken(login.getId());
            ValueOperations<String, String> valueOperations =redisTemplate.opsForValue();
            valueOperations.set("token:" + token, login.getId());
            redisTemplate.expire("token:" + token, 24, TimeUnit.HOURS);
            return ResultUtil.success("登录成功",login);
        }else {
            return ResultUtil.error("用户名或密码错误");
        }
    }

    //携带token的请求才能拦截或放行
    //    @ApiImplicitParam(paramType = "header", name = "token", required = true)
    @GetMapping("/alluser")
    @ApiOperation(value = "查询全部用户")
    public Result queryAll(int a,int b){
        //a --页码
        //b --每页条数
        Page<User> userList = userServicelmpl.selectAllUser(a,b);
        if(userList.getTotal()>0){
            return ResultUtil.success(userList);
        }
        return ResultUtil.error();
    }
}
