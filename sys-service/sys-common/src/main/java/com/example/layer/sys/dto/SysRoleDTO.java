package com.example.layer.sys.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author zhaoyl
 * @Date 2022-04-21
 * @description
 **/
@Data
@NoArgsConstructor
public class SysRoleDTO {
    @ApiModelProperty(value = "用户ID")
    private String roleId;
    @ApiModelProperty(value = "角色名称")
    private String roleName;
    @ApiModelProperty(value = "角色排序")
    private Integer roleSort;
    @ApiModelProperty(value = "角色状态（0正常 1停用）")
    private Integer status;
    @ApiModelProperty(value = "角色标记")
    private Integer tag;
    @ApiModelProperty(value = "是否删除 false否 true是")
    private Integer isDeleted;
    @ApiModelProperty(value = "创建人")
    private Long createBy;
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
    @ApiModelProperty(value = "更新人")
    private Long updateBy;
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;
    @ApiModelProperty(value = "来源组(ROLE:USER:DEPT)")
    private String category;
    @ApiModelProperty(value = "数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限 5:个人数据权限）")
    private String dataScope;
}
