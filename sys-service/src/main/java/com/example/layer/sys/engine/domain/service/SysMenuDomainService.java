package com.example.layer.sys.engine.domain.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.layer.sys.dto.SysMenuDTO;
import com.example.layer.sys.engine.components.redis.SysMenuKey;
import com.example.layer.sys.engine.domain.core.SysMenu;
import com.example.layer.sys.engine.mapper.SysMenuMapper;
import com.example.layer.sys.infrastructure.exception.SysMenuExceptionConstant;
import com.example.layer.sys.vo.ModifySysMenuVO;
import com.example.layer.sys.vo.SysMenuVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springlayer.core.boot.context.CurrentUser;
import org.springlayer.core.redis.helper.RedisOperator;
import org.springlayer.core.tool.utils.AssertUtil;
import org.springlayer.core.tool.utils.StringUtil;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author zhaoyl
 * @Date 2022-04-28
 * @description
 **/
@Slf4j
@Service
public class SysMenuDomainService extends ServiceImpl<SysMenuMapper, SysMenu> {

    @Resource
    private RedisOperator redisOperator;

    /**
     * 构建菜单信息
     *
     * @param sysMenuVO
     * @param currentUser
     * @return SysMenu
     */
    public SysMenu buildSysMenu(SysMenuVO sysMenuVO, CurrentUser currentUser) {
        return SysMenu.builder()
                .url(sysMenuVO.getUrl())
                .menuName(sysMenuVO.getMenuName())
                .menuType(sysMenuVO.getMenuType())
                .orderNum(sysMenuVO.getOrderNum())
                .parentId(Long.valueOf(sysMenuVO.getParentId()))
                .target(sysMenuVO.getTarget())
                .remark(sysMenuVO.getRemark())
                .viewPerms(sysMenuVO.getViewPerms())
                .icon(sysMenuVO.getIcon())
                .visible(sysMenuVO.getVisible())
                .createBy(currentUser.getUserId())
                .createTime(LocalDateTime.now())
                .build();
    }

    /**
     * 构建菜单信息
     *
     * @param modifySysMenuVO
     * @param currentUser
     * @return SysMenu
     */
    public SysMenu buildSysMenu(ModifySysMenuVO modifySysMenuVO, CurrentUser currentUser) {
        return SysMenu.builder()
                .menuId(Long.valueOf(modifySysMenuVO.getMenuId()))
                .url(modifySysMenuVO.getUrl())
                .menuName(modifySysMenuVO.getMenuName())
                .menuType(modifySysMenuVO.getMenuType())
                .orderNum(modifySysMenuVO.getOrderNum())
                .parentId(Long.valueOf(modifySysMenuVO.getParentId()))
                .target(modifySysMenuVO.getTarget())
                .remark(modifySysMenuVO.getRemark())
                .viewPerms(modifySysMenuVO.getViewPerms())
                .icon(modifySysMenuVO.getIcon())
                .visible(modifySysMenuVO.getVisible())
                .updateBy(currentUser.getUserId())
                .updateTime(LocalDateTime.now())
                .build();
    }

    /**
     * 转换菜单信息
     *
     * @param sysMenu
     * @return SysMenuDTO
     */
    public SysMenuDTO convertToSysMenuDTO(SysMenu sysMenu) {
        SysMenuDTO menuDTO = new SysMenuDTO();
        BeanUtils.copyProperties(sysMenu, menuDTO);
        menuDTO.setMenuId(sysMenu.getMenuId().toString());
        return menuDTO;
    }

    /**
     * 转换菜单list
     *
     * @param sysMenuList
     * @return List<SysMenuDTO>
     */
    public List<SysMenuDTO> convertToSysMenuDTOList(List<SysMenu> sysMenuList) {
        return sysMenuList.stream().map(sysMenu -> convertToSysMenuDTO(sysMenu)).collect(Collectors.toList());
    }

    /**
     * 根据菜单menuId查询菜单信息
     *
     * @param menuId
     * @return SysMenu
     */
    public SysMenu selectSysMenuByMenuId(Long menuId) {
        String sysMenuStr = redisOperator.get(SysMenuKey.SYS_MENU_INFO_MAP + menuId);
        SysMenu sysMenu = new SysMenu();
        if (StringUtil.isEmpty(sysMenuStr)) {
            sysMenu = this.getById(menuId);
            redisOperator.set(SysMenuKey.SYS_MENU_INFO_MAP + menuId, JSON.toJSONString(sysMenu), 36000);
            return sysMenu;
        }
        return JSON.parseObject(sysMenuStr, SysMenu.class);
    }

    /**
     * 根据菜单名称查询
     *
     * @param menuName
     * @return List<SysMenu>
     */
    public List<SysMenu> selectSysMenuByMenuName(String menuName) {
        //防止脏数据
        return this.list(Wrappers.lambdaQuery(SysMenu.class).eq(SysMenu::getMenuName, menuName));
    }

    /**
     * 查询菜单List
     *
     * @param url
     * @return List<SysMenu>
     */
    public List<SysMenu> selectSysMenuByUrl(String url) {
        return this.list(Wrappers.lambdaQuery(SysMenu.class).eq(SysMenu::getUrl, url));
    }

    /**
     * 根据菜单parentId查询父菜单
     *
     * @param parentId
     * @return List<SysMenu>
     */
    public List<SysMenu> selectSysMenuByDeptId(Long parentId) {
        return this.list(Wrappers.lambdaQuery(SysMenu.class).eq(SysMenu::getParentId, parentId));
    }

    /**
     * 校验非空参数
     *
     * @param sysMenuVO
     */
    public void checkCreatSysMenuParam(SysMenuVO sysMenuVO) {
        AssertUtil.isEmpty(sysMenuVO.getMenuName(), SysMenuExceptionConstant.NOT_MENU_NAME);
        AssertUtil.isEmpty(sysMenuVO.getParentId(), SysMenuExceptionConstant.NOT_MENU_PARENT_ID);
        AssertUtil.isEmpty(sysMenuVO.getOrderNum(), SysMenuExceptionConstant.NOT_MENU_OEDER_NUM);
        AssertUtil.isEmpty(sysMenuVO.getTarget(), SysMenuExceptionConstant.NOT_MENU_TARGET);
        AssertUtil.isEmpty(sysMenuVO.getVisible(), SysMenuExceptionConstant.NOT_MENU_VISIBLE);
    }

    /**
     * 校验非空参数
     *
     * @param modifySysMenuVO
     */
    public void checkModifySysMenuParam(ModifySysMenuVO modifySysMenuVO) {
        AssertUtil.isEmpty(modifySysMenuVO.getMenuId(), SysMenuExceptionConstant.NOT_MENU_ID);
        this.checkCreatSysMenuParam(modifySysMenuVO);
    }

    /**
     * 查询菜单列表缓存
     *
     * @return List<SysMenu>
     */
    public List<SysMenu> selectSysMenuCacheList() {
        String sysMenuStr = redisOperator.get(SysMenuKey.SYS_MENU_INFO_LIST);
        List<SysMenu> sysMenuList = null;
        if (StringUtil.isEmpty(sysMenuStr)) {
            sysMenuList = this.list(Wrappers.lambdaQuery(SysMenu.class).orderByAsc(SysMenu::getOrderNum));
            redisOperator.set(SysMenuKey.SYS_MENU_INFO_LIST, JSON.toJSONString(sysMenuList));
            return sysMenuList;
        }
        return JSON.parseArray(sysMenuStr, SysMenu.class);
    }
}
