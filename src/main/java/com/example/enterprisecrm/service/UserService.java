package com.example.enterprisecrm.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.enterprisecrm.entity.User;

import java.util.List;

public interface UserService extends IService<User> {
    //登录
    public User login(String id, String password);

    //查询用户（全部）(分页)
    public Page<User> selectAllUser(int a,int b);

    //查询用户（一个）
    public User selectUser(String id);

    //增加用户(一个)
    public User insertUser(User user);

    //删除用户（一个或多个） ---返回删除条数
    public int deleteUser(List<String> ids);

    //修改用户
    public int updateUser(User user);


}
