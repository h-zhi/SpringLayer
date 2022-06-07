package com.example.layer.sys.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author zhaoyl
 * @Date 2022-04-21
 * @description 修改岗位Vo
 **/
@Data
@NoArgsConstructor
public class ModifySysPostVO extends SysPostVO{
    @ApiModelProperty(value = "岗位ID")
    private String postId;
}
