package com.example.layer.sys.engine.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.layer.sys.engine.domain.core.SysDept;
import com.example.layer.sys.engine.domain.core.SysUserDept;
import com.example.layer.sys.engine.domain.service.SysUserDeptDomainService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author zhaoyl
 * @Date 2022-04-26
 * @description 用户和角色信息
 **/
@Service
public class SysUserDeptService {

    @Resource
    private SysUserDeptDomainService sysUserDeptDomainService;
    @Resource
    private SysDeptService sysDeptService;

    /**
     * 保存系统用户和部门关系
     *
     * @param deptIds
     * @param userId
     * @return
     */
    public boolean savaSysUserDept(List<String> deptIds, Long userId) {
        List<SysUserDept> sysUserDepts = new ArrayList<>();
        deptIds.stream().forEach(deptId -> {
            if (StringUtils.isNotBlank(deptId)) {
                sysUserDepts.add(new SysUserDept(Long.valueOf(deptId), userId));
            }
        });
        return sysUserDeptDomainService.saveBatch(sysUserDepts);
    }

    /**
     * 修改用户和部门关系
     *
     * @param deptIds
     * @param userId
     * @return
     */
    public boolean modifySysUserDept(List<String> deptIds, Long userId) {
        // 删除用户角色关系
        sysUserDeptDomainService.delSysUserDeptByUserId(userId);
        // 批量保存系统用户角色
        return this.savaSysUserDept(deptIds, userId);
    }

    /**
     * 根据部门deptId判断是否存在用户
     *
     * @param deptId
     * @return 存在 true
     */
    public boolean existUserByDept(Long deptId) {
        List<SysUserDept> sysUserDepts = sysUserDeptDomainService.queryUserDeptByDeptId(deptId);
        if (sysUserDepts.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 通过用户ID查询部门
     *
     * @param userId
     * @return
     */
    public List<Long> selectDeptIdsByUserId(Long userId) {
        List<SysUserDept> list = sysUserDeptDomainService.list(Wrappers.lambdaQuery(SysUserDept.class).eq(SysUserDept::getUserId, userId));
        return list.stream().map(SysUserDept::getDeptId).collect(Collectors.toList());
    }

    /**
     * 根据部门ID查询用户
     * @param deptId
     * @return
     */
    public List<Long> selectUserIdsByDeptId(Long deptId) {
        List<SysUserDept> list = sysUserDeptDomainService.list(Wrappers.lambdaQuery(SysUserDept.class).eq(SysUserDept::getDeptId, deptId));
        return list.stream().map(SysUserDept::getUserId).collect(Collectors.toList());
    }

    /**
     * 查询用户所在部门的全部人员id
     * @param userId
     * @return
     */
    public Set<Long> selectDeptUserIdsByUser(Long userId){
        Set<Long> set = new HashSet<>();
        List<SysUserDept> sysUserDeptList = sysUserDeptDomainService.queryUserDeptByUserId(userId);
        sysUserDeptList.forEach(sysUserDept ->{
            List<Long> listUserId = selectUserIdsByDeptId(sysUserDept.getDeptId());
            set.addAll(listUserId);
        });
        return set;
    }

    /**
     * 查询用户所在部门及其子部门的全部人员id
     * @param userId
     * @return
     */
    public Set<Long> selectAllDeptUserIdsByUser(Long userId){
        Set<Long> set = new HashSet<>();
        List<SysDept> sysDeptList = sysDeptService.selectAllUserIdsByUser(userId);
        sysDeptList.forEach(sysUserDept ->{
            List<Long> listUserId = selectUserIdsByDeptId(sysUserDept.getDeptId());
            set.addAll(listUserId);
        });
        return set;
    }
}
