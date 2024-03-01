package com.example.enterprisecrm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.enterprisecrm.common.result.Result;
import com.example.enterprisecrm.common.result.ResultUtil;
import com.example.enterprisecrm.entity.Marketing;
import com.example.enterprisecrm.service.MarketingService;
import com.example.enterprisecrm.service.servicelmpl.MarketingServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("market")
@AllArgsConstructor
@Api(tags = "MarketApi") //swagger 接口说明
public class MarketingController {
    @Resource
    private MarketingService service;

    @PostMapping("/add")
    @ApiOperation(value = "增加活动")
    public Result add(@ModelAttribute Marketing market){
        int i = service.insertMartet(market);
        if(i>0){
            return ResultUtil.success(i);
        }
        return ResultUtil.error(i);
    }

    @PutMapping("/update")
    @ApiOperation(value = "修改活动")
    public Result update(@ModelAttribute Marketing market){
        int i = service.updateMartet(market);
        if(i>0){
            return ResultUtil.success(i);
        }
        return ResultUtil.error(i);
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "删除活动")
    public Result delete(@RequestParam List<String> ids){
        int i = service.deleteMartet(ids);
        if(i>0){
            return ResultUtil.success(i);
        }
        return ResultUtil.error(i);
    }

    @PostMapping("/queryall")
    @ApiOperation(value = "查询全部活动")
    public Result queryAll(int c,int size){
        Page<Marketing> marketingPage = service.selectAll(c, size);
        if(marketingPage!=null){
            return ResultUtil.success(marketingPage);
        }
        return ResultUtil.error();
    }

    @PostMapping("/query")
    @ApiOperation(value = "查询活动")
    public Result query(@RequestParam String id){
        Marketing marketing = service.selectMarket(id);
        if(marketing!=null){
            return ResultUtil.success(marketing);
        }
        return ResultUtil.error();
    }
}
