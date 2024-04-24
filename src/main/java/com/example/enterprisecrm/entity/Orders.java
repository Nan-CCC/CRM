package com.example.enterprisecrm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(description = "订单实体类")
public class Orders {
    @ApiModelProperty("订单编号")
    @TableId(value = "o_id")
    private String id;
    @ApiModelProperty("订单状态")
    @TableField("o_status")
    private String status;
    @ApiModelProperty("省")
    @TableField("province")
    private Integer province;
    @ApiModelProperty("市")
    @TableField("city")
    private Integer city;
    @ApiModelProperty("区")
    @TableField("district")
    private Integer district;
    @ApiModelProperty("地址详情")
    @TableField("o_address")
    private String address;
    @ApiModelProperty("创建时间")
    @TableField("o_create")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @ApiModelProperty("逻辑删除")
    @TableField("logical")
    private Integer logical;
    @ApiModelProperty("员工编号")
    @TableField("user_id")
    private String uid;
    @ApiModelProperty("客户编号")
    @TableField("customer_id")
    private String cid;
    @ApiModelProperty("合同编号")
    @TableField("contract_id")
    private String ctid;

}
