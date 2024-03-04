package com.example.enterprisecrm.service.servicelmpl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.enterprisecrm.entity.Orders;
import com.example.enterprisecrm.entity.Service;
import com.example.enterprisecrm.mapper.ServiceMapper;
import com.example.enterprisecrm.service.ServiceService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Service
public class ServiceServiceImpl extends ServiceImpl<ServiceMapper, Service> implements ServiceService {

    @Resource
    private ServiceMapper mapper;
    @Override
    public int insert(Service service) {
        int i = mapper.insert(service);
        return i;
    }

    @Override
    public int delete(List<String> ids) {
        int i = mapper.deleteBatchIds(ids);
        return i;
    }

    @Override
    public int update(Service service) {
        int i = mapper.updateById(service);
        return i;
    }

    @Override
    public Page<Service> selectAll(int c, int size) {
        Page<Service> page = new Page<>();
        page.setSize(size);
        page.setCurrent(c);
        Page<Service> selectPage = mapper.selectPage(page, null);
        return selectPage;
    }

    @Override
    public Service select(String id) {
        Service service = mapper.selectById(id);
        return service;
    }
}
