package com.example.layer.sys.engine.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.layer.sys.engine.domain.beans.SysUserDeptBean;
import com.example.layer.sys.engine.domain.core.SysUser;

import java.util.List;

/**
 * @Author Hzhi
 * @Date 2022-04-19 9:31
 * @description
 **/
public interface SysUserMapper extends BaseMapper<SysUser> {
    List<SysUser> iPageSelect(Page<SysUser> page, SysUserDeptBean sysUserDeptBean);
}