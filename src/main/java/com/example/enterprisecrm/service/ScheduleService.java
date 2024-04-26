package com.example.enterprisecrm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.enterprisecrm.entity.Schedule;

import java.util.Date;
import java.util.List;

public interface ScheduleService extends IService<Schedule> {
    //插入
    public int insert(Schedule schedule);
    //查询某一天的日程
    public Schedule selectToday(String user, Date day);

    //查询用户所有日程
    public List<Schedule> selectall(String user);

    //删除
    public  int delete(String user,Date date);

    //修改
    public int update(String uid,Date date,String content);

    //修改状态
    public int updateStatus(String id,Integer status);
}
