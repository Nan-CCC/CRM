package com.example.enterprisecrm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(description = "订单-产品 包含类")
public class Include {
    @ApiModelProperty("包含编号")
    @TableId(value = "id",type = IdType.AUTO)
    private  Integer id;
    @ApiModelProperty("订单编号")
    @TableField("order_id")
    private String oid;
    @ApiModelProperty("产品编号")
    @TableField("product_id")
    private String pid;
    @ApiModelProperty("产品数量")
    @TableField("product_num")
    private Integer pnum;
    @TableField("sum(product_num)")
    private Integer topNum;
}
