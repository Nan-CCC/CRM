package com.example.enterprisecrm.entity;

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
@ApiModel(description = "产品实体类")
public class Product {
    @ApiModelProperty("产品编号")
    @TableId("pd_id")
    private String id;
    @ApiModelProperty("产品名称")
    @TableField("pd_name")
    private String name;
    @ApiModelProperty("产品价格")
    @TableField("pd_price")
    private double price;
    @ApiModelProperty("产品库存")
    @TableField("pd_num")
    private Integer num;
}
