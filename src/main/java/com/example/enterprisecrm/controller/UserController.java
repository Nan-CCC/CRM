package com.example.enterprisecrm.controller;

import com.example.enterprisecrm.common.result.Result;
import com.example.enterprisecrm.common.result.ResultUtil;
import com.example.enterprisecrm.entity.User;
import com.example.enterprisecrm.service.servicelmpl.UserServicelmpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



//用于标记一个类，表明该类是一个控制器，并且其下的方法都将返回数据作为响应
@RestController
@RequestMapping("user")
@AllArgsConstructor
@Api("UserApi")
public class UserController {
    @Autowired
    UserServicelmpl userServicelmpl;

    @PostMapping("/login")
    @ApiOperation(value = "登录")
    public Result login(@RequestParam String id, @RequestParam String password){
        User login = userServicelmpl.login(id, password);
        if(login!=null){
            return ResultUtil.success(login);
        }else {
            return ResultUtil.error("登录失败");
        }
    }
}
