package com.example.enterprisecrm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.enterprisecrm.entity.Manage;
import com.example.enterprisecrm.entity.Menu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuMapper extends BaseMapper<Menu> {


}
