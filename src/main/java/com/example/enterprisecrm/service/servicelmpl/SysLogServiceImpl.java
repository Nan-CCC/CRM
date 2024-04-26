package com.example.enterprisecrm.service.servicelmpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.enterprisecrm.entity.SysLog;
import com.example.enterprisecrm.mapper.SysLogMapper;
import com.example.enterprisecrm.service.SysLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService  {

    @Resource
    private SysLogMapper mapper;
    @Override
    public int insert(SysLog sysLog) {
        int insert = mapper.insert(sysLog);
        return insert;
    }
}
