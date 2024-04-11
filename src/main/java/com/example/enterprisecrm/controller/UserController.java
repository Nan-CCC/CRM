package com.example.enterprisecrm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.enterprisecrm.common.result.Result;
import com.example.enterprisecrm.common.result.ResultUtil;
import com.example.enterprisecrm.common.util.JwtUtil;
import com.example.enterprisecrm.entity.User;
import com.example.enterprisecrm.service.UserService;
import com.example.enterprisecrm.service.servicelmpl.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

//用于标记一个类，表明该类是一个控制器，并且其下的方法都将返回数据作为响应
@RestController
@RequestMapping("user")
@AllArgsConstructor
@Api(tags = "UserApi") //swagger 接口说明
public class UserController {
    @Resource
    UserService userService;
    RedisTemplate redisTemplate;
    @PostMapping("/login")
    @ApiOperation(value = "登录")
    public Result login(@RequestParam String id, @RequestParam String password, @RequestParam String verify, HttpSession session){
        String verifyCode = session.getAttribute("verifyCode")+"";
        //测试验证码
        if(verify.equals("123")){
            User login = userService.login(id, password);
            if(login!=null){
                redisTemplate.delete("token:");
                String token = JwtUtil.createToken(login.getId());
                ValueOperations<String, String> valueOperations =redisTemplate.opsForValue();
                valueOperations.set("token:" + token, login.getId());
                redisTemplate.persist("token:" + token);
                redisTemplate.expire("token:" + token, 10, TimeUnit.MINUTES);
                return ResultUtil.success("登录成功",token);
            }
            return ResultUtil.error("用户名或密码错误");
        }
        return ResultUtil.error("验证码错误");
    }

    //根据Authorization里的token获取用户信息
    //携带token的请求才能拦截或放行
   // @ApiImplicitParam(paramType = "header", name = "Authorization", required = true)
    //这个token应该在header里
    @GetMapping("/queryuser")
    @ApiOperation(value = "Authorization查询用户")
    public Result queryUserByToken(@RequestHeader("Authorization") String token){
        String userId = JwtUtil.getToken(token);
        User user = userService.selectUser(userId);
        if(user!=null){
            return ResultUtil.success(user);
        }
        return ResultUtil.error();
    }

    @GetMapping("/verify")
    @ApiOperation(value = "判断token是否过期")
    public Result verifyToken(@RequestHeader("Authorization") String token){
        try {
            boolean verified = JwtUtil.verifyToken(token);
            if(verified){
                return ResultUtil.success();
            }
            return ResultUtil.error();
        }catch (Exception e){
            return ResultUtil.warning();
        }
    }

    @PostMapping("/queryall")
    @ApiOperation(value = "查询全部用户")
    public Result queryAll(int a,int b){
        //a --页码
        //b --每页条数
        Page<User> userList = userService.selectAllUser(a,b);
        if(userList.getTotal()>0){
            return ResultUtil.success(userList);
        }
        return ResultUtil.error();
    }

    @PostMapping("/query")
    @ApiOperation(value = "查询用户")
    public Result query(@RequestParam String id){
        User user = userService.selectUser(id);
        if(user!=null){
            return ResultUtil.success(user);
        }
        return ResultUtil.error();
    }

    @PostMapping("/add")
    @ApiOperation(value = "增加用户")
    public Result add(@ModelAttribute User user){
        User insertUser = userService.insertUser(user);
        if(insertUser!=null){
            return ResultUtil.success(insertUser);
        }
        return ResultUtil.error();
    }



    @DeleteMapping("/delete")
    @ApiOperation(value = "删除用户")
    public Result delete(@RequestParam List<String> ids){
        int i = userService.deleteUser(ids);
        if(i>0){
            return ResultUtil.success(i);
        }
        return ResultUtil.error(i);
    }

    @PutMapping("/update")
    @ApiOperation(value = "修改用户")
    public Result update(@ModelAttribute User user){
        int i = userService.updateUser(user);
        if(i>0){
            return ResultUtil.success(i);
        }
        return ResultUtil.error();
    }


}
