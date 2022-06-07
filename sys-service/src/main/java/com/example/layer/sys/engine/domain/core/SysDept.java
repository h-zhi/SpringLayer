package com.example.layer.sys.engine.domain.core;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Hzhi
 * @Date 2022-04-21 9:25
 * @description 部门实体
 **/
@Data
@Builder
@AllArgsConstructor
@TableName(value = "sys_dept")
@NoArgsConstructor
public class SysDept implements Serializable {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @TableId(type = IdType.ASSIGN_ID)
    private Long deptId;
    /**
     * 父ID
     **/
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long parentId;
    /**
     * 部门名称
     **/
    private String deptName;

    /**
     * 组级别列表
     */
    private String ancestors;
    /**
     * 显示顺序
     **/
    private Integer orderNum;
    /**
     * 部门状态（0正常 1停用）
     **/
    private Integer status;
    /**
     * 是否已删除（true：是，false：否）
     **/
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
     * 描述
     */
    private String description;

    /**
     * 子菜单
     */
    @TableField(exist = false)
    private List<SysDept> children = new ArrayList<SysDept>();

    public SysDept(Long parentId, String deptName, String ancestors, Integer orderNum, Integer status, Boolean isDeleted, Long createBy, LocalDateTime createTime, Long updateBy, LocalDateTime updateTime, String description) {
        this.parentId = parentId;
        this.deptName = deptName;
        this.ancestors = ancestors;
        this.orderNum = orderNum;
        this.status = status;
        this.isDeleted = isDeleted;
        this.createBy = createBy;
        this.createTime = createTime;
        this.updateBy = updateBy;
        this.updateTime = updateTime;
        this.description = description;
    }
}