package com.example.layer.sys.engine.domain.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.layer.sys.engine.components.redis.SysDeptKey;
import com.example.layer.sys.engine.domain.core.SysDept;
import com.example.layer.sys.engine.mapper.SysDeptMapper;
import com.example.layer.sys.engine.service.SysUserDeptService;
import com.example.layer.sys.infrastructure.exception.SysDeptExceptionConstant;
import com.example.layer.sys.vo.ModifySysDeptVO;
import com.example.layer.sys.vo.SysDeptVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springlayer.core.boot.context.CurrentUser;
import org.springlayer.core.redis.helper.RedisOperator;
import org.springlayer.core.tool.utils.AssertUtil;
import org.springlayer.core.tool.utils.StringUtil;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author Hzhi
 * @Date 2022-04-19 11:59
 * @description 系统组织细粒度逻辑层
 **/
@Slf4j
@Service
public class SysDeptDomainService extends ServiceImpl<SysDeptMapper, SysDept> {

    @Resource
    private RedisOperator redisOperator;
    @Resource
    private SysUserDeptService sysUserDeptService;
    @Resource
    private SysDeptMapper sysDeptMapper;

    /**
     * 查询系统组织缓存集合
     *
     * @return List<SysDept>
     */
    public List<SysDept> selectSysDeptCacheList() {
        String deptKey = redisOperator.get(SysDeptKey.SYS_DEPT_MAP);
        List<SysDept> sysDeptList = null;
        if (StringUtil.isEmpty(deptKey)) {
            sysDeptList = this.list(Wrappers.lambdaQuery(SysDept.class).eq(SysDept::getIsDeleted, false).orderByAsc(SysDept::getOrderNum));
            redisOperator.set(SysDeptKey.SYS_DEPT_MAP, JSON.toJSONString(sysDeptList));
            return sysDeptList;
        }
        return JSON.parseArray(deptKey, SysDept.class);
    }

    /**
     * 查询系统部门通过ID
     *
     * @param deptId 组织ID
     * @return SysDept
     */
    public SysDept selectSysDeptByDeptId(Long deptId) {
        return this.getOne(Wrappers.lambdaQuery(SysDept.class).eq(SysDept::getDeptId, deptId).eq(SysDept::getIsDeleted, false));
    }

    /**
     * 校验系统组织参数
     *
     * @param sysDeptVO
     */
    public void checkSysDeptParam(SysDeptVO sysDeptVO) {
        AssertUtil.isEmpty(sysDeptVO.getDeptName(), SysDeptExceptionConstant.NOT_DEPT_NAME);
        AssertUtil.isEmpty(sysDeptVO.getParentId(), SysDeptExceptionConstant.NOT_PARENT);
        AssertUtil.isEmpty(sysDeptVO.getOrderNum(), SysDeptExceptionConstant.NOT_ORDER_NUM);
        AssertUtil.isEmpty(sysDeptVO.getStatus(), SysDeptExceptionConstant.NOT_STATUS);
    }

    /**
     * 构建系统组织
     *
     * @param sysDeptVO
     * @param currentUser
     * @param parentSysDept
     * @return SysDept
     */
    public SysDept buildSysDept(SysDeptVO sysDeptVO, CurrentUser currentUser, SysDept parentSysDept) {
        return SysDept.builder().deptName(sysDeptVO.getDeptName()).parentId(parentSysDept.getDeptId()).ancestors(parentSysDept.getAncestors() + "," + sysDeptVO.getParentId())
                .createBy(currentUser.getUserId()).description(sysDeptVO.getDescription()).createTime(LocalDateTime.now())
                .isDeleted(false).orderNum(sysDeptVO.getOrderNum()).status(sysDeptVO.getStatus()).build();
    }

    /**
     * 校验: 校验修改系统组织参数
     *
     * @param modifySysDeptVO
     */
    public void checkModifySysDeptParam(ModifySysDeptVO modifySysDeptVO) {
        AssertUtil.isEmpty(modifySysDeptVO.getDeptId(), SysDeptExceptionConstant.NOT_DEPT_ID);
        this.checkSysDeptParam(modifySysDeptVO);
    }

    /**
     * 修改子元素关系
     *
     * @param id            被修改的部门ID
     * @param newParentDept 新的父部门
     * @param sysDept       当前部门
     */
    public void updateDeptChildren(Long id, SysDept newParentDept, SysDept sysDept) {
        String newAncestors = newParentDept.getAncestors() + "," + newParentDept.getDeptId();
        sysDept.setAncestors(newAncestors);
         List<SysDept> children = sysDeptMapper.selectChildrenDeptById(id);
        for (SysDept child : children) {
            child.setAncestors(child.getAncestors().replace(sysDept.getAncestors(), newAncestors));
        }
        if (children.size() > 0) {
            this.updateBatchById(children);
        }
    }

    /**
     * 构建修改系统组织
     *
     * @param sysDept
     * @param modifySysDeptVO
     * @param currentUser
     */
    public void buildModifySysDept(SysDept sysDept, ModifySysDeptVO modifySysDeptVO, CurrentUser currentUser) {
        sysDept.setDeptName(modifySysDeptVO.getDeptName());
        sysDept.setUpdateBy(currentUser.getUserId());
        sysDept.setUpdateTime(LocalDateTime.now());
        sysDept.setParentId(Long.valueOf(modifySysDeptVO.getParentId()));
        sysDept.setStatus(modifySysDeptVO.getStatus());
        sysDept.setOrderNum(modifySysDeptVO.getOrderNum());
        sysDept.setDescription(modifySysDeptVO.getDescription());
    }

    /**
     * 保存并且清除缓存
     *
     * @param sysDept
     * @return boolean
     */
    public boolean saveDelCache(SysDept sysDept) {
        // 缓存: 清除缓存
        redisOperator.del(SysDeptKey.SYS_DEPT_MAP);
        return this.save(sysDept);
    }

    /**
     * 修改清除缓存
     *
     * @param sysDept
     * @return boolean
     */
    public boolean updateDelCacheById(SysDept sysDept) {
        // 缓存: 清除缓存
        redisOperator.del(SysDeptKey.SYS_DEPT_MAP);
        return this.updateById(sysDept);
    }

    /**
     * 校验部门下是否存在用户
     *
     * @param deptId
     */
    public void checkExitsDeptUser(Long deptId) {
        AssertUtil.isBolEmpty(sysUserDeptService.existUserByDept(deptId), SysDeptExceptionConstant.EXIST_DEPT_USER);

    }

    /**
     * 查询部门的全部子部门
     * @param deptId
     * @return
     */
    public List<SysDept> selectChildrenDeptList(Long deptId) {
        return sysDeptMapper.selectAllDeptByDeptId(deptId);
    }

    /**
     * 查询本部门和自己的部门
     * @param deptId
     * @return
     */
    public List<SysDept> selectAllDeptList(Long deptId){
        List<SysDept> list = sysDeptMapper.selectAllDeptByDeptId(deptId);
         return list;
    }
}
