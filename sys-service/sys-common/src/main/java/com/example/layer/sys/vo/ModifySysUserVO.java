package com.example.layer.sys.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Hzhi
 * @Date 2022-04-19 11:26
 * @description
 **/
@Data
@NoArgsConstructor
public class ModifySysUserVO extends CreateSysUserVO {
    @ApiModelProperty(value = "用户ID")
    private String userId;
}