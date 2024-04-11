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
@ApiModel(description = "客户实体类")
public class Customer {
    @ApiModelProperty("客户编号")
    @TableId("c_id")
    private String id;
    @ApiModelProperty("客户姓名")
    @TableField("c_name")
    private String name;
    @ApiModelProperty("年龄段")
    @TableField("c_age")
    private String age;
    @ApiModelProperty("所属公司")
    @TableField("c_company")
    private String company;
    @ApiModelProperty("联系电话")
    @TableField("c_phone")
    private String phone;
    @ApiModelProperty("邮箱")
    @TableField("c_email")
    private String email;
    @ApiModelProperty("备注")
    @TableField("c_notes")
    private String info;
    @ApiModelProperty("营销编号")
    @TableField("market_id")
    private String mid;
    @ApiModelProperty("平台编号")
    @TableField("platform_id")
    private String pid;
    @ApiModelProperty("所属员工编号")
    @TableField("owner")
    private String uid;

}