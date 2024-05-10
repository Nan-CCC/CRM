package com.example.enterprisecrm.service.servicelmpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.enterprisecrm.entity.User;
import com.example.enterprisecrm.mapper.UserMapper;
import com.example.enterprisecrm.service.UserService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {
    @Resource
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
    public List<User> selectAllUser() {
        List<User> users = userMapper.selectList(null);
        return users;
    }

    @Override
    public User selectUser(String id) {
        User user = userMapper.selectById(id);
        return user;
    }

    @Override
    public User insertUser(User user) {
        int flag = userMapper.insert(user);
        if(flag!=0){
            LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
            wrapper.orderByDesc(User::getId);
            wrapper.last("limit 1");
            User selectOne = userMapper.selectOne(wrapper);
            return selectOne;
        }
        return null;
    }

    @Override
    public int deleteUser(List<String> ids) {
        int i = userMapper.deleteBatchIds(ids);
        return i;
    }

    @Override
    public int updateUser(User user) {
        int i = userMapper.updateById(user);
        return i;
    }

}
