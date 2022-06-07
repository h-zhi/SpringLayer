package com.example.layer.sys.engine.service;

import com.example.layer.sys.engine.domain.core.SysAclModule;
import com.example.layer.sys.engine.domain.enums.ComAuthAclModuleEnum;
import com.example.layer.sys.engine.domain.service.SysAclModuleDomainService;
import com.example.layer.sys.infrastructure.exception.SysAclModuleExceptionEnums;
import com.example.layer.sys.vo.ModifySysAclModuleVO;
import com.example.layer.sys.vo.SysAclModuleVO;
import org.springframework.stereotype.Service;
import org.springlayer.core.tool.utils.AssertUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @Author zhaoyl
 * @Date 2022-04-26
 * @description 权限模块点
 **/
@Service
public class SysAclModuleService {
    @Resource
    private SysAclModuleDomainService sysAclModuleDomainService;
    @Resource
    private SysUserRoleService sysUserRoleService;
    @Resource
    private SysUserDeptService sysUserDeptService;

    /**
     * 保存权限模块
     *
     * @param sysAclModuleVO
     * @return
     */
    public boolean savaSysAclModule(SysAclModuleVO sysAclModuleVO) {
        SysAclModule sysAclModule = sysAclModuleDomainService.buildSysAclModule(sysAclModuleVO);
        return sysAclModuleDomainService.save(sysAclModule);
    }

    /**
     * 修改权限模块
     *
     * @param modifySysAclModuleVO
     * @return
     */
    public boolean modifySysAclModule(ModifySysAclModuleVO modifySysAclModuleVO) {
        SysAclModule sysAclModule = sysAclModuleDomainService.buildSysAclModule(modifySysAclModuleVO);
        return sysAclModuleDomainService.updateById(sysAclModule);
    }

    /**
     * 查询是否存在
     *
     * @param sourceId
     * @param sourceModule
     * @return 存在返回 true
     */
    public boolean cheakOnlySysAclModule(Long sourceId, String sourceModule) {
        List<SysAclModule> listBySource = sysAclModuleDomainService.getListBySource(sourceId, sourceModule);
        if (listBySource.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 查询
     *
     * @param sourceId
     * @param sourceModule
     * @return
     */
    public List<SysAclModule> getSysAclModuleBySource(Long sourceId, String sourceModule) {
        return sysAclModuleDomainService.getListBySource(sourceId, sourceModule);
    }

    /**
     * 查询一条
     *
     * @param sourceId
     * @param sourceModule
     * @return
     */
    public SysAclModule getOneSysAclModuleBySource(Long sourceId, String sourceModule) {
        return sysAclModuleDomainService.getOneBySource(sourceId, sourceModule);
    }

    /**
     * 校验是否存在
     *
     * @param sourceId
     * @param sourceModule
     */
    public void checkExistSysAclModule(Long sourceId, String sourceModule) {
        AssertUtil.isBolEmpty(this.cheakOnlySysAclModule(sourceId, sourceModule), SysAclModuleExceptionEnums.EXIST_SOURCE_MODULE.getMessage());
    }

    /**
     * 编辑校验唯一
     *
     * @param modifySysAclModuleVO
     */
    public void checkModifyExistSysAclModule(ModifySysAclModuleVO modifySysAclModuleVO) {
        List<SysAclModule> sysAclModuleBySource = this.getSysAclModuleBySource(Long.valueOf(modifySysAclModuleVO.getSourceId()), modifySysAclModuleVO.getSourceModule());
        if (sysAclModuleBySource.size() > 0) {
            AssertUtil.isBolEmpty(!modifySysAclModuleVO.getAclModuleId().equals(sysAclModuleBySource.get(0).getAclModuleId()), SysAclModuleExceptionEnums.EXIST_SOURCE_MODULE.getMessage());
        }
    }

    /**
     * 获取用户全部模块权限
     *
     * @param userId
     */
    public List<SysAclModule> queryUserAllAclModule(Long userId) {
        List<Long> roleSourceIds = sysUserRoleService.selectRoleIdsByUserId(userId);
        List<SysAclModule> roleAcl = this.getUserSysAclModule(roleSourceIds, ComAuthAclModuleEnum.ROLE.getName());
        List<Long> deptSourceIds = sysUserDeptService.selectDeptIdsByUserId(userId);
        List<SysAclModule> deptAcl = this.getUserSysAclModule(deptSourceIds,ComAuthAclModuleEnum.DEPT.getName());
        List<SysAclModule> userAcl = this.getSysAclModuleBySource(userId, ComAuthAclModuleEnum.USER.getName());
        //处理数据
        List<SysAclModule> userAclAll = new ArrayList();
        userAclAll.addAll(roleAcl);
        userAclAll.addAll(deptAcl);
        userAclAll.addAll(userAcl);
        //去重
        List<SysAclModule> uniqueByIdList = userAclAll.stream().collect(
                Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(SysAclModule::getAclModuleId))), ArrayList::new)
        );
        return uniqueByIdList;
    }


    /**
     * 通过资源id获取权限模块
     *
     * @param sourceIds
     * @param sourceModule
     * @return
     */
    public List<SysAclModule> getUserSysAclModule(List<Long> sourceIds, String sourceModule) {
        List<List<SysAclModule>> sysAclModules = new ArrayList<>();
        List<SysAclModule> aclModules = new ArrayList<>();
        sourceIds.stream().forEach(sourceId -> {
            List<SysAclModule> sysAclModuleList = this.getSysAclModuleBySource(sourceId, sourceModule);
            sysAclModules.add(sysAclModuleList);
        });
        for (List<SysAclModule> sysAclModuleList : sysAclModules) {
            sysAclModuleList.stream().forEach(aclModule -> {
                aclModules.add(aclModule);
            });
        }
        return aclModules;
    }

    /**
     * 获取模块AclModuleId
     *
     * @param sourceIds
     * @return
     */
    public List<Long> querySysAclModuleIdsBySourceIdList(List<Long> sourceIds) {
        List<Long> aclModules = new ArrayList<>();
        sourceIds.stream().forEach(sourceId -> {
            List<SysAclModule> sysAclModules = sysAclModuleDomainService.selectSysAclModuleBySourceId(sourceId);
            List<Long> collect = sysAclModules.stream().map(SysAclModule::getAclModuleId).collect(Collectors.toList());
            aclModules.addAll(collect);
        });
        return aclModules;
    }

    /**
     * 查询启用的
     *
     * @param aclModuleId
     * @return
     */
    public SysAclModule getSysAclModuleByIdAndStatus(Long aclModuleId) {
        return sysAclModuleDomainService.selectSysAclModuleByaclModuleId(aclModuleId);
    }

    /**
     * 查询用户权限
     * @param userId
     * @return
     */
    public   List<Long> getAclModules(Long userId){
        List<Long> sourceIds = new ArrayList<>();
        if (userId==null){
            return sourceIds;
        }
        sourceIds.add(userId);
        List<Long> listRole = sysUserRoleService.selectRoleIdsByUserId(userId);
        if (null!=listRole&&!listRole.isEmpty()){
            sourceIds.addAll(listRole);
        }
        List<Long> listDept = sysUserDeptService.selectDeptIdsByUserId(userId);
        if (null!=listDept&&!listDept.isEmpty()){
            sourceIds.addAll(listDept);
        }
        List<Long> aclModules = querySysAclModuleIdsBySourceIdList(sourceIds);
        return aclModules;
    }
}
