package com.example.enterprisecrm.service.servicelmpl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.enterprisecrm.entity.Marketing;
import com.example.enterprisecrm.mapper.MarketingMapper;
import com.example.enterprisecrm.service.MarketingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MarketingServiceImpl extends ServiceImpl<MarketingMapper, Marketing> implements MarketingService {
    @Resource
    private MarketingMapper mapper;
    @Override
    public int insertMartet( Marketing marketing) {
        int insert = mapper.insert(marketing);
        return insert;
    }

    @Override
    public int updateMartet( Marketing marketing) {
        int i = mapper.updateById(marketing);
        return i;
    }

    @Override
    public int deleteMartet( List<String> ids) {
        int i = mapper.deleteBatchIds(ids);
        return i;
    }

    @Override
    public Page<Marketing> selectAll(int a,int b) {
        Page<Marketing> page = new Page<>();
        page.setCurrent(a);
        page.setSize(b);
        mapper.selectPage(page, null);
        return page;
    }

    @Override
    public Marketing selectMarket( String id) {
        Marketing marketing = mapper.selectById(id);
        return marketing;
    }
}
