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
@ApiModel(description = "用户-服务 进行实体类")
public class Process {
    @ApiModelProperty("员工编号")
    @TableId("user_id")
    private String uid;
    @ApiModelProperty("服务编号")
    @TableField("service_id")
    private String sid;
    @ApiModelProperty("变更时间")
    @TableField("change")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date change;
    @ApiModelProperty("回复内容")
    @TableField("content")
    private String content;
}
