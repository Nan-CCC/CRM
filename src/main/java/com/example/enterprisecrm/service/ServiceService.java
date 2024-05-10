package com.example.enterprisecrm.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.enterprisecrm.VO.ServiceVO;
import com.example.enterprisecrm.entity.Orders;
import com.example.enterprisecrm.entity.Process;
import com.example.enterprisecrm.entity.Service;

import java.util.List;

public interface ServiceService extends IService<Service> {
    //服务
    //增加
    public int insert(Service service);
    //删除
    public int delete(List<String> ids);
    //修改
    public int update(String id,String status);
    //查询
    public Page<Service> selectAll(int c, int size,String status,String type);
    public Service select(String id);

    public List<Process> selectContent(String sid);

    public int insertContent(Process process);

    public List<ServiceVO> selectListByCid(String cid, String type);
}
