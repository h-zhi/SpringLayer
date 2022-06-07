package com.example.layer.sys.engine.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.layer.sys.engine.domain.beans.SysUserDeptBean;
import com.example.layer.sys.engine.domain.core.SysUser;
import com.example.layer.sys.engine.domain.service.SysUserDomainService;
import com.example.layer.sys.infrastructure.exception.SysUserExceptionConstant;
import org.springlayer.core.redis.helper.RedisOperator;
import org.springlayer.core.tool.utils.AssertUtil;
import org.springlayer.core.tool.utils.StringUtil;
import com.example.layer.sys.dto.LoginUserDTO;
import com.example.layer.sys.dto.SysUserDTO;
import com.example.layer.sys.engine.components.redis.SysUserKey;
import com.example.layer.sys.engine.domain.convertot.SysUserConvertor;
import com.example.layer.sys.engine.mapper.SysUserMapper;
import com.example.layer.sys.vo.CreateSysUserVO;
import com.example.layer.sys.vo.ModifySysUserVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Hzhi
 * @Date 2022-04-19 9:33
 * @description 系统用户逻辑层
 **/
@Service
public class SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysUserDomainService sysUserDomainService;
    @Resource
    private RedisOperator redisOperator;


    /**
     * 查询系统用户分页
     *
     * @param current   当前页
     * @param size      每页条数
     * @param username  用户名
     * @param loginName 登录名
     * @param deptId    部门ID
     * @param phone     电话号
     * @return IPage<SysUserDTO>
     */
    public IPage<SysUserDTO> querySysUserPage(Integer current, Integer size, String username, String loginName, String phone, Long deptId) {
        Page<SysUser> page = new Page<SysUser>(current, size);
        SysUserDeptBean sysUserDeptBean = new SysUserDeptBean(username, loginName, phone, deptId);
        List<SysUser> sysUserList = sysUserMapper.iPageSelect(page, sysUserDeptBean);
        if (StringUtil.isEmpty(sysUserList)) {
            return new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        }
        return this.convertToSysUserDTOPage(page, sysUserList);
    }

    /**
     * 转换分页系统用户DTO
     *
     * @param page
     * @param sysUserList
     * @return IPage<SysUserDTO>
     */
    private IPage<SysUserDTO> convertToSysUserDTOPage(Page<SysUser> page, List<SysUser> sysUserList) {
        List<SysUserDTO> tenantUserDTOS = SysUserConvertor.convertToSysUserDTOList(sysUserList);
        IPage<SysUserDTO> pageVo = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        pageVo.setRecords(tenantUserDTOS);
        return pageVo;
    }

    /**
     * 查询系统用户通过用户ID
     *
     * @param userId
     * @return SysUserDTO
     */
    public SysUserDTO querySysUserCacheByUserId(Long userId) {
        // 查询: 查询缓存系统用户
        SysUser sysUser = sysUserDomainService.selectSysUserCacheByUserId(userId);
        if (null == sysUser) {
            return null;
        }
        // 转换: 转换系统用户DTO
        return SysUserConvertor.convertToSysUserDTO(sysUser);
    }

    /**
     * 保存系统用户
     *
     * @param createSysUserVO
     */
    public SysUser savaSysUser(CreateSysUserVO createSysUserVO) {
        SysUser sysUser = sysUserDomainService.buildSysUser(createSysUserVO);
        sysUserDomainService.save(sysUser);
        return sysUser;
    }

    /**
     * 查询系统用户通过用户ID
     *
     * @param userId 用户ID
     * @return SysUser
     */
    public SysUser querySysUserByUserId(Long userId) {
        return sysUserDomainService.selectSysUserByUserId(userId);
    }

    /**
     * 校验相同手机号和登录名
     *
     * @param sysUser
     * @param modifySysUserVO
     */
    public void checkSamePhoneAndLoginName(SysUser sysUser, ModifySysUserVO modifySysUserVO) {
        // 校验: 校验手机是否存在
        SysUser existUserByPhone = sysUserDomainService.isExistUserByPhone(modifySysUserVO.getPhone());
        AssertUtil.isBolEmpty(!existUserByPhone.getUserId().equals(sysUser.getUserId()), SysUserExceptionConstant.SAME_PHONE);
        // 校验: 校验登录名是否存在
        SysUser existUserByLoginName = sysUserDomainService.isExistUserByLoginName(modifySysUserVO.getLoginName());
        AssertUtil.isBolEmpty(!existUserByLoginName.getUserId().equals(sysUser.getUserId()), SysUserExceptionConstant.SAME_LOGIN_NAME);
    }

    /**
     * 修改系统用户
     *
     * @param sysUser         系统用户
     * @param modifySysUserVO 修改系统用户数据
     */
    public void modifySysUser(SysUser sysUser, ModifySysUserVO modifySysUserVO) {
        SysUser build = sysUserDomainService.buildSysUser(modifySysUserVO);
        build.setUserId(sysUser.getUserId());
        redisOperator.set(SysUserKey.SYS_USER_INFO_MAP + sysUser.getUserId(), JSON.toJSONString(build), 36000);
        sysUserDomainService.updateById(build);
    }

    /**
     * 删除系统用户
     *
     * @param sysUser
     * @return boolean
     */
    public boolean delSysUser(SysUser sysUser) {
        sysUser.setIsDeleted(true);
        redisOperator.del(SysUserKey.SYS_USER_INFO_MAP + sysUser.getUserId());
        return sysUserDomainService.updateById(sysUser);
    }

    /**
     * 用户登录
     *
     * @param param
     * @return LoginUserDTO
     */
    public LoginUserDTO login(String param) {
        SysUser sysUser = sysUserDomainService.getOne(Wrappers.lambdaQuery(SysUser.class).eq(SysUser::getLoginName, param).eq(SysUser::getIsDeleted, false));
        if (null == sysUser) {
            return null;
        }
        return new LoginUserDTO(sysUser.getUserId(), sysUser.getUsername(), sysUser.getLoginName(), sysUser.getPassword(), sysUser.getStatus());
    }
}
