package com.example.enterprisecrm.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * 增加订单-查看订单
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(description = "已有订单视图")
public class OrderVO {
    @ApiModelProperty("订单编号")
    private String oid;
    @ApiModelProperty("客户订单")
    private String cid;
    @ApiModelProperty("客户名称")
    private String cName;
    @ApiModelProperty("电话")
    private String phone;
    @ApiModelProperty("省")
    private Integer province;
    @ApiModelProperty("市")
    private Integer city;
    @ApiModelProperty("区")
    private Integer district;
    @ApiModelProperty("详细地址")
    private String address;
    @ApiModelProperty("合同地址")
    private String ctContent;
    @ApiModelProperty("签署时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date sign;
    @ApiModelProperty("提交时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @ApiModelProperty("订单状态")
    private String status;
}
