package com.example.layer.sys.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Hzhi
 * @Date 2022-04-24 9:39
 * @description
 **/
@Data
@NoArgsConstructor
public class ModifySysDeptVO extends SysDeptVO {
    @ApiModelProperty(value = "部门ID")
    private String deptId;
}