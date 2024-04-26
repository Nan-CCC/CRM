package com.example.enterprisecrm.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.enterprisecrm.entity.Orders;
import icu.mhb.mybatisplus.plugln.base.mapper.JoinBaseMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMapper extends JoinBaseMapper<Orders> {

}
