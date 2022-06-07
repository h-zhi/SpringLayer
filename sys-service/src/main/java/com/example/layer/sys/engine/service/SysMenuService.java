package com.example.layer.sys.engine.service;

import com.example.layer.sys.engine.domain.core.SysMenu;
import com.example.layer.sys.engine.domain.service.SysMenuDomainService;
import com.example.layer.sys.infrastructure.exception.SysMenuExceptionConstant;
import com.example.layer.sys.engine.components.redis.SysMenuKey;
import com.example.layer.sys.vo.ModifySysMenuVO;
import com.example.layer.sys.vo.SysMenuVO;
import org.springframework.stereotype.Service;
import org.springlayer.core.boot.context.CurrentUser;
import org.springlayer.core.redis.helper.RedisOperator;
import org.springlayer.core.tool.utils.AssertUtil;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author zhaoyl
 * @Date 2022-04-28
 * @description
 **/
@Service
public class SysMenuService {
    @Resource
    private RedisOperator redisOperator;
    @Resource
    private SysMenuDomainService sysMenuDomainService;

    /**
     * 保存菜单信息
     * @param sysMenuVO
     * @param currentUser
     * @return
     */
    public boolean savaSysMenu(SysMenuVO sysMenuVO, CurrentUser currentUser) {
        SysMenu sysMenu = sysMenuDomainService.buildSysMenu(sysMenuVO, currentUser);
        return sysMenuDomainService.save(sysMenu);
    }

    /**
     * 修改菜单信息
     * @param modifySysMenuVO
     * @param currentUser
     * @return
     */
    public boolean updateSysMenu(ModifySysMenuVO modifySysMenuVO, CurrentUser currentUser){
        SysMenu sysMenu = sysMenuDomainService.buildSysMenu(modifySysMenuVO, currentUser);
        redisOperator.del(SysMenuKey.SYS_MENU_INFO_MAP+sysMenu.getMenuId());
        return sysMenuDomainService.updateById(sysMenu);
    }
    /**
     * 校验菜单名称是否唯一
     * @param sysMenuVO
     */
   public void checkCreatExistSysMenu(SysMenuVO sysMenuVO){
       List<SysMenu> sysMenus = sysMenuDomainService.selectSysMenuByMenuName(sysMenuVO.getMenuName());
       AssertUtil.isBolEmpty(sysMenus.size()>0, SysMenuExceptionConstant.EXITS_MENU_NAME);
   }
    /**
     * 校验菜单名称是否唯一
     * @param modifySysMenuVO
     */
    public void checkModifyExistSysMenu(ModifySysMenuVO modifySysMenuVO){
        List<SysMenu> sysMenus = sysMenuDomainService.selectSysMenuByMenuName(modifySysMenuVO.getMenuName());
        if (sysMenus.size()>0){
            AssertUtil.isBolEmpty(!modifySysMenuVO.getMenuName().equals(sysMenus.get(0).getMenuName()), SysMenuExceptionConstant.EXITS_MENU_NAME);
        }
    }

    /**
     * 校验父菜单是否存在
     * @param parentId
     */
   public void checkSysMenuParentId(Long parentId){
      if (parentId!=0){
          SysMenu sysMenu = sysMenuDomainService.selectSysMenuByMenuId(parentId);
          AssertUtil.isEmpty(sysMenu, SysMenuExceptionConstant.NOT_MENU_PARENT);
      }
   }

    /**
     * 校验是否存在子菜单
     * @param menuId
     */
   public void checkSysMenuExitsSon(Long menuId){
       List<SysMenu> sysMenuList = sysMenuDomainService.selectSysMenuByDeptId(menuId);
       AssertUtil.isBolEmpty(sysMenuList.size()>0, SysMenuExceptionConstant.EXITS_MENU_SON);
   }


    /**
     * 组装菜单树
     * @param menuList
     * @param i 父节点Id
     * @return
     */
    public List<SysMenu> assembleChildPerms(List<SysMenu> menuList, int i) {
        List<SysMenu> returnList = new ArrayList<SysMenu>();
        for (Iterator<SysMenu> iterator = menuList.iterator(); iterator.hasNext(); ) {
            SysMenu t = (SysMenu) iterator.next();
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t.getParentId() == i) {
                this.recursionFn(menuList, t);
                returnList.add(t);
            }
        }
        return returnList;
    }

    /**
     * 递归列表
     *
     * @param list
     * @param t
     */
    private void recursionFn(List<SysMenu> list, SysMenu t) {
        // 得到子节点列表
        List<SysMenu> childList = this.getChildList(list, t);
        t.setChildren(childList);
        for (SysMenu tChild : childList) {
            if (this.hasChild(list, tChild)) {
                // 判断是否有子节点
                Iterator<SysMenu> it = childList.iterator();
                while (it.hasNext()) {
                    SysMenu n = (SysMenu) it.next();
                    recursionFn(list, n);
                }
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysMenu> getChildList(List<SysMenu> list, SysMenu t) {
        List<SysMenu> menuList = new ArrayList<SysMenu>();
        Iterator<SysMenu> it = list.iterator();
        while (it.hasNext()) {
            SysMenu n = (SysMenu) it.next();
            if (n.getParentId().longValue() == t.getMenuId().longValue()) {
                menuList.add(n);
            }
        }
        return menuList;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysMenu> list, SysMenu t) {
        return this.getChildList(list, t).size() > 0 ? true : false;
    }

    /**
     * 获取菜单树(通过菜单list菜单)
     *
     * @param list
     * @return
     */
    public  List<SysMenu> getTreeByColl(List<SysMenu> list) {
        Map<Long, SysMenu> map;
        List<SysMenu> treelist= new ArrayList<>();

        if (null==list||list.isEmpty()){
            return null;
        }
        // 将list集合转map并去重
        map = list.stream().collect(Collectors.toMap(SysMenu::getMenuId, a -> a,(k1, k2)->k1));
        // 如果id是父级的话就放入tree中treelist
        for (SysMenu menu : list) {
            if ("0".equals(map.get(menu.getParentId()))) {
                treelist.add(menu);
            } else {
                // 子级通过父id获取到父级的类型
                SysMenu parent = map.get(menu.getParentId());
                if(parent!=null){
                    // 父级获得子级，再将子级放到对应的父级中
                    parent.addChildren(menu);
                }else {
                    treelist.add(menu);
                }
            }
        }
        return treelist;
    }

}
