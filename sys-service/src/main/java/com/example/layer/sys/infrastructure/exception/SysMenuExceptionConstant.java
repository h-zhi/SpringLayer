package com.example.layer.sys.infrastructure.exception;

/**
 * @author zhaoyl
 * @date 2022/04/28
 * @description 菜单
 */
public class SysMenuExceptionConstant {
    public static final String NOT_MENU_ID = "菜单ID不能为空";
    public static final String NOT_MENU_NAME = "菜单名称不能为空";
    public static final String NOT_MENU_PARENT_ID = "父菜单ID不能为空";
    public static final String NOT_MENU_OEDER_NUM ="显示顺序不能为空";
    public static final String NOT_MENU_TARGET = "打开方式不能为空";
    public static final String NOT_MENU_VISIBLE = "打开方式不能为空";

    public static final String EXITS_MENU_NAME= "菜单名称已存在";
    public static final String NOT_MENU_PARENT = "父菜单不存在";
    public static final String EXITS_MENU_SON= "该菜单下存在子菜单";
}
