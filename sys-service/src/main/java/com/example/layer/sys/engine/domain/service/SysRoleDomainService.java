package com.example.layer.sys.engine.domain.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springlayer.core.boot.context.CurrentUser;
import org.springlayer.core.redis.helper.RedisOperator;
import org.springlayer.core.tool.utils.AssertUtil;
import org.springlayer.core.tool.utils.StringUtil;
import com.example.layer.sys.engine.components.redis.SysRoleKey;
import com.example.layer.sys.engine.domain.core.SysRole;
import com.example.layer.sys.engine.mapper.SysRoleMapper;
import com.example.layer.sys.engine.service.SysUserRoleService;
import com.example.layer.sys.infrastructure.exception.SysRoleExceptionConstant;
import com.example.layer.sys.vo.ModifySysRoleVO;
import com.example.layer.sys.vo.SysRoleDeptVO;
import com.example.layer.sys.vo.SysRoleVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @Author zhaoyl
 * @Date 2022-04-19
 * @description 系统角色逻辑层
 **/
@Slf4j
@Service
public class SysRoleDomainService extends ServiceImpl<SysRoleMapper, SysRole> {

    @Resource
    private RedisOperator redisOperator;
    @Resource
    private SysUserRoleService sysUserRoleService;

    /**
     * 构建角色信息
     * @param sysRoleVO
     * @return SysRole
     */
    public SysRole buildSysRole(SysRoleVO sysRoleVO, CurrentUser currentUser) {
        return SysRole.builder()
                .roleName(sysRoleVO.getRoleName())
                .roleSort(sysRoleVO.getRoleSort())
                .status(sysRoleVO.getStatus())
                .tag(sysRoleVO.getTag())
                .category(sysRoleVO.getCategory())
                .dataScope(StringUtils.isBlank(sysRoleVO.getDataScope())?"1":sysRoleVO.getDataScope())
                .isDeleted(false)
                .createBy(currentUser.getUserId())
                .createTime(LocalDateTime.now())
                .build();
    }

    /**
     * 角色修改实体转换
     * @param modifySysRoleVO
     * @param currentUser
     * @return SysRole
     */
    public SysRole buildModifySysRole(ModifySysRoleVO modifySysRoleVO, CurrentUser currentUser) {
        return SysRole.builder().roleName(modifySysRoleVO.getRoleName())
                .roleSort(modifySysRoleVO.getRoleSort())
                .status(modifySysRoleVO.getStatus())
                .tag(modifySysRoleVO.getTag())
                .roleId(Long.valueOf(modifySysRoleVO.getRoleId()))
                .category(modifySysRoleVO.getCategory())
                .dataScope(StringUtils.isBlank(modifySysRoleVO.getDataScope())?"1":modifySysRoleVO.getDataScope())
                .updateBy(currentUser.getUserId())
                .updateTime(LocalDateTime.now())
                .isDeleted(false)
                .build();
    }

    /**
     * 查询角色信息通过roleId
     * @param roleId
     * @return SysRole
     */
    public SysRole selectSysRoleById(Long roleId) {
        // 查询: 查询缓存
        String sysRoleInfo = redisOperator.get(SysRoleKey.SYS_ROLE_INFO_MAP + roleId);
        // 校验: 校验缓存不为空直接返回
        if (StringUtil.isNotEmpty(sysRoleInfo)) {
            return JSON.parseObject(sysRoleInfo, SysRole.class);
        }
        SysRole sysRole = this.getById(roleId);
        redisOperator.set(SysRoleKey.SYS_ROLE_INFO_MAP + roleId, JSON.toJSONString(sysRole), 36000);
        return sysRole;
    }

    /**
     * 校验创建角色信息参数
     * @param sysRoleVO
     */
    public void checkCreateSysRoleParam(SysRoleVO sysRoleVO) {
        AssertUtil.isEmpty(sysRoleVO.getRoleName(), SysRoleExceptionConstant.NOT_ROLE_NAME);
        AssertUtil.isEmpty(sysRoleVO.getRoleSort(), SysRoleExceptionConstant.NOT_ROLE_SORT);
        AssertUtil.isEmpty(sysRoleVO.getStatus(), SysRoleExceptionConstant.NOT_ROLE_STATUS);
    }

    /**
     * 校验修改角色信息参数
     * @param modifySysRoleVO
     */
    public void checkModifySysRoleParam(ModifySysRoleVO modifySysRoleVO) {
        AssertUtil.isEmpty(modifySysRoleVO.getRoleId(), SysRoleExceptionConstant.NOT_ROLE_ID);
        AssertUtil.isEmpty(modifySysRoleVO.getRoleName(), SysRoleExceptionConstant.NOT_ROLE_NAME);
        AssertUtil.isEmpty(modifySysRoleVO.getRoleSort(), SysRoleExceptionConstant.NOT_ROLE_SORT);
        AssertUtil.isEmpty(modifySysRoleVO.getStatus(), SysRoleExceptionConstant.NOT_ROLE_STATUS);
    }

    /**
     * 校验角色下是否存在用户
     * @param roleId
     */
    public void checkExitsRoleUser(Long roleId) {
        AssertUtil.isBolEmpty(sysUserRoleService.existUserByRole(roleId), SysRoleExceptionConstant.EXIST_ROLE_USER);
    }

    /**
     * 校验角色部门数据
     * @param sysRoleDeptVO
     */
    public void checkRoleDeptParam(SysRoleDeptVO sysRoleDeptVO){
        AssertUtil.isEmpty(sysRoleDeptVO.getRoleId(), SysRoleExceptionConstant.NOT_ROLE_ID);
        AssertUtil.isEmpty(sysRoleDeptVO.getRoleId(), SysRoleExceptionConstant.EXIST_ROLE_DEPT);
    }
}
