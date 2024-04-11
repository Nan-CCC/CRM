package com.example.enterprisecrm.controller;

import com.example.enterprisecrm.common.result.Result;
import com.example.enterprisecrm.common.result.ResultUtil;
import com.example.enterprisecrm.entity.Menu;
import com.example.enterprisecrm.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("menu")
@AllArgsConstructor
@Api(tags = "MenuApi") //swagger 接口说明
public class MenuController {
    @Resource
    MenuService service;

    @PostMapping("/queryall")
    @ApiOperation(value = "所有菜单")
    public Result queryAll(@RequestParam String role){
        List<Menu> menus = service.selectAllByRole(role);
        if(menus!=null){
            return ResultUtil.success(menus);
        }
        return ResultUtil.error();
    }
}
