package com.example.enterprisecrm.service.servicelmpl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.enterprisecrm.entity.Platform;
import com.example.enterprisecrm.mapper.PlatformMapper;
import com.example.enterprisecrm.service.PlatformService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class PlatformServiceImpl extends ServiceImpl<PlatformMapper,Platform> implements PlatformService{
    @Resource
    private PlatformMapper mapper;
    @Override
    public int insert(Platform platform) {
        int i = mapper.insert(platform);
        return i;
    }

    @Override
    public int delete(List<String> ids) {
        int i = mapper.deleteBatchIds(ids);
        return i;
    }

    @Override
    public int update(Platform platform) {
        int i = mapper.updateById(platform);
        return i;
    }

    @Override
    public List<Platform> selectAll() {
        List<Platform> platforms = mapper.selectList(null);
        return platforms;
    }

    @Override
    public Platform select(String id) {
        Platform platform = mapper.selectById(id);
        return platform;
    }
}
