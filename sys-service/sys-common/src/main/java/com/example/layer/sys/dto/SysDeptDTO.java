package com.example.layer.sys.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author Hzhi
 * @Date 2022-04-21 10:36
 * @description
 **/
@Data
public class SysDeptDTO {
    @ApiModelProperty(value = "ID")
    private String deptId;
    /**
     * 部门名称
     **/
    @ApiModelProperty(value = "部门名称")
    private String deptName;
    /**
     * 显示顺序
     **/
    @ApiModelProperty(value = "显示顺序")
    private Integer orderNum;
    /**
     * 部门状态（0正常 1停用）
     **/
    @ApiModelProperty(value = "部门状态（0正常 1停用）")
    private Integer status;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
}
