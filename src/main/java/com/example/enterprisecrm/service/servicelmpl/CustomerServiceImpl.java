package com.example.enterprisecrm.service.servicelmpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.enterprisecrm.VO.OrderVO;
import com.example.enterprisecrm.entity.Customer;
import com.example.enterprisecrm.entity.Orders;
import com.example.enterprisecrm.entity.Platform;
import com.example.enterprisecrm.mapper.CustomerMapper;
import com.example.enterprisecrm.service.CustomerService;
import icu.mhb.mybatisplus.plugln.base.service.impl.JoinServiceImpl;
import icu.mhb.mybatisplus.plugln.core.JoinLambdaWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class CustomerServiceImpl extends JoinServiceImpl<CustomerMapper, Customer> implements CustomerService {
    @Resource
    private CustomerMapper mapper;
    @Override
    public int insert(Customer customer) {
        customer.setId("");
        int i = mapper.insert(customer);
        return i;
    }

    @Override
    public int delete(List<String> ids) {
        int i = mapper.deleteBatchIds(ids);
        return i;
    }

    @Override
    public int update(Customer customer) {
        int i = mapper.updateById(customer);
        return i;
    }

    @Override
    public int updateOwner(String id, String uid) {
        UpdateWrapper<Customer> wrapper = new UpdateWrapper<>();

        int update = mapper.update(null, wrapper.eq("c_id",id).set("owner",uid));
        return update;
    }


    @Override
    public Page<Customer> selectAll(int c, int size, String owner) {
        Page<Customer> page = new Page<>();
        page.setCurrent(c);
        page.setSize(size);
        QueryWrapper<Customer> wrapper = new QueryWrapper<>();
        if(owner.equals("0") ||owner=="0"){//机会
            wrapper.isNull("owner").or().eq("owner","").orderByDesc("c_id");
        }
        else if(owner.equals("public")){
            wrapper.eq("owner","public").orderByDesc("c_id");
        }
        else {
            wrapper.eq("owner",owner).orderByDesc("c_id");
        }
        Page<Customer> select = mapper.selectPage(page,wrapper);
        return select;

    }

    @Override
    public Customer select(String id) {
        Customer customer = mapper.selectById(id);
        return customer;
    }

    @Override
    public Page<Customer> selectLike(int current,int size,String column, String like,String owner) {
        Page<Customer> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);
        QueryWrapper<Customer> wrapper = new QueryWrapper<>();
        //机会
        if(owner.equals("0") ||owner=="0"){
            wrapper.and(qw->qw.isNull("owner").or().eq("owner",""))
                    .like(column,like)
                    .orderByDesc("c_id");
        }
        else if(owner.equals("public")){
            wrapper.eq("owner","public")
                    .like(column,like)
                    .orderByDesc("c_id");
        }
        else{
            wrapper.eq("owner",owner)
                    .like(column,like)
                    .orderByDesc("c_id");
        }
        Page<Customer> select= mapper.selectPage(page,wrapper);
        return select;
    }

    @Override
    public List<Customer> selectList(String uid) {
        QueryWrapper<Customer> wrapper = new QueryWrapper<>();
        wrapper.and(qw->qw.ne("owner","").or().isNotNull("owner"))
                .eq("owner",uid);
        List<Customer> customers = mapper.selectList(wrapper);
        return customers;
    }

    @Override
    public Integer noOrderNum(String uid) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = format.format(date);

        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.YEAR,-1);
        Date lastYear = instance.getTime();
        String last = format.format(lastYear);

        JoinLambdaWrapper<Customer> wrapper = new JoinLambdaWrapper<>(Customer.class);
        wrapper.and(qw->qw.ne(Customer::getUid,"").or().isNotNull(Customer::getUid))
                .eq(Customer::getUid,uid)
                .leftJoin(Orders.class,Orders::getCid,Customer::getId)
                .eq(Orders::getStatus,"1")
                .between(Orders::getCreateTime,lastYear,date)
                .end()
                .joinList(OrderVO.class);
        List<OrderVO> list = mapper.joinSelectList(wrapper, OrderVO.class);

        return list.size();
    }

    @Override
    public Integer getPie(String uid,String pid) {
        QueryWrapper<Customer> wrapper = new QueryWrapper<>();
        if(pid==""||pid.equals("")){
            wrapper.eq("owner",uid)
                    .and(qw->qw.isNull("platform_id").or().eq("platform_id",""));
        }
        else {
            wrapper.eq("owner",uid)
                    .eq("platform_id",pid);
        }
        Integer count = Math.toIntExact(mapper.selectCount(wrapper));
        return count;
    }


}
