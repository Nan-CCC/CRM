package com.example.enterprisecrm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    public Result add(@ModelAttribute Customer customer){
        int i = service.insert(customer);
        if(i>0){
            return ResultUtil.success(i);
        }
        return ResultUtil.error();
    }

    @PutMapping("update")
    @ApiOperation("修改客户")
    public Result update(@ModelAttribute Customer customer){
        int i = service.update(customer);
        if(i>0){
            return ResultUtil.success(i);
        }
        return ResultUtil.error();
    }

    @DeleteMapping("delete")
    @ApiOperation("删除客户")
    public Result delete(@RequestParam List<String> ids){
        int i = service.delete(ids);
        if(i>0){
            return ResultUtil.success(i);
        }
        return ResultUtil.error();
    }

    @PostMapping("queryall")
    @ApiOperation("查询全部")
    public Result query(int c,int size){
        Page<Customer> page = service.selectAll(c, size);
        if(page!=null){
            return ResultUtil.success(page);
        }
        return ResultUtil.error();
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

}
