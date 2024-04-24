package com.example.enterprisecrm.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.enterprisecrm.entity.Platform;
import com.example.enterprisecrm.entity.Product;

import java.util.List;

public interface ProductService extends IService<Product> {
    //产品
    //增加
    public int insert(Product product);
    //删除
    public int delete(List<String> ids);
    //修改
    public int update(Product product);
    //查询
    public Page<Product> selectAll(int c, int size);
    public List<Product> selectAll();
    public Product select(String id);
}
