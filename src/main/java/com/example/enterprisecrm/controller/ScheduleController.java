package com.example.enterprisecrm.controller;

import com.example.enterprisecrm.common.Log.BusniessType;
import com.example.enterprisecrm.common.Log.MyLog;
import com.example.enterprisecrm.common.result.Result;
import com.example.enterprisecrm.common.result.ResultUtil;
import com.example.enterprisecrm.entity.Schedule;
import com.example.enterprisecrm.service.ScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("schedule")
@Api(tags = "ScheduleApi")
public class ScheduleController {
    @Resource
    private ScheduleService service;

    @PostMapping("/add")
    @ApiOperation("增加日程")
    @MyLog(title = "日程",optParam = "#{schedule}",businessType = BusniessType.INSERT)
    public Result<Schedule> add(@RequestBody Schedule schedule){
        int insert = service.insert(schedule);
        if(insert!=0){
            return ResultUtil.success();
        }
        return ResultUtil.error();
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除日程")
    @MyLog(title = "日程" ,optParam = "#{uid,date}",businessType = BusniessType.DELETE)
    public Result<Schedule> delete(@RequestParam String uid,@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        int delete = service.delete(uid, date);
        if(delete!=0){
            return ResultUtil.success();

        }
        return ResultUtil.error();
    }

    @PutMapping("/update")
    @ApiOperation("修改日程")
    @MyLog(title = "日程" ,optParam = "#{uid,date,content}",businessType = BusniessType.UPDATE)
    public Result<Schedule> update(@RequestParam String uid,@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,@RequestParam String content){
        int update = service.update(uid,date, content);
        if(update==0){
            return ResultUtil.error();
        }
        return ResultUtil.success();
    }

    @PutMapping("/update2")
    @ApiOperation("修改日程状态")
    @MyLog(title = "日程" ,optParam = "#{id,status}",businessType = BusniessType.UPDATE)
    public Result<Schedule> update(@RequestParam String id,@RequestParam Integer status){
        int update = service.updateStatus(id,status);
        if(update==0){
            return ResultUtil.error();
        }
        return ResultUtil.success();
    }
    @PostMapping("/getToday")
    @ApiOperation("查询当前用户某天的日程")
    public Result<Schedule> selectToday(@RequestParam String uid,@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date day){
        Schedule schedule = service.selectToday(uid, day);
        return ResultUtil.success(schedule);
    }
    @PostMapping("/queryall")
    @ApiOperation("查询所有日程")
    public Result<List<Schedule>> queryAll(@RequestParam String uid){
        List<Schedule> selectall = service.selectall(uid);
        return ResultUtil.success(selectall);
    }

}
