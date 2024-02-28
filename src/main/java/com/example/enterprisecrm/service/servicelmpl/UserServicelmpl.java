package com.example.enterprisecrm.service.servicelmpl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.enterprisecrm.entity.User;
import com.example.enterprisecrm.mapper.UserMapper;
import com.example.enterprisecrm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServicelmpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public User login(String id, String password) {
        User user = userMapper.selectById(id);
        if(user!=null){
            if(password.equals(user.getPassword())){
                return user;
            }
            else {
                return null;
            }
        }
        return null;
    }

    @Override
    public Page<User> selectAllUser(int a,int b) {
        Page<User> page = new Page<>();
        page.setCurrent(a);
        page.setSize(b);
        userMapper.selectPage(page, null);
        return page;
    }

}
