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

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(description = "菜单实体类")
public class Menu {
    @ApiModelProperty("菜单编号")
    @TableId(value = "id",type = IdType.AUTO)
    private int id;
    @ApiModelProperty("菜单名称")
    @TableField("name")
    private String name;
    @ApiModelProperty("路径")
    @TableField("path")
    private String path;
    @ApiModelProperty("图标")
    @TableField("icon")
    private String icon;
    @ApiModelProperty("父菜单")
    @TableField("pid")
    private String pid;
    @ApiModelProperty("页面路径")
    @TableField("page_path")
    private String pagePath;
    @ApiModelProperty("权限")
    @TableField("role")
    private String role;
    @ApiModelProperty("位置")
    @TableField("local")
    private String local;
    @ApiModelProperty("子菜单")
    @TableField(exist = false) //数据库没有但有用，忽略
    private List<Menu> children;

}
