package com.example.layer.sys.application;


import com.example.layer.sys.dto.SysMenuDTO;
import com.example.layer.sys.engine.domain.core.SysAclModuleMenu;
import com.example.layer.sys.engine.domain.core.SysMenu;
import com.example.layer.sys.engine.domain.service.SysAclModuleMenuDomainService;
import com.example.layer.sys.engine.service.SysAclModuleMenuService;
import com.example.layer.sys.engine.service.SysMenuService;
import com.example.layer.sys.vo.CreateSysAclModuleMenuVO;
import com.example.layer.sys.vo.ModifySysAclModuleMenuVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author zhaoyl
 * @Date 2022-04-27
 * @description 权限模块点菜单应用层
 **/
@Service
public class SysAclModuleMenuApplication {
    @Resource
    private SysAclModuleMenuDomainService sysAclModuleMenuDomainService;
    @Resource
    private SysAclModuleMenuService sysAclModuleMenuService;
    @Resource
    private SysMenuService sysMenuService;

    /**
     * 权限模块点添加菜单
     *
     * @param createSysAclModuleMenuVO
     * @return boolean
     */
    public boolean createAclModuleMenu(CreateSysAclModuleMenuVO createSysAclModuleMenuVO) {
        //非空校验
        sysAclModuleMenuDomainService.checkCreateAclModuleMenuParam(createSysAclModuleMenuVO);
        //删除
        sysAclModuleMenuDomainService.removeByAclModuleId(Long.valueOf(createSysAclModuleMenuVO.getAclModuleId()));
        //保存
        return sysAclModuleMenuService.savaSysAclModuleMenu(createSysAclModuleMenuVO);
    }

    /**
     * 权限模块点编辑菜单
     *
     * @param modifySysAclModuleMenuVO
     * @return boolean
     */
    public boolean modifyAclModuleMenu(ModifySysAclModuleMenuVO modifySysAclModuleMenuVO) {
        //非空校验
        sysAclModuleMenuDomainService.checkModifyAclModuleMenuParam(modifySysAclModuleMenuVO);
        //删除
        sysAclModuleMenuDomainService.removeByAclModuleId(Long.parseLong(modifySysAclModuleMenuVO.getAclModuleId()));
        //修改
        return sysAclModuleMenuService.modifySysAclModuleMenu(modifySysAclModuleMenuVO);
    }

    /**
     * 根据来源组和id获取菜单list
     *
     * @param sourceId
     * @param sourceModule
     * @return List<SysMenu>
     */
    public List<SysMenu> queryAclModuleMenuList(Long sourceId, String sourceModule) {
        //查询权限模块
        List<SysAclModuleMenu> sysAclModuleMenuList = sysAclModuleMenuService.queryAclModuleMenuList(sourceId, sourceModule);
        //查询菜单
        return sysAclModuleMenuService.queryMenuList(sysAclModuleMenuList);
    }

    /**
     * 查询模块菜单树
     *
     * @param sourceId
     * @param sourceModule
     * @return List<SysMenu>
     */
    public List<SysMenu> queryAclModuleMenuTree(Long sourceId, String sourceModule) {
        //查询菜单
        List<SysMenu> sysMenuList = this.queryAclModuleMenuList(sourceId, sourceModule);
        // 组装: 组装菜单树
        return sysMenuService.getTreeByColl(sysMenuList);
    }


    /**
     * 查询用户菜单权限列表
     *
     * @return List<SysMenuDTO>
     */
    public List<SysMenuDTO> userMenuList(Long userId) {
        // 查询: 查询菜单集合
        return sysAclModuleMenuService.queryUserSysMenuDtoByUserId(userId);
    }

    /**
     * 查询用户菜单权限树形列表
     *
     * @param userId
     * @return List<SysMenu>
     */
    public List<SysMenu> userMenuTreeData(Long userId) {
        // 查询: 查询用户菜单
        List<SysMenu> sysMenuList = sysAclModuleMenuService.queryUserSysMenuByUserId(userId);
        // 组装: 组装菜单树
        return sysMenuService.getTreeByColl(sysMenuList);
    }

}
