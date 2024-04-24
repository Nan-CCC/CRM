package com.example.enterprisecrm.service.servicelmpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.enterprisecrm.common.result.ResultUtil;
import com.example.enterprisecrm.entity.Contract;
import com.example.enterprisecrm.entity.Orders;
import com.example.enterprisecrm.mapper.ContractMapper;
import com.example.enterprisecrm.mapper.OrderMapper;
import com.example.enterprisecrm.service.ContractService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class ContractServiceImpl extends ServiceImpl<ContractMapper, Contract> implements ContractService {
    @Resource
    private ContractMapper mapper;

    @Resource
    private OrderMapper orderMapper;
    @Value("${file.uploadFolder}")
    private String uploadFolder;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(MultipartFile file,HttpServletRequest req,String oid,Date date,String status) {
        try{
            //新建合同
            Contract contract = new Contract();
            contract.setOid(oid);
            contract.setSign(date);
            int i = mapper.insert(contract);
            //查询合同id
            QueryWrapper<Contract> wrapper = new QueryWrapper<>();
            wrapper.eq("order_id",oid);
            Contract one = mapper.selectOne(wrapper);
            String ctId=one.getId();
            //加入order
            UpdateWrapper<Orders> orderUpdateWrapper = new UpdateWrapper<>();
            orderUpdateWrapper.eq("o_id",oid)
                    .set("contract_id",ctId)
                    .set("o_status",status);
            int update1 = orderMapper.update(orderUpdateWrapper);
            //获取文件原始名
            String originalFilename = file.getOriginalFilename();
            //获取文件后缀
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            //生成新文件名
            String newFileName=ctId+extension;
            File dateDir = new File(uploadFolder);
            //如果不存在就创建
            if(!dateDir.exists()){
                dateDir.mkdirs();
            }
            try{
                file.transferTo(new File(dateDir,newFileName));
            }catch (IOException e){
                e.printStackTrace();
            }
            String invented_address =
                    req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort() + "/api/file/" + newFileName;
            //虚拟路径加入数据库
            UpdateWrapper<Contract> wrapper2 = new UpdateWrapper<>();
            wrapper2.eq("ct_id",ctId)
                            .set("ct_content",invented_address);
            int update = mapper.update(wrapper2);
            return update;
        }catch (Exception e){
            throw new RuntimeException("新增合同失败");
        }
    }

    @Override
    public String download(String oid) {
        QueryWrapper<Contract> wrapper = new QueryWrapper<>();
        wrapper.eq("order_id",oid);
        Contract contract = mapper.selectOne(wrapper);
        String[] split = contract.getContent().split("/");
        String fileName=split[split.length-1];

        String filePath=uploadFolder+fileName;
        return filePath;
    }

    @Override
    public int delete(List<String> ids) {
        int i = mapper.deleteBatchIds(ids);
        return i;
    }

    @Override
    public int update(Contract contract) {
        int i = mapper.updateById(contract);
        return i;
    }

    @Override
    public Page<Contract> selectAll(int c, int size) {
        Page<Contract> page = new Page<>();
        page.setSize(size);
        page.setCurrent(c);
        Page<Contract> selectPage = mapper.selectPage(page, null);
        return selectPage;
    }

    @Override
    public Contract select(String id) {
        Contract contract = mapper.selectById(id);
        return contract;
    }
}
