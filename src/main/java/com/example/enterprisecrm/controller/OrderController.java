package com.example.enterprisecrm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.enterprisecrm.VO.AddOrderVO;

import com.example.enterprisecrm.VO.OrderVO;
import com.example.enterprisecrm.common.Log.BusniessType;
import com.example.enterprisecrm.common.Log.MyLog;
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
import java.text.ParseException;
import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("order")
@Api(tags = "OrderApi")
public class OrderController {
    @Resource
    private OrderService service;

    //@ModelAttribute 在参数上，是把对象的属性分别绑到方法参数上
    @PostMapping("/add")
    @ApiOperation("增加订单")
    @MyLog(title = "订单",optParam = "#{orderVO}",businessType = BusniessType.INSERT)
    public Result add(@RequestBody AddOrderVO orderVO) throws ParseException {
        String insert = service.insert(orderVO);
        if(insert.equals("")||insert==null){
            return ResultUtil.error();
        }
        return ResultUtil.success("success",insert);
    }

    @PutMapping("/updateStatus")
    @ApiOperation("修改订单状态")
    @MyLog(title = "订单",optParam = "",businessType = BusniessType.UPDATE)
    public Result updateStatus(@RequestParam String oid,@RequestParam String status){
        int i = service.updateStatus(oid,status);
        if(i>0){
            return ResultUtil.success(i);
        }
        return ResultUtil.error();
    }

    @PostMapping("orderbystatus")
    @ApiOperation("根据状态查看订单")
    public Result selectByStatus(@RequestParam String status,@RequestParam int current,@RequestParam int size){
        Page<OrderVO> voPage = service.selectByStatus(status, current, size);
        return ResultUtil.success(voPage);
    }

    @PostMapping("orderbylike")
    @ApiOperation("根据条件查询订单")
    public Result selectByLike(@RequestParam int current,@RequestParam int size,@RequestParam String status,@RequestParam String column,@RequestParam String like){
        Page<OrderVO> voPage = service.selectByLike(current, size,status, column, like);
        return ResultUtil.success(voPage);
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
    @MyLog(title = "订单",optParam = "#{id}",businessType =BusniessType.DELETE)
    public Result delete(@RequestParam String id){
        int i = service.delete(id);
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
