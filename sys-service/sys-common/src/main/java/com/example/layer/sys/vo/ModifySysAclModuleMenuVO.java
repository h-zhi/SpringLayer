package com.example.layer.sys.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author zhaoyl
 * @Date 2022-04-27
 * @description
 **/
@Data
@NoArgsConstructor
public class ModifySysAclModuleMenuVO extends CreateSysAclModuleMenuVO{
    @ApiModelProperty(value = "权限模块功能点id")
    private String id;
}
