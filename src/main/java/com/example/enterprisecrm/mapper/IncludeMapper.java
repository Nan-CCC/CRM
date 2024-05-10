package com.example.enterprisecrm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.enterprisecrm.entity.Include;
import com.example.enterprisecrm.entity.User;
import icu.mhb.mybatisplus.plugln.base.mapper.JoinBaseMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface IncludeMapper extends JoinBaseMapper<Include> {

}
