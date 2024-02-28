package com.example.enterprisecrm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
@ApiModel(value = "User", description = "员工实体类")
public class User {
    @ApiModelProperty("员工编号")
    @TableId(value = "u_id")
    private String id;
    @ApiModelProperty("员工姓名")
    @TableField("u_name")
    private String name;
    @ApiModelProperty("密码")
    @TableField("password")
    private String password;
    @ApiModelProperty("所属部门")
    @TableField("department")
    private String department;
    @ApiModelProperty("权限")
    @TableField("authority")
    private Integer authority;
}
