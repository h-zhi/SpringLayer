package com.example.layer.sys.engine.domain.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.layer.sys.engine.domain.core.SysAclModuleMenu;
import com.example.layer.sys.engine.mapper.SysAclModuleMenuMapper;
import com.example.layer.sys.infrastructure.exception.SysAclModuleExceptionEnums;
import com.example.layer.sys.vo.CreateSysAclModuleMenuVO;
import com.example.layer.sys.vo.ModifySysAclModuleMenuVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springlayer.core.tool.utils.AssertUtil;

import java.util.List;

/**
 * @Author zhaoyl
 * @Date 2022-04-27
 * @description
 **/
@Slf4j
@Service
public class SysAclModuleMenuDomainService extends ServiceImpl<SysAclModuleMenuMapper, SysAclModuleMenu> {

    /**
     * 校验创建参数
     *
     * @param createSysAclModuleMenuVO
     */
    public void checkCreateAclModuleMenuParam(CreateSysAclModuleMenuVO createSysAclModuleMenuVO) {
        AssertUtil.isEmpty(createSysAclModuleMenuVO.getAclModuleId(), SysAclModuleExceptionEnums.NOT_EXIST_ACL_MODULE_ID.getMessage());
    }

    /**
     * 校验修改参数
     *
     * @param modifySysAclModuleMenuVO
     */
    public void checkModifyAclModuleMenuParam(ModifySysAclModuleMenuVO modifySysAclModuleMenuVO) {
        AssertUtil.isEmpty(modifySysAclModuleMenuVO.getAclModuleId(), SysAclModuleExceptionEnums.NOT_EXIST_ACL_MODULE_ID.getMessage());
        this.checkCreateAclModuleMenuParam(modifySysAclModuleMenuVO);
    }

    /**
     * 删除权限模块菜单通过aclModuleId
     *
     * @param aclModuleId
     * @return boolean
     */
    public boolean removeByAclModuleId(Long aclModuleId) {
        return this.remove(Wrappers.lambdaQuery(SysAclModuleMenu.class).eq(SysAclModuleMenu::getAclModuleId, aclModuleId));
    }

    /**
     * 查询集合通过 aclModuleId
     *
     * @param aclModuleId
     * @return List<SysAclModuleMenu>
     */
    public List<SysAclModuleMenu> getAclModuleMenuList(Long aclModuleId) {
        return this.list(Wrappers.lambdaQuery(SysAclModuleMenu.class).eq(SysAclModuleMenu::getAclModuleId, aclModuleId));
    }

    /**
     * 查询集合通过 menuId
     *
     * @param menuId
     * @return List<SysAclModuleMenu>
     */
    public List<SysAclModuleMenu> getAclModuleMenuListByMenuId(Long menuId) {
        return this.list(Wrappers.lambdaQuery(SysAclModuleMenu.class).eq(SysAclModuleMenu::getMenuId, menuId));
    }
}
