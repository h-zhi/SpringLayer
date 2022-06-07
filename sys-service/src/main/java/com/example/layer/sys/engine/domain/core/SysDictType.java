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
 * @Author zhaoyl
 * @Date 2022-04-23
 * @description 字典类型实体
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName(value = "sys_dict_type")
public class SysDictType {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @TableId(type = IdType.ASSIGN_ID)
    private Long dictId;
    /**
     * 字典名称
     * */
    private String dictName;
    /**
     * 字典类型
     * */
    private String dictType;
    /**
     * 状态（0正常 1停用）
     * */
    private Integer status;
    /**
     * 是否删除 false否 true是
     * */
    private Boolean isDeleted;
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
    /** 备注 */
    private String remark;

}
