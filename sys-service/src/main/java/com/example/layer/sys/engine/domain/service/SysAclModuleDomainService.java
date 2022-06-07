package com.example.layer.sys.engine.domain.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.layer.sys.engine.domain.core.SysAclModule;
import com.example.layer.sys.engine.domain.enums.ComStatusEnum;
import com.example.layer.sys.engine.mapper.SysAclModuleMapper;
import com.example.layer.sys.infrastructure.exception.SysAclModuleExceptionEnums;
import com.example.layer.sys.vo.ModifySysAclModuleVO;
import com.example.layer.sys.vo.SysAclModuleVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springlayer.core.mp.support.Condition;
import org.springlayer.core.tool.utils.AssertUtil;
import org.springlayer.core.tool.utils.StringUtil;

import java.util.List;

/**
 * @Author zhaoyl
 * @Date 2022-04-26
 * @description
 **/
@Slf4j
@Service
public class SysAclModuleDomainService extends ServiceImpl<SysAclModuleMapper, SysAclModule> {

    /**
     * 构建权限模块点
     *
     * @param sysAclModuleVO
     * @return
     */
    public SysAclModule buildSysAclModule(SysAclModuleVO sysAclModuleVO) {
        return SysAclModule.builder()
                .sourceModule(sysAclModuleVO.getSourceModule())
                .sourceId(Long.parseLong(sysAclModuleVO.getSourceId()))
                .status(sysAclModuleVO.getStatus())
                .build();
    }

    /**
     * 构建权限模块点
     *
     * @param modifySysAclModuleVO
     * @return
     */
    public SysAclModule buildSysAclModule(ModifySysAclModuleVO modifySysAclModuleVO) {
        return SysAclModule.builder()
                .aclModuleId(Long.valueOf(modifySysAclModuleVO.getAclModuleId()))
                .sourceModule(modifySysAclModuleVO.getSourceModule())
                .status(modifySysAclModuleVO.getStatus())
                .sourceId(Long.valueOf(modifySysAclModuleVO.getSourceId()))
                .build();
    }


    /**
     * 校验创建权限模块 非空校验
     *
     * @param sysAclModuleVO
     */
    public void checkNonEmptySysAclModule(SysAclModuleVO sysAclModuleVO) {
        AssertUtil.isEmpty(sysAclModuleVO.getSourceModule(), SysAclModuleExceptionEnums.NOT_EXIST_SOURCE_MODULE.getMessage());
        AssertUtil.isEmpty(sysAclModuleVO.getStatus(), SysAclModuleExceptionEnums.NOT_EXIST_STATUS.getMessage());
        AssertUtil.isEmpty(sysAclModuleVO.getSourceId(), SysAclModuleExceptionEnums.NOT_EXIST_SOURCE_ID.getMessage());
    }

    /**
     * 校验修改权限模块 非空校验
     *
     * @param modifySysAclModuleVO
     */
    public void checkModifyNonEmptySysAclModule(ModifySysAclModuleVO modifySysAclModuleVO) {
        this.checkNonEmptySysAclModule(modifySysAclModuleVO);
        AssertUtil.isEmpty(modifySysAclModuleVO.getAclModuleId(), SysAclModuleExceptionEnums.NOT_EXIST_ACL_MODULE_ID.getMessage());
    }

    /**
     * 删除权限模块点
     * 通过 aclModuleId
     *
     * @param aclModuleId
     */
    public boolean delSysAclModuleById(Long aclModuleId) {
        return this.remove(Wrappers.lambdaQuery(SysAclModule.class).eq(SysAclModule::getAclModuleId, aclModuleId));
    }

    /**
     * 删除权限模块
     * 通过 sourceId
     *
     * @param sourceId
     * @return
     */
    public boolean delSysAclModuleBySourceId(Long sourceId) {
        return this.remove(Wrappers.lambdaQuery(SysAclModule.class).eq(SysAclModule::getSourceId, sourceId));
    }

    /**
     * 查询list
     *
     * @param sourceId
     * @param sourceModule
     * @return
     */
    public List<SysAclModule> getListBySource(Long sourceId, String sourceModule) {
        List<SysAclModule> list = this.list(Wrappers.lambdaQuery(SysAclModule.class)
                .eq(SysAclModule::getSourceId, sourceId)
                .eq(SysAclModule::getSourceModule, sourceModule)
                .eq(SysAclModule::getStatus, 0));
        return list;
    }

    /**
     * 查询one
     *
     * @param sourceId
     * @param sourceModule
     * @return
     */
    public SysAclModule getOneBySource(Long sourceId, String sourceModule) {
        SysAclModule sysAclModule = this.getOne(Wrappers.lambdaQuery(SysAclModule.class)
                .eq(SysAclModule::getSourceId, sourceId)
                .eq(SysAclModule::getSourceModule, sourceModule)
                .eq(SysAclModule::getStatus, 0));
        return sysAclModule;
    }

    /**
     * 条件查询
     *
     * @param sysAclModule
     * @return
     */
    public List<SysAclModule> getListByCondition(SysAclModule sysAclModule) {
        QueryWrapper<SysAclModule> queryWrapper = Condition.getQueryWrapper(sysAclModule);
        if (StringUtil.isNotEmpty(sysAclModule.getSourceModule())) {
            queryWrapper.lambda().eq(SysAclModule::getSourceModule, sysAclModule.getSourceModule());
        }
        if (StringUtil.isNotNull(sysAclModule.getSourceId())) {
            queryWrapper.lambda().eq(SysAclModule::getSourceId, sysAclModule.getSourceId());
        }
        if (!StringUtil.isEmpty(sysAclModule.getStatus())) {
            queryWrapper.lambda().eq(SysAclModule::getStatus, sysAclModule.getStatus());
        }
        return this.list(queryWrapper);
    }

    /**
     * 修改权限模块状态
     *
     * @param aclModuleId
     * @param status
     * @return boolean
     */
    public boolean updateAclModuleStatus(Long aclModuleId, Integer status) {
        SysAclModule sysAclModule = this.getById(aclModuleId);
        AssertUtil.isEmpty(sysAclModule, SysAclModuleExceptionEnums.NOT_EXIST_ACL_MODULE.getMessage());
        sysAclModule.setStatus(status);
        return this.updateById(sysAclModule);
    }

    /**
     * 通过sourceIds查询SysAclModule
     *
     * @param sourceId
     * @return List<SysAclModule>
     */
    public List<SysAclModule> selectSysAclModuleBySourceId(Long sourceId) {
        return this.list(Wrappers.lambdaQuery(SysAclModule.class).in(SysAclModule::getSourceId, sourceId).eq(SysAclModule::getStatus, ComStatusEnum.YES.value()));
    }

    /**
     * 通过aclModuleId查询SysAclModule
     *
     * @param aclModuleId
     * @return SysAclModule
     */
    public SysAclModule selectSysAclModuleByaclModuleId(Long aclModuleId) {
        return this.getOne(Wrappers.lambdaQuery(SysAclModule.class).in(SysAclModule::getAclModuleId, aclModuleId).eq(SysAclModule::getStatus, ComStatusEnum.YES.value()));
    }
}
