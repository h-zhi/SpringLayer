package com.example.layer.sys.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author Hzhi
 * @Date 2022-04-25 13:55
 * @description
 **/
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class SysLogininforDTO {
    @ApiModelProperty(value = "登录日志ID")
    private String infoId;
    @ApiModelProperty(value = "登录名称")
    private String loginName;
    @ApiModelProperty(value = "IP")
    private String ipaddr;
    @ApiModelProperty(value = "地址")
    private String loginLocation;
    @ApiModelProperty(value = "浏览器类型")
    private String browser;
    @ApiModelProperty(value = "操作系统")
    private String os;
    @ApiModelProperty(value = "登录状态（0成功 1失败）")
    private Integer status;
    @ApiModelProperty(value = "提示消息")
    private String msg;
    @ApiModelProperty(value = "访问时间")
    private LocalDateTime loginTime;
}