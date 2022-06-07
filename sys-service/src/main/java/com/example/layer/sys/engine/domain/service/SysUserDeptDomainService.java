package com.example.layer.sys.engine.domain.service;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.layer.sys.engine.domain.core.SysUserDept;
import com.example.layer.sys.engine.mapper.SysUserDeptMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author zhaoyl
 * @Date 2022-04-26
 * @description
 **/
@Slf4j
@Service
public class SysUserDeptDomainService extends ServiceImpl<SysUserDeptMapper, SysUserDept> {

    /**
     * 删除用户部门信息
     * 通过用户userId
     * @param userId
     */
    public void delSysUserDeptByUserId(Long userId) {
        this.remove(Wrappers.lambdaQuery(SysUserDept.class).eq(SysUserDept::getUserId, userId));
    }

    /**
     * 删除用户部门信息
     * 通过部门deptId
     * @param deptId
     */
    public void delSysUserDeptByUdeptId(Long deptId) {
        this.remove(Wrappers.lambdaQuery(SysUserDept.class).eq(SysUserDept::getDeptId, deptId));
    }

    /**
     * 根据部门deptId查询部门用户
     * @param deptId
     * @return List<SysUserDept>
     */
    public List<SysUserDept> queryUserDeptByDeptId(Long deptId) {
        return this.list(Wrappers.lambdaQuery(SysUserDept.class).eq(SysUserDept::getDeptId, deptId));
    }

    /**
     * 根据用户id查询用户部门
     * @param userId
     * @return List<SysUserDept>
     */
    public List<SysUserDept> queryUserDeptByUserId(Long userId){
        return this.list(Wrappers.lambdaQuery(SysUserDept.class).eq(SysUserDept::getUserId, userId));
    }

}
