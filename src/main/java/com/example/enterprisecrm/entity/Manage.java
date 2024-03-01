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
@ApiModel(description = "员工-客户 管理实体类")
public class Manage {
    @ApiModelProperty("员工编号")
    @TableId("user_id")
    private String uid;
    @ApiModelProperty("客户编号")
    @TableField("customer_id")
    private String cid;
    @ApiModelProperty("操作时间")
    @TableField("manipulate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date manipulate;
    @ApiModelProperty("操作类型")
    @TableField("manipulate_type")
    private String type;
}
