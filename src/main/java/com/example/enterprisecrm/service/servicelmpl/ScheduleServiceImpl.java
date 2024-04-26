package com.example.enterprisecrm.service.servicelmpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.enterprisecrm.entity.Schedule;
import com.example.enterprisecrm.mapper.ScheduleMapper;
import com.example.enterprisecrm.service.ScheduleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

@Service
public class ScheduleServiceImpl extends ServiceImpl<ScheduleMapper, Schedule> implements ScheduleService {
    @Resource
    private ScheduleMapper mapper;
    @Override
    public int insert(Schedule schedule) {
        //增加弹窗，默认为未完成-0
        schedule.setStatus(0);
        int insert = mapper.insert(schedule);
        return insert;
    }

    @Override
    public Schedule selectToday(String user,Date date) {
        QueryWrapper<Schedule> wrapper = new QueryWrapper<>();
        wrapper.eq("date",date)
                .eq("user",user);
        Schedule schedule = mapper.selectOne(wrapper);
        if(schedule!=null){
            return schedule;
        }
        return null;
    }

    @Override
    public List<Schedule> selectall(String user) {
        QueryWrapper<Schedule> wrapper = new QueryWrapper<>();
//        wrapper.select("date").eq("user",user);
//        List<Date> dates = mapper.selectObjs(wrapper);
        wrapper.eq("user",user);
        List<Schedule> list = mapper.selectList(wrapper);
        return list;

    }

    @Override
    public int delete(String user, Date date) {
        QueryWrapper<Schedule> wrapper = new QueryWrapper<>();
        wrapper.eq("user",user)
                .eq("date",date);
        List<Schedule> list = mapper.selectList(wrapper);
        if(list.size()==1){
            int delete = mapper.delete(wrapper);
            return delete;
        }
        return 0;
    }

    @Override
    public int update(String uid,Date date,String content) {
        UpdateWrapper<Schedule> wrapper = new UpdateWrapper<>();
        wrapper.eq("user",uid)
                .eq("date",date)
                .set("content",content);
        int update = mapper.update(wrapper);
        return update;
    }

    @Override
    public int updateStatus(String id, Integer status) {
        UpdateWrapper<Schedule> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",id)
                .set("status",status);
        int update = mapper.update(wrapper);
        return update;
    }


}
