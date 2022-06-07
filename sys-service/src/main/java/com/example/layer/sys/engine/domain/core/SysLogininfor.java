package com.example.layer.sys.engine.domain.core;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
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
@AllArgsConstructor
@Builder
@TableName(value = "sys_logininfor")
public class SysLogininfor {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @TableId(type = IdType.ASSIGN_ID)
    private Long infoId;
    private String loginName;
    private String ipaddr;
    private String loginLocation;
    private String browser;
    private String os;
    private Integer status;
    private String msg;
    private LocalDateTime loginTime;
}
