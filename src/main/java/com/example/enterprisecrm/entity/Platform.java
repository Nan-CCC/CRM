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
@ApiModel(description = "平台实体类")
public class Platform {
    @ApiModelProperty("平台编号")
    @TableId("pf_id")
    private String id;
    @ApiModelProperty("平台名称")
    @TableField("pf_name")
    private String name;
}
