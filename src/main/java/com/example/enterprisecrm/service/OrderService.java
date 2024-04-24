package com.example.enterprisecrm.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.enterprisecrm.VO.AddOrderVO;
import com.example.enterprisecrm.entity.Orders;

import java.text.ParseException;
import java.util.List;

public interface OrderService extends IService<Orders> {
    //订单
    //增加
    public String insert(AddOrderVO orderVO) throws ParseException;

    //修改状态
    public int updateStatus(String oid,String status);


    //修改
    public int update(Orders orders);
    //删除
    public int delete(List<String> ids);
    //查询
    public Page<Orders> selectAll(int c, int size);
    public Orders select(String id);
}
