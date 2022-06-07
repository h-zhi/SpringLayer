package com.example.layer.sys.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author Hzhi
 * @Date 2022-04-25 9:32
 * @description
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysConfigDTO {
    /**
     * 参数名称
     */
    @ApiModelProperty(value = "参数ID")
    private String configId;
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
    @ApiModelProperty(value = "创建者")
    private String createBy;
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
}