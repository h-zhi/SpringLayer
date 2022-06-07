package com.example.layer.sys.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author zhaoyl
 * @Date 2022-04-23
 * @description 修改字典类型
 **/
@Data
@NoArgsConstructor
public class ModifySysDictTypeVO extends SysDictTypeVO {
    @ApiModelProperty(value = "字典类型ID")
    private String dictId;
}
