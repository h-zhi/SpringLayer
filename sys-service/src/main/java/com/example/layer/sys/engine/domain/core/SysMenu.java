package com.example.layer.sys.engine.domain.core;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author zhaoyl
 * @Date 2022-04-28
 * @description 系统菜单
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName(value = "sys_menu")
public class SysMenu {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @TableId(type = IdType.ASSIGN_ID)
    private Long menuId;
    /**
     * 菜单名称
     */
    private String menuName;
    /**
     * 父菜单Id
     */
    private Long parentId;
    /**
     * 显示顺序
     */
    private Integer orderNum;
    /**
     * 请求地址
     */
    private String  url;
    /**
     * 打开方式（menuItem页签 menuBlank新窗口）
     */
    private String  target;
    /**
     * 菜单类型（M目录 C菜单 B按钮 Fapi）
     */
    private String  menuType;
    /**
     * 菜单状态（0显示 1隐藏）
     */
    private Integer  visible;
    /**
     * 页面权限标识
     */
    private String viewPerms;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 创建人
     */
    private Long createBy;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新人
     */
    private Long updateBy;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 备注
     */
    private String remark;
    /**
     * 子菜单
     */
    @TableField(exist = false)
    private List<SysMenu> children = new ArrayList<SysMenu>();

    public void addChildren(SysMenu menu) {
      if (menu!=null){
          this.children.add(menu);
      }
    }




}
