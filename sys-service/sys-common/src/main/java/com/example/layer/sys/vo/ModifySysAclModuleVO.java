package com.example.layer.sys.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author zhaoyl
 * @Date 2022-04-26
 * @description 修改权限模块点
 **/
@Data
@NoArgsConstructor
public class ModifySysAclModuleVO extends SysAclModuleVO {
    @ApiModelProperty(value = "权限模块点id")
    private String aclModuleId;
}
