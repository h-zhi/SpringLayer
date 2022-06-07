package com.example.layer.sys.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author zhaoyl
 * @Date 2022-04-27
 * @description
 **/
@Data
@NoArgsConstructor
public class CreateSysAclModuleMenuVO {

    @ApiModelProperty(value = "权限模块点id")
    private String aclModuleId;
    @ApiModelProperty(value = "菜单id")
    private List<String> menuId;
}
