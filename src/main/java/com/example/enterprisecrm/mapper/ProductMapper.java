package com.example.enterprisecrm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.enterprisecrm.entity.Product;
import com.example.enterprisecrm.entity.User;
import icu.mhb.mybatisplus.plugln.base.mapper.JoinBaseMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductMapper extends JoinBaseMapper<Product> {

}
