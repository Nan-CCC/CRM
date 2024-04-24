package com.example.enterprisecrm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.enterprisecrm.VO.AddOrderVO;
import com.example.enterprisecrm.VO.OrderVO;
import com.example.enterprisecrm.entity.Orders;
import icu.mhb.mybatisplus.plugln.base.mapper.JoinBaseMapper;
import icu.mhb.mybatisplus.plugln.base.service.JoinIService;

import java.text.ParseException;
import java.util.List;

public interface OrderService extends JoinIService<Orders> {
    //订单
    //增加
    public String insert(AddOrderVO orderVO) throws ParseException;

    //修改状态
    public int updateStatus(String oid,String status);

    //联表查询 分页查询所有订单
    public Page<OrderVO> selectByStatus(String status, int current, int size);
    //删除
    public int delete(String id);




    //修改
    public int update(Orders orders);

    //查询
    public Page<Orders> selectAll(int c, int size);
    public Orders select(String id);
}
