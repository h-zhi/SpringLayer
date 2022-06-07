package com.example.layer.sys.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author zhaoyl
 * @Date 2022-04-28
 * @description 系统菜单VO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysMenuVO {
    @ApiModelProperty(value = "菜单名称")
    private String menuName;
    @ApiModelProperty(value = "父菜单Id(一级为0)")
    private String parentId;
    @ApiModelProperty(value = "显示顺序")
    private Integer orderNum;
    @ApiModelProperty(value = "请求地址")
    private String  url;
    @ApiModelProperty(value = "打开方式(menuItem页签 menuBlank新窗口)")
    private String  target;
    @ApiModelProperty(value = "菜单类型(M目录 C菜单 B按钮 Fapi)")
    private String  menuType;
    @ApiModelProperty(value = "菜单状态(0显示 1隐藏)")
    private Integer  visible;
    @ApiModelProperty(value = "页面权限标识")
    private String viewPerms;
    @ApiModelProperty(value = "菜单图标")
    private String icon;
    @ApiModelProperty(value = "备注")
    private String remark;
}
