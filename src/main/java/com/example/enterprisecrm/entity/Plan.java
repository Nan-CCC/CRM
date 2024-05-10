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
@ApiModel(description = "营销活动-用户 策划实体类")
public class Plan {
    @ApiModelProperty("编号")
    @TableId("id")
    private Integer id;
    @ApiModelProperty("营销编号")
    @TableField("market_id")
    private String mid;
    @ApiModelProperty("员工编号")
    @TableField("user_id")
    private String uid;

}
