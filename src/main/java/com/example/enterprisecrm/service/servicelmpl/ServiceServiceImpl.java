package com.example.enterprisecrm.service.servicelmpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.enterprisecrm.VO.ServiceVO;
import com.example.enterprisecrm.entity.Marketing;
import com.example.enterprisecrm.entity.Orders;
import com.example.enterprisecrm.entity.Process;
import com.example.enterprisecrm.entity.Service;
import com.example.enterprisecrm.mapper.ProcessMapper;
import com.example.enterprisecrm.mapper.ServiceMapper;
import com.example.enterprisecrm.service.ServiceService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Service
public class ServiceServiceImpl extends ServiceImpl<ServiceMapper, Service> implements ServiceService {

    @Resource
    private ServiceMapper mapper;

    @Resource
    private ProcessMapper processMapper;
    @Override
    public int insert(Service service) {
        int i = mapper.insert(service);
        return i;
    }

    @Override
    public int delete(List<String> ids) {
        int i = mapper.deleteBatchIds(ids);
        return i;
    }

    @Override
    public int update(String id,String status) {
        UpdateWrapper<Service> serviceUpdateWrapper = new UpdateWrapper<>();
        serviceUpdateWrapper.eq("s_id",id)
                .set("s_status",status);
        int update = mapper.update(serviceUpdateWrapper);

        return update;
    }

    @Override
    public Page<Service> selectAll(int c, int size,String status,String type) {

        QueryWrapper<Service> wrapper = new QueryWrapper<>();
        if(type==""||type.equals("")){
            wrapper.and(qw->qw.eq("s_status","1").or().eq("s_status","2"))
                    .orderByDesc("s_create");
        }
        else {
            if(status=="1" ||status.equals("1")){
                wrapper.eq("s_type",type)
                        .and(qw->qw.eq("s_status","1").or().eq("s_status","2"))
                        .orderByDesc("s_create");
            }else{
                //待处理
                wrapper.eq("s_type",type)
                        .and(qw->qw.eq("s_status","0").or().eq("s_status",status))
                        .orderByDesc("s_create");
            }
        }

        Page<Service> page = new Page<>();
        page.setCurrent(c);
        page.setSize(size);
        Page<Service> servicePage = mapper.selectPage(page, wrapper);

        return servicePage;
    }

    @Override
    public Service select(String id) {
        Service service = mapper.selectById(id);
        return service;
    }

    @Override
    public List<Process> selectContent(String sid) {
        QueryWrapper<Process> processQueryWrapper = new QueryWrapper<>();
        processQueryWrapper.eq("service_id",sid);
        List<Process> processes = processMapper.selectList(processQueryWrapper);
        return processes;
    }

    @Override
    public int insertContent(Process process) {
        process.setChangeTime(new Date());
        int insert = processMapper.insert(process);
        return insert;
    }

    @Override
    public List<ServiceVO> selectListByCid(String cid, String type) {
        List<ServiceVO> serviceVOS=new ArrayList<>();
        QueryWrapper<Service> wrapper = new QueryWrapper<>();
        wrapper.eq("s_type",type)
                .eq("customer_id",cid)
                .and(qw->qw.eq("s_status",1).or().eq("s_status",2));
        List<Service> services = mapper.selectList(wrapper);
        for(int i=0;i<services.size();i++){
            ServiceVO serviceVO = new ServiceVO();
            serviceVO.setService(services.get(i));
            QueryWrapper<Process> processQueryWrapper = new QueryWrapper<>();
            processQueryWrapper.eq("service_id",services.get(i).getId());
            List<Process> nowProcess = processMapper.selectList(processQueryWrapper);
            serviceVO.setProcessList(nowProcess);
            serviceVOS.add(serviceVO);
        }
        return serviceVOS;
    }
}
