package com.example.enterprisecrm.service.servicelmpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.enterprisecrm.VO.AddOrderVO;
import com.example.enterprisecrm.VO.ProductVO;
import com.example.enterprisecrm.entity.Include;
import com.example.enterprisecrm.entity.Orders;
import com.example.enterprisecrm.mapper.IncludeMapper;
import com.example.enterprisecrm.mapper.OrderMapper;
import com.example.enterprisecrm.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Orders> implements OrderService {
    @Resource
    private OrderMapper mapper;

    @Resource
    private IncludeMapper includeMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String insert(AddOrderVO orderVO) throws ParseException {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String create = format.format(date);
        Date parse = format.parse(create);
        Orders orders = new Orders();
        //状态
        orders.setStatus(orderVO.getStatus());
        //创建时间
        orders.setCreateTime(parse);
        //逻辑存在  1-逻辑删除
        orders.setLogical(0);
        orders.setProvince(orderVO.getProvince());
        orders.setCity(orderVO.getCity());
        orders.setDistrict(orderVO.getDistrict());
        orders.setAddress(orderVO.getAddress());
        orders.setUid(orderVO.getUid());
        orders.setCid(orderVO.getCid());
        String newId=null;
        try{
            int insert = mapper.insert(orders);
            if(insert!=0){
                QueryWrapper<Orders> wrapper = new QueryWrapper<>();
                wrapper.eq("o_create",create)
                        .eq("user_id",orders.getUid());
                List<Orders> list = mapper.selectList(wrapper);
                newId = list.get(0).getId();
                List<ProductVO> proList = orderVO.getProList();
                for (ProductVO productVO:orderVO.getProList()){
                    System.out.println(productVO);
                    Include include = new Include(null,newId,productVO.getPid(),productVO.getNum());
                    int insert2 = includeMapper.insert(include);
                }
            }
            return newId;
        }catch (Exception e){
            throw new RuntimeException("新增订单系列失败");
        }

    }

    @Override
    public int updateStatus(String oid,String status) {
        UpdateWrapper<Orders> wrapper = new UpdateWrapper<>();
        wrapper.eq("o_id",oid)
                .set("o_status",status);
        int update = mapper.update(wrapper);
        return update;
    }

    @Override
    public int delete(List<String> ids) {
        int i = mapper.deleteBatchIds(ids);
        return i;
    }

    @Override
    public int update(Orders orders) {
        int i = mapper.updateById(orders);
        return i;
    }

    @Override
    public Page<Orders> selectAll(int c, int size) {
        Page<Orders> page = new Page<>();
        page.setSize(size);
        page.setCurrent(c);
        Page<Orders> selectPage = mapper.selectPage(page, null);
        return selectPage;
    }

    @Override
    public Orders select(String id) {
        Orders orders = mapper.selectById(id);
        return orders;
    }
}
