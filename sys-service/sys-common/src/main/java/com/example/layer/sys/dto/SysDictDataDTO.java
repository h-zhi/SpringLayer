package com.example.layer.sys.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author zhaoyl
 * @Date 2022-04-23
 * @description 字典数据
 **/
@Data
@NoArgsConstructor
public class SysDictDataDTO {
    @ApiModelProperty(value = "字典数据主键")
    private String dictCode;
    @ApiModelProperty(value = "字典排序")
    private Long dictSort;
    @ApiModelProperty(value = "字典标签")
    private String dictLabel;
    @ApiModelProperty(value = "字典键值")
    private String dictValue;
    @ApiModelProperty(value = "字典类型")
    private String dictType;
    @ApiModelProperty(value = "样式属性")
    private String cssClass;
    @ApiModelProperty(value = "表格字典样式")
    private String listClass;
    @ApiModelProperty(value = "是否默认（Y是 N否）")
    private String isDefault;
    @ApiModelProperty(value = "状态（0正常 1停用）")
    private Integer status;
    /**
     * 创建人
     */
    private Long createBy;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新人
     */
    private Long updateBy;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    /**  */
    @ApiModelProperty(value = "备注")
    private String remark;
}
