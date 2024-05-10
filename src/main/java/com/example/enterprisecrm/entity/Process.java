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
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(description = "用户-服务 进行实体类")
public class Process {
    @ApiModelProperty("编号")
    @TableId("id")
    private Integer id;
    @ApiModelProperty("客户编号")
    @TableField("c_id")
    private String cid;
    @ApiModelProperty("员工编号")
    @TableField("user_id")
    private String uid;
    @ApiModelProperty("服务编号")
    @TableField("service_id")
    private String sid;
    @ApiModelProperty("变更时间")
    @TableField("changeTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date changeTime;
    @ApiModelProperty("回复内容")
    @TableField("content")
    private String content;
}
