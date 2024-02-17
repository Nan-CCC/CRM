package com.example.enterprisecrm.service.servicelmpl;

import com.example.enterprisecrm.entity.User;
import com.example.enterprisecrm.mapper.UserMapper;
import com.example.enterprisecrm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServicelmpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public User login(String id, String password) {
        User user = userMapper.selectUser(id);
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

}
