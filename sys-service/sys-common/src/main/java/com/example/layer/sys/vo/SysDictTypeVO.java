package com.example.layer.sys.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhaoyl
 * @Date 2022-04-23
 * @description
 * 字典类型VO
 * */
@Data
@NoArgsConstructor
public class SysDictTypeVO {

    @ApiModelProperty(value = "字典名称")
    private String dictName;
    @ApiModelProperty(value = "字典类型")
    private String dictType;
    @ApiModelProperty(value = "状态（0正常 1停用）")
    private Integer status;
    @ApiModelProperty(value = "备注")
    private String remark;
}
