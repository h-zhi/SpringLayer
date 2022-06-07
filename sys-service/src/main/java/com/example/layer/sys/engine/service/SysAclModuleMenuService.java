package com.example.layer.sys.engine.service;

import com.alibaba.fastjson.JSON;
import com.example.layer.sys.dto.SysMenuDTO;
import com.example.layer.sys.engine.domain.core.SysAclModule;
import com.example.layer.sys.engine.domain.core.SysAclModuleMenu;
import com.example.layer.sys.engine.domain.core.SysMenu;
import com.example.layer.sys.engine.domain.enums.ComAuthAclModuleEnum;
import com.example.layer.sys.engine.domain.service.SysAclModuleMenuDomainService;
import com.example.layer.sys.engine.domain.service.SysMenuDomainService;
import com.example.layer.sys.engine.components.redis.SysAclModuleKey;
import com.example.layer.sys.vo.CreateSysAclModuleMenuVO;
import com.example.layer.sys.vo.ModifySysAclModuleMenuVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springlayer.core.redis.helper.RedisOperator;
import org.springlayer.core.secure.constant.RedisConstant;
import org.springlayer.core.tool.utils.StringUtil;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

/**
 * @Author zhaoyl
 * @Date 2022-04-27
 * @description 权限模块点菜单
 **/
@Service
public class SysAclModuleMenuService {
    @Resource
    private SysAclModuleMenuDomainService sysAclModuleMenuDomainService;
    @Resource
    private RedisOperator redisOperator;
    @Resource
    private SysAclModuleService sysAclModuleService;
    @Resource
    private SysMenuDomainService sysMenuDomainService;
    @Resource
    private SysUserRoleService sysUserRoleService;
    @Resource
    private SysUserDeptService sysUserDeptService;

    /**
     * 保存权限模块点菜单
     *
     * @param createSysAclModuleMenuVO
     * @return
     */
    public boolean savaSysAclModuleMenu(CreateSysAclModuleMenuVO createSysAclModuleMenuVO) {
        List<SysAclModuleMenu> sysAclModuleMenuList = new ArrayList<>();
        Long aclModuleId = Long.valueOf(createSysAclModuleMenuVO.getAclModuleId());
        List<String> menuIds = createSysAclModuleMenuVO.getMenuId();
        menuIds.stream().forEach(menuId -> {
            if (StringUtils.isNotBlank(menuId)) {
                sysAclModuleMenuList.add(new SysAclModuleMenu(aclModuleId, Long.valueOf(menuId)));
            }
        });
        //缓存权限覆盖
        this.operationCacheAclModule(aclModuleId);
        return sysAclModuleMenuDomainService.saveBatch(sysAclModuleMenuList);
    }

    /**
     * 修改
     *
     * @param modifySysAclModuleMenuVO
     * @return
     */
    public boolean modifySysAclModuleMenu(ModifySysAclModuleMenuVO modifySysAclModuleMenuVO) {
        List<SysAclModuleMenu> sysAclModuleMenuList = new ArrayList<>();
        Long aclModuleId = Long.valueOf(modifySysAclModuleMenuVO.getAclModuleId());
        List<String> menuIds = modifySysAclModuleMenuVO.getMenuId();
        menuIds.stream().forEach(menuId -> {
            if (StringUtils.isNotBlank(menuId)) {
                sysAclModuleMenuList.add(new SysAclModuleMenu(aclModuleId, Long.valueOf(menuId)));
            }
        });
        //缓存权限覆盖
        this.operationCacheAclModule(aclModuleId);
        return sysAclModuleMenuDomainService.saveBatch(sysAclModuleMenuList);
    }

    /**
     * 管理员授权用户，角色，部门数据处理 加入缓存处理
     *
     * @param aclModuleId
     */
    public void operationCacheAclModule(Long aclModuleId) {
        SysAclModule sysAclModule = sysAclModuleService.getSysAclModuleByIdAndStatus(aclModuleId);
        if (sysAclModule==null){
            return;
        }
        Integer aclModuleValue = ComAuthAclModuleEnum.getAclModuleValue(sysAclModule.getSourceModule());
        switch (aclModuleValue) {
            //修改用户
            case 1:
                this.getUSERAclModule(sysAclModule.getSourceId());
                break;
            //修改角色
            case 2:
                this.getROLEAclModule(sysAclModule.getSourceId());
                break;
            //修改部门
            case 3:
                this.getDEPTAclModule(sysAclModule.getSourceId());
                break;
            default:
                break;
        }

    }

    /**
     * 获取用户权限模块
     *
     * @param userId
     * @return Map--> key:url菜单，value:list<Long> 权限模块AclModuleId
     */
    public Map<String, List<Long>> getUSERAclModule(Long userId) {
        Map<String, List<Long>> map = new HashMap<>();
        List<SysMenu> sysMenuList = queryUserSysMenuByUserId(userId);
        sysMenuList.stream().forEach(sysMenu -> {
            List<Long> aclModuleIdCollection = this.getAclModuleIdCollection(userId, sysMenu.getUrl());
            map.put(sysMenu.getUrl(), aclModuleIdCollection);
        });
        //加入缓存
        redisOperator.set(RedisConstant.RESOURCE_ROLES_MAP + userId, JSON.toJSONString(map), 36000);
        return map;
    }

    /**
     * 修改角色全部用户权限缓存
     *
     * @param roleId
     */
    public void getROLEAclModule(Long roleId) {
        //查询用户ID
        List<Long> list = sysUserRoleService.selectUserIdsByRoleId(roleId);
        //查询角色用户
        list.stream().forEach(userId -> {
            this.getUSERAclModule(userId);
        });
    }

