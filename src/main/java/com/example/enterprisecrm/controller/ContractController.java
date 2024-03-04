package com.example.enterprisecrm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.enterprisecrm.common.result.Result;
import com.example.enterprisecrm.common.result.ResultUtil;
import com.example.enterprisecrm.entity.Contract;
import com.example.enterprisecrm.entity.Orders;
import com.example.enterprisecrm.service.ContractService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("contract")
@Api(tags = "ContractApi")
public class ContractController {
    @Resource
    private ContractService service;

    @PostMapping("/add")
    @ApiOperation("增加合同")
    public Result add(@ModelAttribute Contract contract){
        int i = service.insert(contract);
        if(i>0){
            return ResultUtil.success(i);
        }
        return ResultUtil.error();
    }

    @PutMapping("/update")
    @ApiOperation("修改合同")
    public Result update(@ModelAttribute Contract contract){
        int i = service.update(contract);
        if(i>0){
            return ResultUtil.success(i);
        }
        return ResultUtil.error();
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除合同")
    public Result delete(@RequestParam List<String> ids){
        int i = service.delete(ids);
        if(i>0){
            return ResultUtil.success(i);
        }
        return ResultUtil.error();
    }

    @PostMapping("/queryall")
    @ApiOperation("查看所有合同")
    public Result queryAll(int c,int size){
        Page<Contract> page = service.selectAll(c, size);
        if(page!=null){
            return ResultUtil.success(page);
        }
        return ResultUtil.error();
    }

    @PostMapping("/query")
    @ApiOperation("查看合同")
    public Result query(@RequestParam String id){
        Contract select = service.select(id);
        if(select!=null){
            return ResultUtil.success(select);
        }
        return ResultUtil.error();
    }
}
