package com.example.enterprisecrm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.enterprisecrm.VO.AddOrderVO;

import com.example.enterprisecrm.VO.Order2VO;
import com.example.enterprisecrm.VO.OrderVO;
import com.example.enterprisecrm.VO.Product2VO;
import com.example.enterprisecrm.common.Log.BusniessType;
import com.example.enterprisecrm.common.Log.MyLog;
import com.example.enterprisecrm.common.result.Result;
import com.example.enterprisecrm.common.result.ResultUtil;
import com.example.enterprisecrm.entity.Orders;
import com.example.enterprisecrm.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("order")
@Api(tags = "OrderApi")
public class OrderController {
    @Resource
    private OrderService service;

    //@ModelAttribute 在参数上，是把对象的属性分别绑到方法参数上
    @PostMapping("/add")
    @ApiOperation("增加订单")
    @MyLog(title = "订单",optParam = "#{orderVO}",businessType = BusniessType.INSERT)
    public Result add(@RequestBody AddOrderVO orderVO) throws ParseException {
        String insert = service.insert(orderVO);
        if(insert.equals("")||insert==null){
            return ResultUtil.error();
        }
        return ResultUtil.success("success",insert);
    }

    @PutMapping("/updateStatus")
    @ApiOperation("修改订单状态")
    @MyLog(title = "订单",optParam = "",businessType = BusniessType.UPDATE)
    public Result updateStatus(@RequestParam String oid,@RequestParam String status){
        int i = service.updateStatus(oid,status);
        if(i>0){
            return ResultUtil.success(i);
        }
        return ResultUtil.error();
    }

    @PostMapping("orderbystatus")
    @ApiOperation("根据状态查看订单")
    public Result selectByStatus(@RequestParam String status,@RequestParam int current,@RequestParam int size,@RequestParam  String uid){
        Page<OrderVO> voPage = service.selectByStatus(status, current, size,uid);
        return ResultUtil.success(voPage);
    }

    @PostMapping("orderbylike")
    @ApiOperation("根据条件查询订单")
    public Result selectByLike(@RequestParam int current,@RequestParam int size,@RequestParam String status,@RequestParam String column,@RequestParam String like,@RequestParam String uid){
        Page<OrderVO> voPage = service.selectByLike(current, size,status, column, like,uid);
        return ResultUtil.success(voPage);
    }

    @PostMapping("selectByMonth")
    @ApiModelProperty("查询月订单情况")
    public Result selectByMonth(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd")Date date,@RequestParam String uid){
        List<Product2VO> product2VOS = service.selectByMonth(date,uid);
        return ResultUtil.success(product2VOS);
    }

    @PostMapping("/selectbychart")
    @ApiOperation("获取图表数据")
    public Result selectByChart(@RequestBody List<String> list,@RequestParam String column,@RequestParam String uid){
        if(list.size()<=0 ||column.isEmpty()){
            return ResultUtil.error();
        }
        else {
            //返回数据
            //price
            List<Double> data=new ArrayList<>();
            //count
            List<Integer> count =new ArrayList<>();
            //查询到的数据
            List<List<Product2VO>> lists = service.selectBychart(list, uid);
            //计算
            if(column=="price" || column.equals("price")){
                for (int i=0;i<lists.size();i++){
                    double sum=0;
                    if(lists.get(i).size()>0){
                        for(int j=0;j<lists.get(i).size();j++){
                            Product2VO product2VO = lists.get(i).get(j);
                            sum+=product2VO.getNum()* product2VO.getPrice();
                        }
                    }
                    data.add(sum);
                }
                return ResultUtil.success(data);
            }
            else if(column=="count" || column.equals("count")){
                for (int i=0;i<lists.size();i++){
                    int num=0;
                    if(lists.get(i).size()>0) {
                        List<String> ids = new ArrayList<>();
                        for (Product2VO obj : lists.get(i)) {
                            ids.add(obj.getOid());
                        }
                        HashSet hashSet = new HashSet(ids);
                        num = hashSet.size();
                    }
                    count.add(num);
                }
                return ResultUtil.success(count);
            }
            else if(column=="cNum" || column.equals("cNum")){
                for (int i=0;i<lists.size();i++){
                    int num=0;
                    if(lists.get(i).size()>0) {
                        List<String> cids = new ArrayList<>();
                        for (Product2VO obj : lists.get(i)) {
                            cids.add(obj.getCid());
                        }
                        HashSet hashSet = new HashSet(cids);
                        num = hashSet.size();
                    }
                    count.add(num);
                }
                return ResultUtil.success(count);
            }
        }
        return ResultUtil.error();
    }

    @PutMapping("/update")
    @ApiOperation("修改订单")
    public Result update(@ModelAttribute Orders orders){
        int i = service.update(orders);
        if(i>0){
            return ResultUtil.success(i);
        }
        return ResultUtil.error();
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除订单")
    @MyLog(title = "订单",optParam = "#{id}",businessType =BusniessType.DELETE)
    public Result delete(@RequestParam String id){
        int i = service.delete(id);
        if(i>0){
            return ResultUtil.success(i);
        }
        return ResultUtil.error();
    }

    @PostMapping("/queryall")
    @ApiOperation("查看所有订单")
    public Result queryAll(int c,int size){
        Page<Orders> page = service.selectAll(c, size);
        if(page!=null){
            return ResultUtil.success(page);
        }
        return ResultUtil.error();
    }

    @PostMapping("/query")
    @ApiOperation("查看订单")
    public Result query(@RequestParam String id){
        Orders select = service.select(id);
        if(select!=null){
            return ResultUtil.success(select);
        }
        return ResultUtil.error();
    }

    @GetMapping("isOld")
    @ApiOperation("判断客户是否有过订单")
    public Result selectIsOrder(@RequestParam String cid){
        Boolean b = service.selectIsOld(cid);
        return ResultUtil.success(b);
    }

    @GetMapping("history")
    @ApiOperation(("查看客户历史订单"))
    public Result selectHistory(@RequestParam String cid,@RequestParam String oid){
        List<Order2VO> order2VOS = service.selectHistory(cid,oid);
        return ResultUtil.success(order2VOS);
    }
}
