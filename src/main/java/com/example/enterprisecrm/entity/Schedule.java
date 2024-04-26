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

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(description = "日程实体类")
public class Schedule {
    @TableId(value = "id",type = IdType.AUTO)
    @ApiModelProperty("日程编号")
    private Integer id;

    @TableField("user")
    @ApiModelProperty("员工编号")
    private String user;

    @TableField("content")
    @ApiModelProperty("内容")
    private String content;

    @TableField("date")
    @ApiModelProperty("目标时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @TableField("status")
    @ApiModelProperty("状态")
    private Integer status;
}
