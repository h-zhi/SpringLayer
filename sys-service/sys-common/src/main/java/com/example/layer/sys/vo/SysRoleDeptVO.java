package com.example.layer.sys.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author zhaoyl
 * @Date 2022-05-30 15:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysRoleDeptVO {
    @ApiModelProperty(value = "角色ID")
    private String roleId;
    @ApiModelProperty(value = "部门ID")
    private List<String> deptId;
}


