package com.example.layer.sys.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author Hzhi
 * @Date 2022-04-21 9:25
 * @description 部门实体
 **/
@Data
@NoArgsConstructor
public class SysDeptVO implements Serializable {

    /**
     * 父ID
     **/
    @ApiModelProperty(value = "父ID")
    private String parentId;
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
}