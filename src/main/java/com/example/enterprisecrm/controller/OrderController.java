package com.example.enterprisecrm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.enterprisecrm.common.result.Result;
import com.example.enterprisecrm.common.result.ResultUtil;
import com.example.enterprisecrm.entity.Orders;
import com.example.enterprisecrm.entity.Product;
import com.example.enterprisecrm.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("order")
@Api(tags = "OrderApi")
public class OrderController {
    @Resource
    private OrderService service;

    @PostMapping("/add")
    @ApiOperation("增加订单")
    public Result add(@ModelAttribute Orders orders){
        int i = service.insert(orders);
        if(i>0){
            return ResultUtil.success(i);
        }
        return ResultUtil.error();
    }

    @PutMapping("/update")
    @ApiOperation("修改订单")
    public Result update(@ModelAttribute Orders orders){
        int i = service.update(orders);
        if(i>0){
            return ResultUtil.success(i);
        }
        return ResultUtil.error();
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除订单")
    public Result delete(@RequestParam List<String> ids){
        int i = service.delete(ids);
        if(i>0){
            return ResultUtil.success(i);
        }
        return ResultUtil.error();
    }

    @PostMapping("/queryall")
    @ApiOperation("查看所有订单")
    public Result queryAll(int c,int size){
        Page<Orders> page = service.selectAll(c, size);
        if(page!=null){
            return ResultUtil.success(page);
        }
        return ResultUtil.error();
    }

    @PostMapping("/query")
    @ApiOperation("查看订单")
    public Result query(@RequestParam String id){
        Orders select = service.select(id);
        if(select!=null){
            return ResultUtil.success(select);
        }
        return ResultUtil.error();
    }

}
