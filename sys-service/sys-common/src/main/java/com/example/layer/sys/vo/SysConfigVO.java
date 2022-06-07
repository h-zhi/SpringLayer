package com.example.layer.sys.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Hzhi
 * @Date 2022-04-25 9:32
 * @description
 **/
@Data
@NoArgsConstructor
public class SysConfigVO {
    /**
     * 参数名称
     */
    @ApiModelProperty(value = "参数名称")
    private String configName;

    /**
     * 参数键
     */
    @ApiModelProperty(value = "参数键")
    private String configKey;

    /**
     * 参数值
     */
    @ApiModelProperty(value = "参数值")
    private String configValue;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
}
