package com.example.enterprisecrm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.enterprisecrm.entity.Menu;
import com.example.enterprisecrm.entity.Orders;

import java.util.List;

public interface MenuService extends IService<Menu> {
    public List<Menu> selectAllByRole(String role);
}
