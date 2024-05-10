package com.example.enterprisecrm.mapper;

import com.example.enterprisecrm.entity.Include;
import com.example.enterprisecrm.entity.Orders;
import icu.mhb.mybatisplus.plugln.base.mapper.JoinBaseMapper;
import icu.mhb.mybatisplus.plugln.core.JoinLambdaWrapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface OrderMapper extends JoinBaseMapper<Orders> {

}
