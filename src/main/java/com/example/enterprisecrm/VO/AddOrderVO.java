package com.example.enterprisecrm.VO;

import com.example.enterprisecrm.entity.Product;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(description = "新增订单视图")
public class AddOrderVO {
    @ApiModelProperty("订单状态")
    private String status;
    @ApiModelProperty("省")
    private Integer province;
    @ApiModelProperty("市")
    private Integer city;
    @ApiModelProperty("区")
    private Integer district;
    @ApiModelProperty("详细地址")
    private String address;
    @ApiModelProperty("员工编号")
    private String uid;
    @ApiModelProperty("客户编号")
    private String cid;
    @ApiModelProperty("产品列表")
    private List<ProductVO> proList;

}
