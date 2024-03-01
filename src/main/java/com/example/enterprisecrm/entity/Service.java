package com.example.enterprisecrm.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(description = "服务实体类")
public class Service {
    @ApiModelProperty("服务编号")
    @TableId("s_id")
    private String id;
    @ApiModelProperty("服务类型")
    @TableField("s_type")
    private String type;
    @ApiModelProperty("服务内容")
    @TableField("s_content")
    private String content;

    @ApiModelProperty("处理状态")
    @TableField("s_statuus")
    private String statuus;
    @ApiModelProperty("创建时间")
    @TableField("s_create")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create;

    @ApiModelProperty("订单编号")
    @TableField("order_id")
    private String oid;
    @ApiModelProperty("客户编号")
    @TableField("customer_id")
    private String cid;
}
