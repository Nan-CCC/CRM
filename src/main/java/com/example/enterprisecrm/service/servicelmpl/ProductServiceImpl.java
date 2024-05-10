package com.example.enterprisecrm.service.servicelmpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.enterprisecrm.entity.Implement;
import com.example.enterprisecrm.entity.Include;
import com.example.enterprisecrm.entity.Platform;
import com.example.enterprisecrm.entity.Product;
import com.example.enterprisecrm.mapper.ImplementMapper;
import com.example.enterprisecrm.mapper.IncludeMapper;
import com.example.enterprisecrm.mapper.ProductMapper;
import com.example.enterprisecrm.service.ProductService;
import icu.mhb.mybatisplus.plugln.core.JoinLambdaWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Resource
    private ProductMapper mapper;
    @Resource
    private IncludeMapper includeMapper;
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

    @Override
    public List<Product> selectTop() {
        List<Product> products=new ArrayList<>();
        LambdaQueryWrapper<Include> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Include::getPid,Include::getTopNum)
                .groupBy(Include::getPid)
                .orderByDesc(Include::getTopNum)
                .last("limit 20");
        List<Include> includes = includeMapper.selectList(wrapper);
        for(int i=0;i<includes.size();i++){
            Product product = new Product();
            Include include = includes.get(i);

            QueryWrapper<Product> productQueryWrapper = new QueryWrapper<>();
            productQueryWrapper.eq("pd_id",include.getPid());
            Product product1 = mapper.selectOne(productQueryWrapper);

            product1.setNum(include.getTopNum());

            products.add(product1);
        }
        return products;
    }
}
