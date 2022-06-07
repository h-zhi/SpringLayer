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
 * @Date 2022-04-25 9:15
 * @description 系统参数
 **/
@Data
@Builder
@AllArgsConstructor
@TableName(value = "sys_config")
@NoArgsConstructor
public class SysConfig {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @TableId(type = IdType.ASSIGN_ID)
    private Long configId;

    /**
     * 参数名称
     */
    private String configName;

    /**
     * 参数键
     */
    private String configKey;

    /**
     * 参数值
     */
    private String configValue;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 创建事件
     */
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    private Long updateBy;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否删除
     */
    private Boolean isDeleted;
}