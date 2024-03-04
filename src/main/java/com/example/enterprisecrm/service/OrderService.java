package com.example.enterprisecrm.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.enterprisecrm.entity.Orders;

import java.util.List;

public interface OrderService extends IService<Orders> {
    //订单
    //增加
    public int insert(Orders orders);
    //删除
    public int delete(List<String> ids);
    //修改
    public int update(Orders orders);
    //查询
    public Page<Orders> selectAll(int c, int size);
    public Orders select(String id);
}
