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
 * @Date 2022-04-27
 * @description 权限模块与功能点
 **/
@Data
@Builder
@AllArgsConstructor
@TableName(value = "sys_acl_module_menu")
@NoArgsConstructor
public class SysAclModuleMenu {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Long aclModuleId;
    private Long menuId;

    public SysAclModuleMenu(Long aclModuleId, Long menuId) {
        this.aclModuleId = aclModuleId;
        this.menuId = menuId;
    }

}
