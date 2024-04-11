package com.example.enterprisecrm.service.servicelmpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
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
        customer.setId("");
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
    public int updateOwner(String id, String uid) {
        UpdateWrapper<Customer> wrapper = new UpdateWrapper<>();

        int update = mapper.update(null, wrapper.eq("c_id",id).set("owner",uid));
        return update;
    }

    @Override
    public Page<Customer> selectPublicAll(int c, int size) {
        Page<Customer> page = new Page<>();
        page.setCurrent(c);
        page.setSize(size);
        QueryWrapper<Customer> wrapper = new QueryWrapper<>();
        Page<Customer> select = mapper.selectPage(page, wrapper.isNull("owner").or().eq("owner","").orderByDesc("c_id"));
        return select;
    }

    @Override
    public Page<Customer> selectUserAll(int c, int size, String id) {
        Page<Customer> page = new Page<>();
        page.setCurrent(c);
        page.setSize(size);
        QueryWrapper<Customer> wrapper = new QueryWrapper<>();
        Page<Customer> select = mapper.selectPage(page,wrapper.eq("owner",id).orderByDesc("c_id"));
        return select;
    }

    @Override
    public Customer select(String id) {
        Customer customer = mapper.selectById(id);
        return customer;
    }
}
