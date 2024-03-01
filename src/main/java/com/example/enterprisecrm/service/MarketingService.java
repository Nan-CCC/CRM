package com.example.enterprisecrm.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.enterprisecrm.entity.Marketing;

import java.util.List;

public interface MarketingService extends IService<Marketing> {
    //营销活动
    //增加（一个）
    public int insertMartet(Marketing marketing);
    //修改
    public int updateMartet(Marketing marketing);
    //删除(多个)
    public int deleteMartet(List<String> ids);
    //查询（分页）
    public Page<Marketing> selectAll(int a,int b);
    //查询(一个)
    public Marketing selectMarket(String id);
}
