package com.example.layer.sys.application;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.layer.sys.engine.domain.core.SysRole;
import com.example.layer.sys.engine.domain.service.SysRoleDomainService;
import com.example.layer.sys.engine.service.SysRoleService;
import org.springlayer.core.boot.context.CurrentUser;
import com.example.layer.sys.dto.SysRoleDTO;
import com.example.layer.sys.vo.ModifySysRoleVO;
import com.example.layer.sys.vo.SysRoleDeptVO;
import com.example.layer.sys.vo.SysRoleVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Author zhaoyl
 * @Date 2022-04-20
 * @description 角色管理信息
 **/
@Service
public class SysRoleApplicationService {

    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private SysRoleDomainService sysRoleDomainService;

    /**
     * 创建角色信息
     *
     * @param roleVo
     * @return boolean
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean createSysRole(SysRoleVO roleVo, CurrentUser currentUser) {
        // 校验: 校验创建角色信息参数
        sysRoleDomainService.checkCreateSysRoleParam(roleVo);
        // 保存: 保存角色信息
        return sysRoleService.savaSysRole(roleVo, currentUser);
    }

    /**
     * 查询角色详细信息，通过角色id
     *
     * @param roleId
     * @return
     */
    public SysRoleDTO querySysRoleDetail(Long roleId) {
        return sysRoleService.selectSysRoleCacheByRoleId(roleId);
    }

    /**
     * 修改角色信息
     *
     * @param modifySysRoleVO
     * @return boolean
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean modifySysRole(ModifySysRoleVO modifySysRoleVO, CurrentUser currentUser) {
        // 校验: 校验修改角色信息
        sysRoleDomainService.checkModifySysRoleParam(modifySysRoleVO);
        // 查询: 查询角色信息
        SysRole sysRole = sysRoleService.selectSysRoleByRoleId(Long.valueOf(modifySysRoleVO.getRoleId()));
        // 修改: 修改角色信息
        return sysRoleService.modifySysRole(sysRole, modifySysRoleVO, currentUser);
    }

    /**
     * 删除角色信息
     *
     * @param roleId
     * @return boolean
     */
    public boolean removeSysRole(Long roleId) {
        //查询: 查询角色
        SysRole sysRole = sysRoleService.selectSysRoleByRoleId(roleId);
        //校验: 角色是否存在用户
        sysRoleDomainService.checkExitsRoleUser(roleId);
        //删除
        return sysRoleService.delSysRole(sysRole);
    }

    /**
     * 分页条件查询角色数据
     *
     * @param current
     * @param size
     * @param roleName
     * @param category
     * @param status
     * @return IPage<SysRoleDTO>
     */
    public IPage<SysRoleDTO> querySysRolePage(Integer current, Integer size, String roleName, String category, Integer status) {
        return sysRoleService.querySysRolePage(current, size, roleName, category, status);
    }

    /**
     * 保存角色部门数据
     * @param sysRoleDeptVO
     */
    public boolean saveRoleDept(SysRoleDeptVO sysRoleDeptVO){
        //校验数据
        sysRoleDomainService.checkRoleDeptParam(sysRoleDeptVO);
        //保存数据
        return sysRoleService.saveRoleDept(sysRoleDeptVO);
    }

}
