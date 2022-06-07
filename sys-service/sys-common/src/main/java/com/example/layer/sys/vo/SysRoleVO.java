package com.example.layer.sys.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author zhaoyl
 * @Date 2022-04-21
 * @description 角色信息VO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysRoleVO {

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "角色排序")
    private Integer roleSort;

    @ApiModelProperty(value = "角色状态（0正常 1停用")
    private Integer status;

    @ApiModelProperty(value = "角色标记")
    private String tag;

    @ApiModelProperty(value = "来源组(ROLE:USER:DEPT)")
    private String category;

    @ApiModelProperty(value = "数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限 5:个人数据权限）")
    private String dataScope;
}
