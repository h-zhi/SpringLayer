package com.example.layer.sys.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author zhaoyl
 * @Date 2022-04-26
 * @description
 **/
@Data
@NoArgsConstructor
public class SysAclModuleVO {
    @ApiModelProperty(value = "来源ID")
    private String sourceId;
    @ApiModelProperty(value = "来源组(ROLE;USER;DEPT)")
    private String sourceModule;
    @ApiModelProperty(value = "状态 0 启用 1禁用")
    private Integer status;
}
