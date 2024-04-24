package com.example.enterprisecrm.service.servicelmpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.enterprisecrm.entity.Platform;
import com.example.enterprisecrm.entity.Product;
import com.example.enterprisecrm.mapper.ProductMapper;
import com.example.enterprisecrm.service.ProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Resource
    private ProductMapper mapper;
    @Override
    public int insert(Product product) {
        int i = mapper.insert(product);
        return i;
    }

    @Override
    public int delete(List<String> ids) {
        int i = mapper.deleteBatchIds(ids);
        return i;
    }

    @Override
    public int update(Product product) {
        int i = mapper.updateById(product);
        return i;
    }

    @Override
    public Page<Product> selectAll(int c, int size) {
        Page<Product> page = new Page<>();
        page.setSize(size);
        page.setCurrent(c);
        Page<Product> selectPage = mapper.selectPage(page, null);
        return selectPage;
    }

    @Override
    public List<Product> selectAll() {
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.gt("pd_num",0);
        List<Product> products = mapper.selectList(wrapper);
        return products;

    }

    @Override
    public Product select(String id) {
        Product select = mapper.selectById(id);
        return select;
    }
}
