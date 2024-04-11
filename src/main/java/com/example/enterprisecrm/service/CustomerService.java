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

    //修改拥有者
    public int updateOwner(String id,String uid);

    //查询公海全部
    public Page<Customer> selectPublicAll(int c, int size);

    //查询当前员工全部
    public Page<Customer> selectUserAll(int c, int size,String id);
    public Customer select(String id);
}
