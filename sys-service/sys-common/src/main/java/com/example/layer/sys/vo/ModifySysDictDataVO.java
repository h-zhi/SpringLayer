package com.example.layer.sys.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author zhaoyl
 * @Date 2022-04-23
 * @description 修改字典数据
 **/
@Data
@NoArgsConstructor
public class ModifySysDictDataVO extends SysDictDataVO{
    @ApiModelProperty(value = "字典数据ID")
    private String dictCode;
}
