package com.example.enterprisecrm.service.servicelmpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.example.enterprisecrm.VO.AddOrderVO;
import com.example.enterprisecrm.VO.OrderVO;
import com.example.enterprisecrm.VO.ProductVO;
import com.example.enterprisecrm.entity.Contract;
import com.example.enterprisecrm.entity.Customer;

import com.example.enterprisecrm.entity.Include;
import com.example.enterprisecrm.entity.Orders;
import com.example.enterprisecrm.mapper.IncludeMapper;
import com.example.enterprisecrm.mapper.OrderMapper;
import com.example.enterprisecrm.service.OrderService;

import icu.mhb.mybatisplus.plugln.base.service.impl.JoinServiceImpl;
import icu.mhb.mybatisplus.plugln.core.JoinLambdaWrapper;
import icu.mhb.mybatisplus.plugln.extend.Joins;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl extends JoinServiceImpl<OrderMapper, Orders> implements OrderService {
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
    public Page<OrderVO> selectByStatus(String status, int current, int size) {
        JoinLambdaWrapper<Orders> wrapper = new JoinLambdaWrapper<>(Orders.class);
        if(status=="1" ||status.equals("1")){
            wrapper.eq(Orders::getStatus,"1");
        }
        else {
            wrapper.ne(Orders::getStatus,"1");
        }
        wrapper.orderByDesc(Orders::getCreateTime)
                .selectAs(Orders::getId,OrderVO::getOid)
                .selectAs(Orders::getProvince,OrderVO::getProvince)
                .selectAs(Orders::getCity,OrderVO::getCity)
                .selectAs(Orders::getDistrict,OrderVO::getDistrict)
                .selectAs(Orders::getAddress,OrderVO::getAddress)
                .selectAs(Orders::getCreateTime,OrderVO::getCreateTime)
                .selectAs(Orders::getStatus,OrderVO::getStatus)
                .leftJoin(Customer.class, Customer::getId, Orders::getCid)
                .selectAs(Customer::getId,OrderVO::getCid)
                .selectAs(Customer::getName,OrderVO::getCName)
                .selectAs(Customer::getPhone,OrderVO::getPhone)
                .end()
                .leftJoin(Contract.class,Contract::getId,Orders::getCtid)
                .selectAs(Contract::getContent,OrderVO::getCtContent)
                .selectAs(Contract::getSign,OrderVO::getSign)
                .end()
                .joinList(OrderVO.class);

        //创建分页
        Page<OrderVO> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);
        Page<OrderVO> voPage = mapper.joinSelectPage(page, wrapper, OrderVO.class);
        return voPage;
//        List<OrderVO> list = Joins.of(Orders.class)
//                .selectAs(Orders::getId,OrderVO::getOid)
//                .selectAs(Orders::getProvince,OrderVO::getProvince)
//                .selectAs(Orders::getCity,OrderVO::getCity)
//                .selectAs(Orders::getDistrict,OrderVO::getDistrict)
//                .selectAs(Orders::getAddress,OrderVO::getAddress)
//                .selectAs(Orders::getCreateTime,OrderVO::getCreateTime)
//                .selectAs(Orders::getStatus,OrderVO::getStatus)
//                .leftJoin(Customer.class, Customer::getId, Orders::getCid)
//                .selectAs(Customer::getName,OrderVO::getCName)
//                .end()
//                .leftJoin(Contract.class,Contract::getId,Orders::getCtid)
//                .selectAs(Contract::getContent,OrderVO::getCtContent)
//                .selectAs(Contract::getSign,OrderVO::getSign)
//                .end()
//                .joinList(OrderVO.class);



    }

    @Override
    public int delete(String id) {
        int i = mapper.deleteById(id);
        return i;
    }

    @Override
    public Page<OrderVO> selectByLike(int current, int size,String status, String column, String like) {
        JoinLambdaWrapper<Orders> wrapper = new JoinLambdaWrapper<>(Orders.class);
        if(status=="1" ||status.equals("1")){
            wrapper.eq(Orders::getStatus,"1");
        }
        else {
            wrapper.ne(Orders::getStatus,"1");
        }
        wrapper.like(Orders::getStatus,column=="date"||column.equals("date")?like:"")
                .like(Orders::getId,column=="oid"||column.equals("oid")?like:"")
                .orderByDesc(Orders::getCreateTime)
                .selectAs(Orders::getId,OrderVO::getOid)
                .selectAs(Orders::getProvince,OrderVO::getProvince)
                .selectAs(Orders::getCity,OrderVO::getCity)
                .selectAs(Orders::getDistrict,OrderVO::getDistrict)
                .selectAs(Orders::getAddress,OrderVO::getAddress)
                .selectAs(Orders::getCreateTime,OrderVO::getCreateTime)
                .selectAs(Orders::getStatus,OrderVO::getStatus)
                .leftJoin(Customer.class, Customer::getId, Orders::getCid)
                .like(Customer::getId,column=="cid"||column.equals("cid")?like:"")
                .like(Customer::getName,column=="name"||column.equals("name")?like:"")
                .like(Customer::getPhone,column=="phone"||column.equals("phone")?like:"")
                .selectAs(Customer::getId,OrderVO::getCid)
                .selectAs(Customer::getName,OrderVO::getCName)
                .selectAs(Customer::getPhone,OrderVO::getPhone)
                .end()
                .leftJoin(Contract.class,Contract::getId,Orders::getCtid)
                .selectAs(Contract::getContent,OrderVO::getCtContent)
                .selectAs(Contract::getSign,OrderVO::getSign)
                .end()
                .joinList(OrderVO.class);

        //创建分页
        Page<OrderVO> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);
        Page<OrderVO> voPage = mapper.joinSelectPage(page, wrapper, OrderVO.class);
        return voPage;
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
