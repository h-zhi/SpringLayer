package com.example.layer.sys.engine.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.layer.sys.engine.domain.core.SysUserRole;
import com.example.layer.sys.engine.domain.service.SysUserRoleDomainService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author zhaoyl
 * @Date 2022-04-26
 * @description 用户和角色信息
 **/
@Service
public class SysUserRoleService {

    @Resource
    private SysUserRoleDomainService sysUserRoleDomainService;

    /**
     * 保存系统用户和角色关系
     * @param roleIds 角色ID集合
     * @param userId  用户ID
     * @return boolean
     */
    public boolean savaSysUserRole(List<String> roleIds, Long userId) {
        List<SysUserRole> sysUserRoles = new ArrayList<>();
        roleIds.stream().forEach(roleId -> {
            if (StringUtils.isNotBlank(roleId)) {
                sysUserRoles.add(new SysUserRole(Long.valueOf(roleId), userId));
            }
        });
        return sysUserRoleDomainService.saveBatch(sysUserRoles);
    }

    /**
     * 修改系统用户和角色关系
     * @param roleIds
     * @param userId
     * @return
     */
    public boolean modifySysUserRole(List<String> roleIds, Long userId) {
        // 删除用户角色关系
        sysUserRoleDomainService.delSysUserRoleByUserId(userId);
        // 批量保存系统用户角色
        return this.savaSysUserRole(roleIds, userId);
    }

    /**
     * 根据角色roleId判断是否存在用户
     * @param roleId
     * @return 存在 true
     */
    public boolean existUserByRole(long roleId) {
        List<SysUserRole> sysUserRoles = sysUserRoleDomainService.queryUserRolesByRoleId(roleId);
        if (sysUserRoles.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 通过用户id查询角色Id
     * @param userId
     * @return
     */
    public List<Long> selectRoleIdsByUserId(Long userId) {
        List<SysUserRole> list = sysUserRoleDomainService.list(Wrappers.lambdaQuery(SysUserRole.class).eq(SysUserRole::getUserId, userId));
        return list.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
    }

    /**
     * 通过角色id查询用户Id
     * @param roleId
     * @return
     */
    public List<Long> selectUserIdsByRoleId(Long roleId){
        List<SysUserRole> list = sysUserRoleDomainService.list(Wrappers.lambdaQuery(SysUserRole.class).eq(SysUserRole::getRoleId, roleId));
        return list.stream().map(SysUserRole::getUserId).collect(Collectors.toList());
    }

}
