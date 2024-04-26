package com.example.enterprisecrm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.enterprisecrm.entity.SysLog;

public interface SysLogService extends IService<SysLog> {

    //插入
    public int insert(SysLog sysLog);
}
