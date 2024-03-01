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
    @ApiModelProperty("营销编号")
    @TableId("market_id")
    private String mid;
    @ApiModelProperty("员工编号")
    @TableField("user_id")
    private String uid;
    @ApiModelProperty("活动提交时间")
    @TableField("submit")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date submit;
}
