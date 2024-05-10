package com.example.enterprisecrm.VO;

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
@ApiModel(description = "产品视图")
public class ProductVO {
    @ApiModelProperty("产品编号")
    private String pid;
    @ApiModelProperty("产品数量")
    private Integer num;
    @ApiModelProperty("产品价格")
    private Double price;
}
