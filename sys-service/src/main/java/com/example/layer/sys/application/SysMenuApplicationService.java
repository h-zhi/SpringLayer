package com.example.layer.sys.application;


import com.example.layer.sys.engine.domain.core.SysMenu;
import com.example.layer.sys.engine.domain.service.SysMenuDomainService;
import com.example.layer.sys.engine.service.SysMenuService;
import org.springlayer.core.boot.context.CurrentUser;
import org.springlayer.core.redis.helper.RedisOperator;
import com.example.layer.sys.dto.SysMenuDTO;
import com.example.layer.sys.engine.components.redis.SysMenuKey;
import com.example.layer.sys.vo.ModifySysMenuVO;
import com.example.layer.sys.vo.SysMenuVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author zhaoyl
 * @Date 2022-04-28
 * @description 系统菜单应用层
 **/
@Service
public class SysMenuApplicationService {
    @Resource
    private SysMenuDomainService sysMenuDomainService;
    @Resource
    private SysMenuService sysMenuService;
    @Resource
    private RedisOperator redisOperator;

    /**
     * 保存菜单信息
     *
     * @param sysMenuVO
     * @param currentUser
     * @return boolean
     */
    public boolean createSysMenu(SysMenuVO sysMenuVO, CurrentUser currentUser) {
        //参数非空校验
        sysMenuDomainService.checkCreatSysMenuParam(sysMenuVO);
        //参数唯一校验
        sysMenuService.checkCreatExistSysMenu(sysMenuVO);
        //校验父菜单节点
        sysMenuService.checkSysMenuParentId(Long.valueOf(sysMenuVO.getParentId()));
        //保存菜单
        return sysMenuService.savaSysMenu(sysMenuVO, currentUser);
    }

    /**
     * 修改菜单信息
     *
     * @param modifySysMenuVO
     * @param currentUser
     * @return
     */
    public boolean modifySysMenu(ModifySysMenuVO modifySysMenuVO, CurrentUser currentUser) {
        //参数非空校验
        sysMenuDomainService.checkModifySysMenuParam(modifySysMenuVO);
        //参数唯一校验
        sysMenuService.checkModifyExistSysMenu(modifySysMenuVO);
        //校验父菜单节点
        sysMenuService.checkSysMenuParentId(Long.valueOf(modifySysMenuVO.getParentId()));
        //修改
        return sysMenuService.updateSysMenu(modifySysMenuVO, currentUser);
    }

    /**
     * 查询菜单列表
     *
     * @return
     */
    public List<SysMenuDTO> querySysMenuList() {
        //查询列表
        List<SysMenu> sysMenuList = sysMenuDomainService.selectSysMenuCacheList();
        //转换: 转换DTO
        return sysMenuDomainService.convertToSysMenuDTOList(sysMenuList);
    }

    /**
     * 删除菜单
     *
     * @param menuId
     * @return
     */
    public boolean removeSysMenu(Long menuId) {
        SysMenu sysMenu = sysMenuDomainService.selectSysMenuByMenuId(menuId);
        if (null == sysMenu) {
            return true;
        }
        //校验: 是否存在子菜单
        sysMenuService.checkSysMenuExitsSon(menuId);
        // 缓存: 清除缓存
        redisOperator.del(SysMenuKey.SYS_MENU_INFO_MAP + menuId);
        // 移除: 移除菜单
        return sysMenuDomainService.removeById(menuId);
    }

    /**
     * 查询菜单树形结构
     *
     * @return
     */
    public List<SysMenu> menuTreeData() {
        // 查询: 查询菜单集合
        List<SysMenu> sysMenuList = sysMenuDomainService.selectSysMenuCacheList();
        // 组装: 组装菜单树
        return sysMenuService.assembleChildPerms(sysMenuList, 0);
    }

}
