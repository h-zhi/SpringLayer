package com.example.layer.sys.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhaoyl
 * @Date 2022-04-20
 * @description
 * 岗位信息VO
 * */
@Data
@NoArgsConstructor
public class SysPostVO {

    @ApiModelProperty(value = "岗位编码")
    private String postCode;
    @ApiModelProperty(value = "岗位名称")
    private String postName;
    @ApiModelProperty(value = "显示顺序")
    private Integer postSort;
    @ApiModelProperty(value = "状态（0正常 1停用）")
    private Integer status;
    @ApiModelProperty(value = "备注")
    private String remark;

}
