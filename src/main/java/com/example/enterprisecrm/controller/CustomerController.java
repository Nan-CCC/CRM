package com.example.enterprisecrm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.enterprisecrm.common.Log.BusniessType;
import com.example.enterprisecrm.common.Log.MyLog;
import com.example.enterprisecrm.common.result.Result;
import com.example.enterprisecrm.common.result.ResultUtil;
import com.example.enterprisecrm.entity.Customer;
import com.example.enterprisecrm.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/customer")
@Api(tags = "CustonmerApi")
@AllArgsConstructor
public class CustomerController {
    @Resource
    private CustomerService service;

    @PostMapping("add")
    @ApiOperation("增加客户")
    @MyLog(title = "公海客户",optParam = "#{customer}",businessType = BusniessType.INSERT)
    public Result add(@ModelAttribute Customer customer){
        int i = service.insert(customer);
        if(i>0){
            return ResultUtil.success(i);
        }
        return ResultUtil.error();
    }

    @PutMapping("update")
    @ApiOperation("修改客户")
    @MyLog(title = "公海客户",optParam = "#{customer}",businessType = BusniessType.UPDATE)
    public Result update(@ModelAttribute Customer customer){
        int i = service.update(customer);
        if(i>0){
            return ResultUtil.success(i);
        }
        return ResultUtil.error();
    }

    @PutMapping("updateowner")
    @ApiOperation("修改客户拥有者")
    @MyLog(title = "公海客户",optParam = "#{cid,uid}",businessType = BusniessType.UPDATE)
    public Result updateOwner(@RequestParam String cid,@RequestParam String uid){
        int i = service.updateOwner(cid,uid);
        if(i>0){
            return ResultUtil.success(i);
        }
        return ResultUtil.error();
    }

    @DeleteMapping("delete")
    @ApiOperation("删除客户")
    @MyLog(title = "公海客户",optParam = "#{ids}",businessType = BusniessType.DELETE)
    public Result delete(@RequestParam List<String> ids){
        int i = service.delete(ids);
        if(i>0){
            return ResultUtil.success(i);
        }
        return ResultUtil.error();
    }

    @PostMapping("queryall")
    @ApiOperation("查询用户全部的客户")
    public Result query(@RequestParam int c,@RequestParam int size,@RequestParam String uid){
        Page<Customer> page = service.selectAll(c, size, uid);
        return ResultUtil.success(page);

    }

    @PostMapping("query")
    @ApiOperation("查询客户")
    public Result query(@RequestParam String id){
        Customer select = service.select(id);
        if(select!=null){
            return ResultUtil.success(select);
        }
        return ResultUtil.error();
    }

    @PostMapping("search")
    @ApiOperation("模糊搜索客户")
    public Result search(@RequestParam int current,@RequestParam int size,@RequestParam String column,@RequestParam String like,@RequestParam String owner){
        Page<Customer> page = service.selectLike(current, size, column, like,owner);
        return ResultUtil.success(page);
    }

    @GetMapping("selectList")
    @ApiOperation("获取全部用户")
    public Result selectList(@RequestParam  String uid){
        List<Customer> customers = service.selectList(uid);
        return ResultUtil.success(customers);
    }

    @GetMapping("noOrderList")
    @ApiOperation("获取近一年无订单客户数量")
    public Result selectNoOrder(@RequestParam  String uid){
        Integer i = service.noOrderNum(uid);
        return ResultUtil.success("success",i);
    }

    @GetMapping("pie")
    @ApiOperation("获取用户来源分布")
    public Result selectByPie(@RequestParam String uid,@RequestParam String pid){
        Integer pie = service.getPie(uid, pid);
        return ResultUtil.success("success",pie);
    }

}
