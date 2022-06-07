package com.example.layer.sys.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author zhaoyl
 * @Date 2022-04-28
 * @description
 * 系统菜单信息
 * */
@Data
@NoArgsConstructor
public class SysMenuDTO {
    @ApiModelProperty(value = "菜单Id")
    private String menuId;
    @ApiModelProperty(value = "菜单名称")
    private String menuName;
    @ApiModelProperty(value = "父菜单Id")
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
    @ApiModelProperty(value = "创建人")
    private Long createBy;
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
    @ApiModelProperty(value = "更新人")
    private Long updateBy;
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;
    @ApiModelProperty(value = "备注")
    private String remark;
}
