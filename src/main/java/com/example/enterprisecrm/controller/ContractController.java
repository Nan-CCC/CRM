package com.example.enterprisecrm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.enterprisecrm.common.Log.BusniessType;
import com.example.enterprisecrm.common.Log.MyLog;
import com.example.enterprisecrm.common.result.Result;
import com.example.enterprisecrm.common.result.ResultUtil;
import com.example.enterprisecrm.entity.Contract;
import com.example.enterprisecrm.service.ContractService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("contract")
@Api(tags = "ContractApi")
public class ContractController {
    @Resource
    private ContractService service;

    @PostMapping(value = "/add",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation("增加合同")
    @MyLog(title = "订单",optParam = "#{oid},#{date},#{status}",businessType = BusniessType.INSERT)
    public Result add(@RequestPart("file") MultipartFile file, HttpServletRequest request, @RequestParam  String oid, @RequestParam @DateTimeFormat(pattern ="yyyy-MM-dd") Date date,@RequestParam String status){
        int i = service.insert(file,request,oid,date,status);
        if(i>0){
            return ResultUtil.success(i);
        }
        return ResultUtil.error();
    }

    @GetMapping("/download")
    @ApiOperation("下载合同")
    public Result<String> download(HttpServletResponse response,@RequestParam String oid,HttpServletRequest request){
        //http://localhost:8088/api/file/HT0000000002.docx
        String download = service.download(oid);
        String[] split = download.split("/");
        String fileName=split[split.length-1];

        response.setCharacterEncoding(request.getCharacterEncoding());
        response.setContentType("application/octet-stream");
        ServletOutputStream outputStream = null;
        FileInputStream inputStream = null;
        try {
            //接受文件名，读取磁盘对应的文件，创建输入流对象
            inputStream = new FileInputStream(download);
            //2.获取响应对象的输出流
            outputStream = response.getOutputStream();

            //3.文件下载文件名的编码使用ISO-08859-1编码
            //我们需要将我们UTF-8的 filename转换ISO-8859-1编码
            //3.1先将字符串以UTF-8转换成字节数组
            byte[] bytes = fileName.getBytes("UTF-8");
            //3.2再将字节数组以 ISO-8859-1转换字符串
            fileName = new String(bytes, "ISO-8859-1");
            //4.响应的内容应该是以附件的形式响应给浏览器(设置响应头)
            response.setHeader("Content-Disposition", "attachment;filename="+fileName);
            //5.响应文件给浏览器
            IOUtils.copy(inputStream, outputStream);
            //刷新及关闭连接
            response.flushBuffer();
            inputStream.close();
            outputStream.close();

            return ResultUtil.success(download);
        } catch (Exception e) {
            throw new RuntimeException("下载失败");
        }


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
