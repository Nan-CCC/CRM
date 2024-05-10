package com.example.enterprisecrm.service.servicelmpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.example.enterprisecrm.VO.*;
import com.example.enterprisecrm.entity.*;

import com.example.enterprisecrm.mapper.IncludeMapper;
import com.example.enterprisecrm.mapper.OrderMapper;
import com.example.enterprisecrm.mapper.ProductMapper;
import com.example.enterprisecrm.service.OrderService;

import icu.mhb.mybatisplus.plugln.base.service.impl.JoinServiceImpl;
import icu.mhb.mybatisplus.plugln.core.JoinLambdaWrapper;
import icu.mhb.mybatisplus.plugln.extend.Joins;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl extends JoinServiceImpl<OrderMapper, Orders> implements OrderService {
    @Resource
    private OrderMapper mapper;

    @Resource
    private IncludeMapper includeMapper;

    @Resource
    private ProductMapper productMapper;
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
                    Include include = new Include(null,newId,productVO.getPid(),productVO.getNum(),null);
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
    public Page<OrderVO> selectByStatus(String status, int current, int size,String uid) {
        JoinLambdaWrapper<Orders> wrapper = new JoinLambdaWrapper<>(Orders.class);
        if(status=="1" ||status.equals("1")){
            wrapper.eq(Orders::getStatus,"1");
        }
        else {
            wrapper.ne(Orders::getStatus,"1");
        }

        wrapper.eq(Orders::getUid,uid)
                .orderByDesc(Orders::getCreateTime)
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
    }

    @Override
    public int delete(String id) {
        int i = mapper.deleteById(id);
        return i;
    }

    @Override
    public Page<OrderVO> selectByLike(int current, int size,String status, String column, String like,String uid) {
        JoinLambdaWrapper<Orders> wrapper = new JoinLambdaWrapper<>(Orders.class);
        if(status=="1" ||status.equals("1")){
            wrapper.eq(Orders::getStatus,"1");
        }
        else {
            wrapper.ne(Orders::getStatus,"1");
        }

        wrapper.eq(Orders::getUid,uid)
                .like(Orders::getStatus,column=="date"||column.equals("date")?like:"")
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
    public List<Product2VO> selectByMonth(Date date, String uid) {
        int year = date.getYear()+1900;
        String oldMonth = Integer.toString(date.getMonth() + 1);
        String month;
        if(oldMonth.length()<2){
            month="0"+oldMonth;
        }
        else {
            month=oldMonth;
        }
        JoinLambdaWrapper<Include> wrapper = new JoinLambdaWrapper<>(Include.class);

        wrapper.selectAs(Include::getPid, Product2VO::getPid)
                .selectAs(Include::getPnum, Product2VO::getNum)
                .leftJoin(Orders.class,Orders::getId,Include::getOid)
                .eq(Orders::getUid,uid)
                .eq(Orders::getStatus,"1")
                .like(Orders::getCreateTime,year+"-"+month)
                .selectAs(Orders::getId, Product2VO::getOid)
                .selectAs(Orders::getCid,Product2VO::getCid)
                .end()
                .leftJoin(Product.class,Product::getId,Include::getPid)
                .selectAs(Product::getPrice, Product2VO::getPrice)
                .selectAs(Product::getName,Product2VO::getName)
                .end()
                .joinList(Product2VO.class);
        List<Product2VO> product2VOS = includeMapper.joinSelectList(wrapper, Product2VO.class);
        return product2VOS;
    }

    @Override
    public List<List<Product2VO>> selectBychart(List<String> dates, String uid) {
        List<List<Product2VO>> lists = new ArrayList<>();
        //模糊查询日期
        List<String> date=new ArrayList<>();
        //模糊查季度
        String[][] checked=new String[4][3];
        String datesType="";
        //当前年份
        Date nowDate = new Date();
        int year = nowDate.getYear()+1900;
        //获取日期情况
        if(dates.get(0).length()==4){
            datesType="year";
            date=dates;
        }
        else if(dates.get(0).contains("s")){
            datesType="season";
            int[][] season={{1,2,3},{4,5,6},{7,8,9},{10,11,12}};
            for(int i=0;i<dates.size();i++){
                String[] split = dates.get(i).split("s");
                int index = Integer.parseInt(split[split.length - 1])-1;
                for(int j=0;j<season[i].length;j++){
                    if(index<3){
                        checked[i][j]="2024-0"+season[i][j];
                    }
                    else {
                        checked[i][j]="2024-"+season[i][j];
                    }
                }
            }
            for(int i=0;i<checked.length;i++){
                System.out.println(checked[1]);
            }
        }
        else{
            datesType="month";
            for (int i=0;i<dates.size();i++){
               if(dates.get(i).length()==2){
                   date.add(year+"-"+dates.get(i));
               }else {
                   date.add(year+"-0"+dates.get(i));
               }
            }
        }

        if(datesType.equals("season")){
            for(int i=0;i<checked.length;i++){
                    if(checked[i][0]!=null){
                        JoinLambdaWrapper<Include> wrapper = new JoinLambdaWrapper<>(Include.class);
                        wrapper.selectAs(Include::getPid, Product2VO::getPid)
                                .selectAs(Include::getPnum, Product2VO::getNum)
                                .leftJoin(Orders.class,Orders::getId,Include::getOid)
                                .eq(Orders::getUid,uid)
                                .eq(Orders::getStatus,"1")
                                .like(Orders::getCreateTime,checked[i][0])
                                .or()
                                .like(Orders::getCreateTime,checked[i][1])
                                .or()
                                .like(Orders::getCreateTime,checked[i][2])
                                .selectAs(Orders::getId, Product2VO::getOid)
                                .selectAs(Orders::getCid,Product2VO::getCid)
                                .end()
                                .leftJoin(Product.class,Product::getId,Include::getPid)
                                .selectAs(Product::getPrice, Product2VO::getPrice)
                                .selectAs(Product::getName,Product2VO::getName)
                                .end()
                                .joinList(Product2VO.class);
                        List<Product2VO> product2VOS = includeMapper.joinSelectList(wrapper, Product2VO.class);
                        lists.add(product2VOS);
                }
            }
        }
        else {
            for(int i=0;i<date.size();i++){
                JoinLambdaWrapper<Include> wrapper = new JoinLambdaWrapper<>(Include.class);
                wrapper.selectAs(Include::getPid, Product2VO::getPid)
                        .selectAs(Include::getPnum, Product2VO::getNum)
                        .leftJoin(Orders.class,Orders::getId,Include::getOid)
                        .eq(Orders::getUid,uid)
                        .eq(Orders::getStatus,"1")
                        .like(Orders::getCreateTime,date.get(i))

                        .selectAs(Orders::getId, Product2VO::getOid)
                        .selectAs(Orders::getCid,Product2VO::getCid)
                        .end()
                        .leftJoin(Product.class,Product::getId,Include::getPid)
                        .selectAs(Product::getPrice, Product2VO::getPrice)
                        .selectAs(Product::getName,Product2VO::getName)
                        .end()
                        .joinList(Product2VO.class);
                List<Product2VO> product2VOS = includeMapper.joinSelectList(wrapper, Product2VO.class);
                lists.add(product2VOS);
            }
        }
        return lists;
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

    @Override
    public Boolean selectIsOld(String cid) {
        QueryWrapper<Orders> wrapper = new QueryWrapper<>();
        wrapper.eq("customer_id",cid);
        List<Orders> list = mapper.selectList(wrapper);
        if(list.size()==0){
            return true; //新客户
        }
        return false;
    }

    @Override
    public List<Order2VO> selectHistory(String cid,String oid) {
        List<Order2VO> order2VOS=new ArrayList<>();
        QueryWrapper<Orders> wrapper = new QueryWrapper<>();
        wrapper.eq("customer_id", cid)
                .eq("o_status","1")
                .orderByDesc("o_create");
        if(oid!="" && !oid.equals("")){
            wrapper.eq("o_id",oid);
        }
        List<Orders> list = mapper.selectList(wrapper);
        for(int i=0;i<list.size();i++){
            Orders orders = list.get(i);
            Order2VO order2VO = new Order2VO();
            order2VO.setOrders(orders);
            QueryWrapper<Include> includeQueryWrapper = new QueryWrapper<>();
            includeQueryWrapper.eq("order_id",orders.getId());
            List<Include> includes = includeMapper.selectList(includeQueryWrapper);
            List<Product> products=new ArrayList<>();
            for(int j=0;j<includes.size();j++){
                Include include = includes.get(j);
                Product product = new Product();
                product.setId(include.getPid());
                product.setNum(include.getPnum());
                QueryWrapper<Product> productQueryWrapper = new QueryWrapper<>();
                productQueryWrapper.eq("pd_id",include.getPid());
                Product nowProduct = productMapper.selectOne(productQueryWrapper);
                product.setName(nowProduct.getName());
                product.setPrice(nowProduct.getPrice());
                products.add(product);
            }
            order2VO.setProductList(products);

            order2VOS.add(order2VO);
        }
        return order2VOS;
    }

}
