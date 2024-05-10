package com.example.enterprisecrm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.enterprisecrm.VO.MarketVO;
import com.example.enterprisecrm.common.Log.BusniessType;
import com.example.enterprisecrm.common.Log.MyLog;
import com.example.enterprisecrm.common.result.Result;
import com.example.enterprisecrm.common.result.ResultUtil;
import com.example.enterprisecrm.entity.Marketing;
import com.example.enterprisecrm.service.MarketingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.util.List;


@RestController
@RequestMapping("market")
@AllArgsConstructor
@Api(tags = "MarketApi") //swagger 接口说明
public class MarketingController {
    @Resource
    private MarketingService service;

    @PostMapping("/upload")
    @ApiOperation("上传活动文件")
    public Result<String> upload(@RequestPart MultipartFile file,HttpServletRequest request){
        String upload = service.upload(file, request);
        if(upload.length()!=0){
            return ResultUtil.success(upload);
        }
        return ResultUtil.error();
    }

    @PostMapping("/add")
    @ApiOperation(value = "增加活动")
    //@MyLog(title = "活动",optParam = "#{data}",businessType = BusniessType.INSERT)
    public Result<String> add(@RequestBody MarketVO data){
        String s = service.insertMartet(data);

        return ResultUtil.success("success",s);

    }

    @PutMapping("/update")
    @ApiOperation(value = "修改活动")
    public Result update(@ModelAttribute Marketing market){
        int i = service.updateMartet(market);
        if(i>0){
            return ResultUtil.success(i);
        }
        return ResultUtil.error(i);
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "删除活动")
    public Result delete(@RequestParam String id){
        int i = service.deleteMartet(id);
        if(i>0){
            return ResultUtil.success(i);
        }
        return ResultUtil.error(i);
    }

    @PostMapping("/queryall")
    @ApiOperation(value = "查询全部活动")
    public Result queryAll(int c,int size){
        Page<Marketing> marketingPage = service.selectAll(c, size);
        if(marketingPage!=null){
            return ResultUtil.success(marketingPage);
        }
        return ResultUtil.error();
    }

    @PostMapping("/query")
    @ApiOperation(value = "查询活动")
    public Result query(@RequestParam String id){
        Marketing marketing = service.selectMarket(id);
        if(marketing!=null){
            return ResultUtil.success(marketing);
        }
        return ResultUtil.error();
    }

    @GetMapping("/download")
    @ApiOperation("下载活动方案")
    public Result<String> download(HttpServletResponse response, @RequestParam String mid, HttpServletRequest request){
        //http://localhost:8088/api/file/HT0000000002.docx
        String download = service.download(mid);
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

}
