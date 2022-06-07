package com.example.layer.sys.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author Hzhi
 * @Date 2022-04-26 9:42
 * @description
 **/
@Data
@NoArgsConstructor
public class SysApiLogDTO {
    @ApiModelProperty(value = "操作ID")
    private String operId;

    /**
     * 操作模块
     */
    @ApiModelProperty(value = "标题")
    private String title;

    /**
     * 请求方法
     */
    @ApiModelProperty(value = "请求方法")
    private String method;

    /**
     * 请求方式
     */
    @ApiModelProperty(value = "请求方式")
    private String requestMethod;

    /**
     * 操作类别（0其它 1后台用户 2手机端用户）
     */
    @ApiModelProperty(value = "操作类别（0其它 1后台用户 2手机端用户）")
    private Integer operatorType;

    /**
     * 操作人员
     */
    @ApiModelProperty(value = "操作人员")
    private String operName;

    /**
     * 请求url
     */
    @ApiModelProperty(value = "请求url")
    private String operUrl;

    /**
     * 操作地址
     */
    @ApiModelProperty(value = "操作地址")
    private String operIp;

    /**
     * 请求参数
     */
    @ApiModelProperty(value = "请求参数")
    private String operParam;

    /**
     * 操作时间
     */
    @ApiModelProperty(value = "操作时间")
    private LocalDateTime operTime;
    /**
     * 业务类型（0其它 1新增 2修改 3删除）
     */
    @ApiModelProperty(value = "业务类型（0其它 1新增 2修改 3删除）")
    private Integer businessType;
}