    /**
     * 修改部门全部用户权限缓存
     *
     * @param deptId
     * @return
     */
    public void getDEPTAclModule(Long deptId) {
        //查询用户ID
        List<Long> list = sysUserDeptService.selectUserIdsByDeptId(deptId);
        //查询部门用户
        list.stream().forEach(userId -> {
            this.getUSERAclModule(userId);
        });
    }

    /**
     * 根据来源组和id获取菜单
     *
     * @param sourceId
     * @param sourceModule
     * @return
     */
    public List<SysAclModuleMenu> queryAclModuleMenuList(Long sourceId, String sourceModule) {
        List<SysAclModuleMenu> list = new ArrayList<>();
        SysAclModule sysAclModule = sysAclModuleService.getOneSysAclModuleBySource(sourceId, sourceModule);
        if (null == sysAclModule) {
            return list;
        }
        //查询缓存
        String SysAclModuleMenuListStr = redisOperator.get(SysAclModuleKey.SYS_ACL_MODULE_INFO_MAP + sysAclModule.getAclModuleId());
        // 校验: 校验缓存不为空直接返回
        if (StringUtil.isNotEmpty(SysAclModuleMenuListStr)) {
            return JSON.parseArray(SysAclModuleMenuListStr, SysAclModuleMenu.class);
        }
        list = sysAclModuleMenuDomainService.getAclModuleMenuList(sysAclModule.getAclModuleId());
        redisOperator.set(SysAclModuleKey.SYS_ACL_MODULE_INFO_MAP + sysAclModule.getAclModuleId(),JSON.toJSONString(list),36000);
        return list;
    }

    /**
     * 查询全部权限菜单
     *
     * @param sysAclModuleMenus
     * @return
     */
    public List<SysMenu> queryMenuList(List<SysAclModuleMenu> sysAclModuleMenus) {
        List<SysMenu> sysMenuList = new ArrayList<>();
        sysAclModuleMenus.stream().forEach(aclModuleMenu -> {
            SysMenu sysMenu = sysMenuDomainService.selectSysMenuByMenuId(aclModuleMenu.getMenuId());
            sysMenuList.add(sysMenu);
        });
        return sysMenuList;
    }

    /**
     * 查询用户菜单权限（包括角色和部门）
     *
     * @param userId
     * @return
     */
    public List<SysAclModuleMenu> queryUserAllAclModuleMenu(Long userId) {
        List<SysAclModule> sysAclModules = sysAclModuleService.queryUserAllAclModule(userId);
        List<SysAclModuleMenu> sysAclModuleMenuList = new ArrayList<>();
        sysAclModules.stream().forEach(aclModule -> {
            List<SysAclModuleMenu> aclModuleMenuList = sysAclModuleMenuDomainService.getAclModuleMenuList(aclModule.getAclModuleId());
            sysAclModuleMenuList.addAll(aclModuleMenuList);
        });
        //菜单去重
        ArrayList<SysAclModuleMenu> collect = sysAclModuleMenuList.stream().collect(
                collectingAndThen(
                        toCollection(() -> new TreeSet<>(Comparator.comparing(SysAclModuleMenu::getMenuId))), ArrayList::new)
        );

        return collect;
    }

    /**
     * 查询菜单List通过用户ID
     *
     * @param userId
     * @return List<SysMenu>
     */
    public List<SysMenu> queryUserSysMenuByUserId(Long userId) {
        List<SysAclModuleMenu> sysAclModuleMenuList = this.queryUserAllAclModuleMenu(userId);
        List<SysMenu> sysMenuList = new ArrayList<>();
        sysAclModuleMenuList.stream().forEach(aclModuleMenu -> {
            SysMenu sysMenu = sysMenuDomainService.selectSysMenuByMenuId(aclModuleMenu.getMenuId());
            sysMenuList.add(sysMenu);
        });
        return sysMenuList;
    }


    /**
     * 查询菜单List通过用户ID
     *
     * @param userId
     * @return List<SysMenuDTO>
     */
    public List<SysMenuDTO> queryUserSysMenuDtoByUserId(Long userId) {
        List<SysMenu> sysMenuList = this.queryUserSysMenuByUserId(userId);
        return sysMenuDomainService.convertToSysMenuDTOList(sysMenuList);
    }

    /**
     * 获取AclModuleId通过url和用户ID
     * 查询用户所属url的权限ID
     *
     * @param url
     * @return
     */
    public List<Long> getAclModuleIdCollection(Long userId, String url) {
        //用户
        List<SysAclModule> sysAclModules = sysAclModuleService.queryUserAllAclModule(userId);
        List<Long> idListUser = sysAclModules.stream().map(SysAclModule::getAclModuleId).collect(Collectors.toList());
        //url
        List<SysMenu> sysMenuList = sysMenuDomainService.selectSysMenuByUrl(url);
        List<SysAclModuleMenu> list = new ArrayList<>();
        sysMenuList.stream().forEach(sysMenu -> {
            List<SysAclModuleMenu> aclModuleMenuListByMenuId = sysAclModuleMenuDomainService.getAclModuleMenuListByMenuId(sysMenu.getMenuId());
            list.addAll(aclModuleMenuListByMenuId);
        });
        List<Long> idListUrl = list.stream().map(SysAclModuleMenu::getAclModuleId).collect(Collectors.toList());
        //交集
        List<Long> collectList = idListUser.stream().filter(t -> idListUrl.contains(t)).collect(Collectors.toList());
        return collectList;
    }
}
