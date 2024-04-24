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
@ApiModel(description = "合同实体类")
public class Contract {
    @ApiModelProperty("合同编号")
    @TableId("ct_id")
    private String id;
    @ApiModelProperty("合同内容(文件路径)")
    @TableField("ct_content")
    private String content;
    @ApiModelProperty("提交时间")
    @TableField("ct_sign")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date sign;
    @ApiModelProperty("订单编号")
    @TableField("order_id")
    private String oid;
}
