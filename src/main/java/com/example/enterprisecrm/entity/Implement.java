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
@ApiModel(description = "平台-营销活动 实施实体类")
public class Implement {
    @ApiModelProperty("编号")
    @TableId("id")
    private Integer id;
    @ApiModelProperty("平台编号")
    @TableField("platform_id")
    private String pid;
    @ApiModelProperty("营销编号")
    @TableField("market_id")
    private String mid;

}
