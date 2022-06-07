package com.example.layer.sys.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author Hzhi
 * @Date 2022-04-19 11:26
 * @description
 **/
@Data
@NoArgsConstructor
public class CreateSysUserVO extends SysUserVO {
    @ApiModelProperty(value = "岗位ID")
    private List<String> postIds;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "角色ID")
    private List<String> RoleIds;
    @ApiModelProperty(value = "部门ID")
    private List<String> deptIds;

}
