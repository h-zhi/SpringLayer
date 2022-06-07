package com.example.layer.sys.engine.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.layer.sys.engine.domain.core.SysDept;

import java.util.List;

/**
 * @Author Hzhi
 * @Date 2022-04-19 12:00
 * @description
 **/
public interface SysDeptMapper extends BaseMapper<SysDept> {
    /**
     * 查询子部门通过ID
     * @param id
     * @return
     */
    List<SysDept> selectChildrenDeptById(Long id);

    /**
     * 查询部门及其子部门
     * @param deptId
     * @return
     */
    List<SysDept> selectAllDeptByDeptId(Long deptId);
}
