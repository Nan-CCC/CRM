package com.example.enterprisecrm.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.enterprisecrm.entity.Customer;
import com.example.enterprisecrm.entity.Platform;

import java.util.List;

public interface CustomerService extends IService<Customer> {
    //客户
    //增加
    public int insert(Customer customer);
    //删除
    public int delete(List<String> ids);

    //修改
    public int update(Customer customer);
    //查询
    public Page<Customer> selectAll(int c, int size);
    public Customer select(String id);
}
