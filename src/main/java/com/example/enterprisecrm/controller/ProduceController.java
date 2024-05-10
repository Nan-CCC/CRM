package com.example.enterprisecrm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.enterprisecrm.common.result.Result;
import com.example.enterprisecrm.common.result.ResultUtil;
import com.example.enterprisecrm.entity.Implement;
import com.example.enterprisecrm.entity.Include;
import com.example.enterprisecrm.entity.Platform;
import com.example.enterprisecrm.entity.Product;
import com.example.enterprisecrm.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("produce")
@Api(tags = "ProduceApi")
public class ProduceController {
    @Resource
    private ProductService service;

    @PostMapping("/add")
    @ApiOperation("增加产品")
    public Result add(@ModelAttribute Product product){
        int i = service.insert(product);
        if(i>0){
            return ResultUtil.success(i);
        }
        return ResultUtil.error();
    }

    @PutMapping("/update")
    @ApiOperation("修改产品")
    public Result update(@ModelAttribute Product product){
        int i = service.update(product);
        if(i>0){
            return ResultUtil.success(i);
        }
        return ResultUtil.error();
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除产品")
    public Result delete(@RequestParam List<String> ids){
        int i = service.delete(ids);
        if(i>0){
            return ResultUtil.success(i);
        }
        return ResultUtil.error();
    }

    @PostMapping("/queryallpage")
    @ApiOperation("查看所有产品（分页）")
    public Result queryAll(int c,int size){
        Page<Product> productPage = service.selectAll(c, size);
        if(productPage!=null){
            return ResultUtil.success(productPage);
        }
        return ResultUtil.error();
    }

    @GetMapping("/queryall")
    @ApiOperation("查看所有产品")
    public Result queryAll(){
        List<Product> products = service.selectAll();
        return ResultUtil.success(products);
    }

    @PostMapping("/query")
    @ApiOperation("查看产品")
    public Result query(@RequestParam String id){
        Product select = service.select(id);
        if(select!=null){
            return ResultUtil.success(select);
        }
        return ResultUtil.error();
    }

    @GetMapping("top")
    @ApiOperation("热销产品")
    public Result getTop(){
        List<Product> list = service.selectTop();
        return ResultUtil.success(list);
    }
}
