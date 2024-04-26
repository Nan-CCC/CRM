package com.example.enterprisecrm.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
//当我们用于对象属性比较的时候：只比较子类的属性,
//@Data包含了 @EqualsAndHashCode(callSuper = false)

@Accessors(chain = true)
//不写默认为false，当该值为 true 时，对应字段的 setter 方法调用后，会返回当前对象。

//这个接口是 实现序列化的
public class SysLog implements Serializable {
    private static final long serialVersionUID=1L;

    @TableId(value = "id",type = IdType.AUTO)
    @ApiModelProperty("日志编号")
    private Integer id;

    @TableField("title")
    @ApiModelProperty("模块标题")
    private String title;

    @TableField("business_type")
    @ApiModelProperty("业务类型")
    private String businessType;

    @TableField("url")
    @ApiModelProperty("方法路径")
    private String url;

    @TableField("status")
    @ApiModelProperty("操作状态(0正常，1异常)")
    private Integer status;

    @TableField("param")
    @ApiModelProperty("参数")
    private String param;

    @TableField("user")
    @ApiModelProperty("操作人员编号")
    private String user;

    @TableField("error_msg")
    @ApiModelProperty("错误消息")
    private String errorMsg;

    @TableField("time")
    @ApiModelProperty("时间")
    private Date time;
}
