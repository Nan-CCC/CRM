package com.example.enterprisecrm.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.enterprisecrm.entity.User;

import java.util.List;

public interface UserService {
    //登录
    public User login(String id, String password);

    //查询全部用户
    public Page<User> selectAllUser(int a,int b);


}
