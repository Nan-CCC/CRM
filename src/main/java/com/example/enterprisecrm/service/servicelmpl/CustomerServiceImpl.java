package com.example.enterprisecrm.service.servicelmpl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.enterprisecrm.entity.Customer;
import com.example.enterprisecrm.mapper.CustomerMapper;
import com.example.enterprisecrm.service.CustomerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {
    @Resource
    private CustomerMapper mapper;
    @Override
    public int insert(Customer customer) {
        int i = mapper.insert(customer);
        return i;
    }

    @Override
    public int delete(List<String> ids) {
        int i = mapper.deleteBatchIds(ids);
        return i;
    }

    @Override
    public int update(Customer customer) {
        int i = mapper.updateById(customer);
        return i;
    }

    @Override
    public Page<Customer> selectAll(int c, int size) {
        Page<Customer> page = new Page<>();
        page.setCurrent(c);
        page.setSize(size);
        Page<Customer> select = mapper.selectPage(page, null);
        return select;
    }

    @Override
    public Customer select(String id) {
        Customer customer = mapper.selectById(id);
        return customer;
    }
}
