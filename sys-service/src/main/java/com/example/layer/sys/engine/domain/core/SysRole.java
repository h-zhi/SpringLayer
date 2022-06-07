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
 * @Date 2022-04-21
 * @description 角色实体
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "sys_role")
public class SysRole {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @TableId(type = IdType.ASSIGN_ID)
    private Long roleId;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色排序
     */
    private Integer roleSort;
    /**
     * 角色状态（0正常 1停用）
     **/
    private Integer status;
    /**
     * 角色标记
     */
    private String tag;
    /**
     * 是否删除 false否 true是
     */
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

    /**
     * 类别(ROLE、USER、DEPT)
     */
    private String category;

    /** 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限 5:个人数据权限） */
    private String dataScope;
}
