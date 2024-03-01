package com.example.enterprisecrm.controller;

import com.example.enterprisecrm.common.result.Result;
import com.example.enterprisecrm.common.result.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("document")
@AllArgsConstructor
@Api(tags = "DocumentApi")
public class DocumentController {

    @PostMapping("/uploadfile")
    @ApiOperation(value = "文件上传")
    public Result uploadfile(@RequestPart("file") MultipartFile multipartFile){
        System.out.println(multipartFile.getOriginalFilename());
        if(!multipartFile.isEmpty()){
            try{
                File file = new File("D:/" + multipartFile.getOriginalFilename());
                multipartFile.transferTo(file);
                System.out.println(file.getPath());
                return ResultUtil.success();
            } catch (IOException e) {
                throw new RuntimeException("上传失败"+e);
            }
        }else {
            System.out.println("请上传");
        }
        return ResultUtil.error();
    }
}
