package com.example.enterprisecrm.service.servicelmpl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.enterprisecrm.entity.Orders;
import com.example.enterprisecrm.mapper.OrderMapper;
import com.example.enterprisecrm.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Orders> implements OrderService {
    @Resource
    private OrderMapper mapper;
    @Override
    public int insert(Orders orders) {
        int i = mapper.insert(orders);
        return i;
    }

    @Override
    public int delete(List<String> ids) {
        int i = mapper.deleteBatchIds(ids);
        return i;
    }

    @Override
    public int update(Orders orders) {
        int i = mapper.updateById(orders);
        return i;
    }

    @Override
    public Page<Orders> selectAll(int c, int size) {
        Page<Orders> page = new Page<>();
        page.setSize(size);
        page.setCurrent(c);
        Page<Orders> selectPage = mapper.selectPage(page, null);
        return selectPage;
    }

    @Override
    public Orders select(String id) {
        Orders orders = mapper.selectById(id);
        return orders;
    }
}
