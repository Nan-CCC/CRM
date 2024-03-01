package com.example.enterprisecrm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.enterprisecrm.entity.Customer;
import com.example.enterprisecrm.entity.Marketing;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerMapper extends BaseMapper<Customer> {
}
