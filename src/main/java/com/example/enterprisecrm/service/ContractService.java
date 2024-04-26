package com.example.enterprisecrm.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.enterprisecrm.entity.Contract;
import com.example.enterprisecrm.entity.Orders;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

public interface ContractService extends IService<Contract> {
    //合同
    //增加

    public int insert(MultipartFile file, HttpServletRequest req, String oid, Date date,String status);

    //下载
    public String download(String oid);





    //删除
    public int delete(List<String> ids);
    //修改
    public int update(Contract contract);
    //查询
    public Page<Contract> selectAll(int c, int size);
    public Contract select(String id);
}
