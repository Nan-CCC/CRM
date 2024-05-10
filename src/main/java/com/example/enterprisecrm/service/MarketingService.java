package com.example.enterprisecrm.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.enterprisecrm.VO.MarketVO;
import com.example.enterprisecrm.entity.Marketing;
import com.example.enterprisecrm.entity.Plan;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

public interface MarketingService extends IService<Marketing> {
    //营销活动
    //上传活动文件
    public String upload(MultipartFile file,HttpServletRequest req);
    //增加（一个）
    public String insertMartet(MarketVO marketVO);
    //修改
    public int updateMartet(Marketing marketing);
    //删除(多个)
    public int deleteMartet(String id);
    //查询（分页）
    public Page<Marketing> selectAll(int a,int b);

    public String download(String cid);
    //查询(一个)
    public Marketing selectMarket(String id);
}
