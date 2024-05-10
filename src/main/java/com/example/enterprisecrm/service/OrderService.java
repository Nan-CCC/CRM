package com.example.enterprisecrm.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.enterprisecrm.VO.AddOrderVO;
import com.example.enterprisecrm.VO.Order2VO;
import com.example.enterprisecrm.VO.OrderVO;
import com.example.enterprisecrm.VO.Product2VO;
import com.example.enterprisecrm.entity.Orders;
import icu.mhb.mybatisplus.plugln.base.service.JoinIService;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface OrderService extends JoinIService<Orders> {
    //订单
    //增加
    public String insert(AddOrderVO orderVO) throws ParseException;

    //修改状态
    public int updateStatus(String oid,String status);

    //联表查询 分页查询所有订单
    public Page<OrderVO> selectByStatus(String status,int current, int size,String uid);
    //删除
    public int delete(String id);

    //按条件搜索
    public Page<OrderVO> selectByLike(int current, int size,String status,String column,String like,String uid);

    //获取员工月订单情况
    public List<Product2VO> selectByMonth(Date date, String uid);

    //按条件获取订单数据
    public List<List<Product2VO>> selectBychart(List<String> dates,String uid);

    public int update(Orders orders);
    //查询
    public Page<Orders> selectAll(int c, int size);
    public Orders select(String id);

    public Boolean selectIsOld(String cid);

    public List<Order2VO> selectHistory(String cid,String oid);
}
