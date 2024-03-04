package com.example.enterprisecrm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.enterprisecrm.common.result.Result;
import com.example.enterprisecrm.common.result.ResultUtil;
import com.example.enterprisecrm.entity.Orders;
import com.example.enterprisecrm.entity.Service;
import com.example.enterprisecrm.service.ServiceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("service")
@Api(tags = "ServiceApi")
public class ServiceController {
    @Resource
    private ServiceService service;

    @PostMapping("/add")
    @ApiOperation("增加服务")
    public Result add(@ModelAttribute Service service2){
        int i = service.insert(service2);
        if(i>0){
            return ResultUtil.success(i);
        }
        return ResultUtil.error();
    }

    @PutMapping("/update")
    @ApiOperation("修改服务")
    public Result update(@ModelAttribute Service service2){
        int i = service.update(service2);
        if(i>0){
            return ResultUtil.success(i);
        }
        return ResultUtil.error();
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除服务")
    public Result delete(@RequestParam List<String> ids){
        int i = service.delete(ids);
        if(i>0){
            return ResultUtil.success(i);
        }
        return ResultUtil.error();
    }

    @PostMapping("/queryall")
    @ApiOperation("查看所有服务")
    public Result queryAll(int c,int size){
        Page<Service> page = service.selectAll(c, size);
        if(page!=null){
            return ResultUtil.success(page);
        }
        return ResultUtil.error();
    }

    @PostMapping("/query")
    @ApiOperation("查看服务")
    public Result query(@RequestParam String id){
        Service select = service.select(id);
        if(select!=null){
            return ResultUtil.success(select);
        }
        return ResultUtil.error();
    }
}
