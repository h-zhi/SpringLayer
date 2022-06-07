package com.example.layer.sys.application;

import com.example.layer.sys.dto.SysDeptDTO;
import com.example.layer.sys.engine.components.redis.SysDeptKey;
import com.example.layer.sys.engine.domain.convertot.SysDeptConvertor;
import com.example.layer.sys.engine.domain.core.SysDept;
import com.example.layer.sys.engine.domain.service.SysDeptDomainService;
import com.example.layer.sys.engine.domain.service.SysUserDeptDomainService;
import com.example.layer.sys.engine.service.SysDeptService;
import com.example.layer.sys.infrastructure.exception.SysDeptExceptionConstant;
import com.example.layer.sys.vo.ModifySysDeptVO;
import com.example.layer.sys.vo.SysDeptVO;
import org.springframework.stereotype.Service;
import org.springlayer.core.boot.context.CurrentUser;
import org.springlayer.core.redis.helper.RedisOperator;
import org.springlayer.core.tool.utils.AssertUtil;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Hzhi
 * @Date 2022-04-21 9:25
 * @description 系统组织应用逻辑层
 **/
@Service
public class SysDeptApplicationService {

    @Resource
    private SysDeptDomainService sysDeptDomainService;
    @Resource
    private SysDeptService sysDeptService;
    @Resource
    private RedisOperator redisOperator;
    @Resource
    private SysUserDeptDomainService sysUserDeptDomainService;

    /**
     * 组织树信息
     *
     * @return List<SysDept>
     */
    public List<SysDept> deptTreeData() {
        // 查询: 查询组织集合
        List<SysDept> sysDeptList = sysDeptDomainService.selectSysDeptCacheList();
        // 组装: 组装系统部门树
        return sysDeptService.assembleChildPerms(sysDeptList, 0);
    }

    /**
     * 查询系统组织详情
     *
     * @param deptId 组织ID
     * @return SysDeptDTO
     */
    public SysDeptDTO querySysDeptDetail(Long deptId) {
        // 查询: 查询系统组织
        SysDept sysDept = sysDeptService.querySysDeptByDeptId(deptId);
        if (null == sysDept) {
            return null;
        }
        // 转换: 转换SysDeptDTO
        return SysDeptConvertor.convertToSysDeptDTO(sysDept);
    }

    /**
     * 移除系统部门
     *
     * @param deptId
     * @param currentUser
     * @return boolean
     */
    public boolean removeSysDept(Long deptId, CurrentUser currentUser) {
        SysDept sysDept = sysDeptService.querySysDeptByDeptId(deptId);
        if (null == sysDept) {
            return true;
        }
        //校验: 是否存在用户
        sysDeptDomainService.checkExitsDeptUser(deptId);
        // 缓存: 清除缓存
        redisOperator.del(SysDeptKey.SYS_DEPT_MAP);
        //删除: 删除部门用户关联
        sysUserDeptDomainService.delSysUserDeptByUdeptId(deptId);
        // 移除: 移除组织
        return sysDeptService.removeSysDept(sysDept, currentUser.getUserId());
    }

    /**
     * 创建系统组织
     *
     * @param sysDeptVO   组织参数
     * @param currentUser 当前用户
     * @return boolean
     */
    public boolean createSysDept(SysDeptVO sysDeptVO, CurrentUser currentUser) {
        // 校验: 校验创建系统组织参数
        sysDeptDomainService.checkSysDeptParam(sysDeptVO);
        // 查询: 查询父组织
        SysDept parentSysDept = sysDeptService.querySysDeptByDeptId(Long.valueOf(sysDeptVO.getParentId()));
        AssertUtil.isEmpty(parentSysDept, SysDeptExceptionConstant.NOT_PARENT_EXIST);
        // 保存: 保存组织
        return sysDeptService.createSysDept(sysDeptVO, currentUser, parentSysDept);
    }

    /**
     * 修改系统组织
     *
     * @param modifySysDeptVO
     * @param currentUser
     * @return boolean
     */
    public boolean modifySysDept(ModifySysDeptVO modifySysDeptVO, CurrentUser currentUser) {
        // 校验: 校验修改系统组织参数
        sysDeptDomainService.checkModifySysDeptParam(modifySysDeptVO);
        // 查询: 查询组织
        SysDept sysDept = sysDeptService.querySysDeptByDeptId(Long.valueOf(modifySysDeptVO.getDeptId()));
        // 更新子组织
        sysDeptService.updateDeptChildren(sysDept);
        // 更新: 更新组织
        return sysDeptService.modifySysDept(sysDept, modifySysDeptVO, currentUser);
    }

    /**
     * 查询系统组织
     *
     * @return List<SysDeptDTO>
     */
    public List<SysDeptDTO> querySysDeptList() {
        // 查询: 查询组织集合
        List<SysDept> sysDeptList = sysDeptDomainService.selectSysDeptCacheList();
        // 转换: 转换DTO
        return SysDeptConvertor.convertToSysDeptDTOs(sysDeptList);
    }
}
