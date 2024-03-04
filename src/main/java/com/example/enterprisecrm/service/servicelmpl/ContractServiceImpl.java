package com.example.enterprisecrm.service.servicelmpl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.enterprisecrm.entity.Contract;
import com.example.enterprisecrm.entity.Orders;
import com.example.enterprisecrm.mapper.ContractMapper;
import com.example.enterprisecrm.service.ContractService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ContractServiceImpl extends ServiceImpl<ContractMapper, Contract> implements ContractService {
    @Resource
    private ContractMapper mapper;
    @Override
    public int insert(Contract contract) {
        int i = mapper.insert(contract);
        return i;
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
