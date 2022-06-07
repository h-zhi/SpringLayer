package com.example.layer.sys.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Hzhi
 * @Date 2022-04-25 9:34
 * @description
 **/
@Data
@NoArgsConstructor
public class ModifySysConfigVO extends SysConfigVO {
    @ApiModelProperty(value = "参数ID")
    private String configId;
}