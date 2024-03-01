package com.example.enterprisecrm.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel(value = "Marketing",description = "营销活动实体类")
public class Marketing {
    @TableId("mk_id")
    @ApiModelProperty("营销编号")
    private String id;

    @TableField("mk_name")
    @ApiModelProperty("活动名称")
    private String name;
    @TableField("mk_info")
    @ApiModelProperty("活动内容（路径）")
    private String info;
    @TableField("mk_cost")
    @ApiModelProperty("计划投入")
    private String cost;
    @TableField("mk_start")
    @ApiModelProperty("活动开始日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date start;
    @TableField("mk_end")
    @ApiModelProperty("活动结束日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date end;
    @TableField("mk_status")
    @ApiModelProperty("活动状态")
    private String status;
    @TableField("approver")
    @ApiModelProperty("审批人")
    private String approver;
    @TableField("approver_notes")
    @ApiModelProperty("评价")
    private String notes;
    @TableField("mk_pass")
    @ApiModelProperty("通过时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date pass;
}
