package com.example.layer.sys.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author zhaoyl
 * @Date 2022-04-23
 * @description 字典类型dto
 **/
@Data
@NoArgsConstructor
public class SysDictTypeDTO {
    @ApiModelProperty(value = "字典类型主键")
    private String dictId;
    @ApiModelProperty(value = "名称")
    private String dictName;
    @ApiModelProperty(value = "类型")
    private String dictType;
    @ApiModelProperty(value = "状态（0正常 1停用）")
    private Integer status;
    @ApiModelProperty(value = "创建人")
    private Long createBy;
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
    @ApiModelProperty(value = "更新人")
    private Long updateBy;
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;
    @ApiModelProperty(value = "备注")
    private String remark;
}

