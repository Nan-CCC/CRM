package com.example.enterprisecrm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.enterprisecrm.common.result.Result;
import com.example.enterprisecrm.common.result.ResultUtil;
import com.example.enterprisecrm.entity.Platform;
import com.example.enterprisecrm.service.PlatformService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("platform")
@Api(tags = "PlatformApi")
public class PlatformController {
    @Resource
    private PlatformService service;

    @PostMapping("/add")
    @ApiOperation("增加平台")
    public Result add(@ModelAttribute Platform platform){
        int i = service.insert(platform);
        if(i>0){
            return ResultUtil.success(i);
        }
        return ResultUtil.error();
    }

    @PutMapping("/update")
    @ApiOperation("修改平台")
    public Result update(@ModelAttribute Platform platform){
        int i = service.update(platform);
        if(i>0){
            return ResultUtil.success(i);
        }
        return ResultUtil.error();
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除平台")
    public Result delete(@RequestParam List<String> ids){
        int i = service.delete(ids);
        if(i>0){
            return ResultUtil.success(i);
        }
        return ResultUtil.error();
    }

    @PostMapping("/queryall")
    @ApiOperation("查看所有平台")
    public Result queryAll(int c,int size){
        Page<Platform> platformPage = service.selectAll(c, size);
        if(platformPage!=null){
            return ResultUtil.success(platformPage);
        }
        return ResultUtil.error();
    }

    @PostMapping("/query")
    @ApiOperation("查看平台")
    public Result query(@RequestParam String id){
        Platform select = service.select(id);
        if(select!=null){
            return ResultUtil.success(select);
        }
        return ResultUtil.error();
    }
}
