package com.example.layer.sys.engine.domain.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.layer.sys.engine.domain.core.SysUserRole;
import com.example.layer.sys.engine.mapper.SysUserRoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author zhaoyl
 * @Date 2022-04-26
 * @description
 **/
@Slf4j
@Service
public class SysUserRoleDomainService extends ServiceImpl<SysUserRoleMapper, SysUserRole> {

    /**
     * 删除用户角色信息
     * 通过用户userId
     * @param userId
     */
    public void delSysUserRoleByUserId(Long userId) {
        this.remove(Wrappers.lambdaQuery(SysUserRole.class).eq(SysUserRole::getUserId, userId));
    }

    /**
     * 根据角色roleId查询用户角色list
     * @param roleId
     * @return
     */
    public List<SysUserRole> queryUserRolesByRoleId(long roleId) {
        return this.list(Wrappers.lambdaQuery(SysUserRole.class).eq(SysUserRole::getRoleId, roleId));
    }
    /**
     * 根据用户id查询用户角色list
     * @param userId
     * @return
     */
    public List<SysUserRole> queryUserRolesByUserId(long userId) {
        return this.list(Wrappers.lambdaQuery(SysUserRole.class).eq(SysUserRole::getUserId, userId));
    }


}
