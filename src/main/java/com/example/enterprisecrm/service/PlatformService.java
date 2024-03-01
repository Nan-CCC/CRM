package com.example.enterprisecrm.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.enterprisecrm.entity.Platform;
import com.example.enterprisecrm.entity.User;

import java.util.List;

public interface PlatformService extends IService<Platform> {
    //平台
    //增加
    public int insert(Platform platform);
    //删除
    public int delete(List<String> ids);

    //修改
    public int update(Platform platform);
    //查询
    public Page<Platform> selectAll(int c,int size);
    public Platform select(String id);
}
