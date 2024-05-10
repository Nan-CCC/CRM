package com.example.enterprisecrm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.enterprisecrm.entity.Marketing;
import icu.mhb.mybatisplus.plugln.base.mapper.JoinBaseMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketingMapper extends JoinBaseMapper<Marketing> {
}
