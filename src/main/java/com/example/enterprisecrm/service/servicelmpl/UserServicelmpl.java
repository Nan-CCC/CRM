package com.example.enterprisecrm.service.servicelmpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.enterprisecrm.entity.User;
import com.example.enterprisecrm.mapper.UserMapper;
import com.example.enterprisecrm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static net.sf.jsqlparser.parser.feature.Feature.insert;

@Service
public class UserServicelmpl extends ServiceImpl<UserMapper,User> implements UserService {
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

    @Override
    public User selectUser(String id) {
        User user = userMapper.selectById(id);
        return user;
    }

    @Override
    public User insertUser(User user) {
        int flag = userMapper.insert(user);
        if(flag!=0){
            //Lambda使wapper可以使用实体字段而不是数据库字段
            //例：
            //wrapper.eq("banner_id", id);
            //wrapper.lambda().eq(BannerItem::getBannerId, id);
            LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
            //根据id降序排列
            wrapper.orderByDesc(User::getId);
            //last拼接sql语句 限制1条
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
