package com.example.layer.sys.application;

import com.example.layer.sys.engine.domain.service.SysAclModuleDomainService;
import com.example.layer.sys.engine.domain.service.SysAclModuleMenuDomainService;
import com.example.layer.sys.engine.service.SysAclModuleService;
import com.example.layer.sys.vo.ModifySysAclModuleVO;
import com.example.layer.sys.vo.SysAclModuleVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author zhaoyl
 * @Date 2022-04-26
 * @description 权限模块点应用层
 **/
@Service
public class SysAclModuleApplicationService {
    @Resource
    private SysAclModuleDomainService sysAclModuleDomainService;
    @Resource
    private SysAclModuleService sysAclModuleService;
    @Resource
    private SysAclModuleMenuDomainService sysAclModuleMenuDomainService;

    /**
     * 创建权限模块
     *
     * @param sysAclModuleVO
     * @return
     */
    public boolean createAclModule(SysAclModuleVO sysAclModuleVO) {
        //非空校验
        sysAclModuleDomainService.checkNonEmptySysAclModule(sysAclModuleVO);
        //唯一校验
        sysAclModuleService.checkExistSysAclModule(Long.parseLong(sysAclModuleVO.getSourceId()), sysAclModuleVO.getSourceModule());
        //保存
        return sysAclModuleService.savaSysAclModule(sysAclModuleVO);
    }

    /**
     * 修改模块权限
     *
     * @param modifySysAclModuleVO
     * @return
     */
    public boolean modifyAclModule(ModifySysAclModuleVO modifySysAclModuleVO) {
        //非空校验
        sysAclModuleDomainService.checkModifyNonEmptySysAclModule(modifySysAclModuleVO);
        //唯一校验
        sysAclModuleService.checkModifyExistSysAclModule(modifySysAclModuleVO);
        //修改
        return sysAclModuleService.modifySysAclModule(modifySysAclModuleVO);
    }

    /**
     * 删除模块权限
     *
     * @param aclModuleId
     * @return
     */
    public boolean removeAclModule(Long aclModuleId) {
        sysAclModuleDomainService.removeById(aclModuleId);
        return sysAclModuleMenuDomainService.removeByAclModuleId(aclModuleId);
    }

    /**
     * 修改权限模块状态
     *
     * @param aclModuleId
     * @param status
     * @return
     */
    public boolean updateAclModuleStatus(Long aclModuleId, Integer status) {
        return sysAclModuleDomainService.updateAclModuleStatus(aclModuleId, status);
    }

}
