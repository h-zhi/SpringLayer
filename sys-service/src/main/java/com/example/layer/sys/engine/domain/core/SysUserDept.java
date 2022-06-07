package com.example.layer.sys.engine.domain.core;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author zhaoyl
 * @Date 2022-04-26
 * @description 用户与部门关系实体
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName(value = "sys_user_dept")
public class SysUserDept {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Long deptId;
    private Long userId;

    public SysUserDept(Long deptId, Long userId) {
        this.deptId = deptId;
        this.userId = userId;
    }
}
