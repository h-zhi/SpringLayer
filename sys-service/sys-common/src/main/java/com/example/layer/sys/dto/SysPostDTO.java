package com.example.layer.sys.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author zhaoyl
 * @Date 2022-04-20
 * @description
 * 岗位信息
 * */
@Data
@NoArgsConstructor
public class SysPostDTO {
    @ApiModelProperty(value = "岗位id")
    private String postId;
    @ApiModelProperty(value = "岗位编码")
    private String postCode;
    @ApiModelProperty(value = "岗位名称")
    private String postName;
    @ApiModelProperty(value = "显示顺序")
    private Integer postSort;
    @ApiModelProperty(value = "状态（0正常 1停用）")
    private Integer status;
    @ApiModelProperty(value = "创建者")
    private String createBy;
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
    @ApiModelProperty(value = "备注")
    private String remark;
}
