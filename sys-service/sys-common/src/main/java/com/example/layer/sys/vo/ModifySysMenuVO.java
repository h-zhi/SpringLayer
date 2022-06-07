package com.example.layer.sys.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author zhaoyl
 * @Date 2022-04-28
 * @description 修改菜单VO
 **/
@Data
@NoArgsConstructor
public class ModifySysMenuVO extends SysMenuVO{
    @ApiModelProperty(value = "菜单Id")
    private String menuId;
}
