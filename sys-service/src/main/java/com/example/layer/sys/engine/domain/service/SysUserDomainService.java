package com.example.layer.sys.engine.domain.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springlayer.core.redis.helper.RedisOperator;
import org.springlayer.core.tool.utils.AssertUtil;
import org.springlayer.core.tool.utils.StringUtil;
import com.example.layer.sys.engine.components.redis.SysUserKey;
import com.example.layer.sys.engine.domain.core.SysUser;
import com.example.layer.sys.engine.mapper.SysUserMapper;
import com.example.layer.sys.infrastructure.exception.SysPostExceptionConstant;
import com.example.layer.sys.infrastructure.exception.SysUserExceptionConstant;
import com.example.layer.sys.vo.CreateSysUserVO;
import com.example.layer.sys.vo.ModifySysUserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @Author Hzhi
 * @Date 2022-04-19 9:31
 * @description
 **/
@Slf4j
@Service
public class SysUserDomainService extends ServiceImpl<SysUserMapper, SysUser> {

    @Resource
    private RedisOperator redisOperator;

    /**
     * 是否存在用户
     *
     * @param userId
     * @return SysUser
     */
    public SysUser isExistSysUser(Long userId) {
        SysUser sysUser = this.getOne(Wrappers.lambdaQuery(SysUser.class).eq(SysUser::getUserId, userId).eq(SysUser::getIsDeleted, false));
        AssertUtil.isNotEmpty(sysUser, SysUserExceptionConstant.NOT_EXIST);
        return sysUser;
    }

    /**
     * 校验创建系统用户参数
     *
     * @param createSysUserVO
     */
    public void checkCreateSysUserParam(CreateSysUserVO createSysUserVO) {
        AssertUtil.isEmpty(createSysUserVO.getPostIds(), SysPostExceptionConstant.NOT_POST);
        AssertUtil.isEmpty(createSysUserVO.getDeptIds(), SysUserExceptionConstant.NOT_DEPT);
        AssertUtil.isEmpty(createSysUserVO.getRoleIds(), SysUserExceptionConstant.NOT_ROLE);
        AssertUtil.isEmpty(createSysUserVO.getUsername(), SysPostExceptionConstant.NOT_USERNAME);
        AssertUtil.isEmpty(createSysUserVO.getLoginName(), SysPostExceptionConstant.NOT_LOGIN_NAME);
        AssertUtil.isEmpty(createSysUserVO.getPassword(), SysPostExceptionConstant.NOT_PASSWORD);
        AssertUtil.isEmpty(createSysUserVO.getSex(), SysPostExceptionConstant.NOT_SEX);
        AssertUtil.isEmpty(createSysUserVO.getPhone(), SysPostExceptionConstant.NOT_PHONE);
    }

    /**
     * 是否存在用户
     *
     * @param phone 手机号
     * @return SysUser
     */
    public SysUser isExistUserByPhone(String phone) {
        SysUser sysUser = this.getOne(Wrappers.lambdaQuery(SysUser.class).eq(SysUser::getPhone, phone).eq(SysUser::getIsDeleted, false));
        AssertUtil.isNotEmpty(sysUser, SysUserExceptionConstant.EXIST_PHONE);
        return sysUser;
    }

    /**
     * 是否存在用户
     *
     * @param loginName 登录名
     * @return SysUser
     */
    public SysUser isExistUserByLoginName(String loginName) {
        SysUser sysUser = this.getOne(Wrappers.lambdaQuery(SysUser.class).eq(SysUser::getLoginName, loginName).eq(SysUser::getIsDeleted, false));
        AssertUtil.isNotEmpty(sysUser, SysUserExceptionConstant.EXIST_LOGIN_NAME);
        return sysUser;
    }

    /**
     * 构建系统用户
     *
     * @param createSysUserVO
     * @return SysUser
     */
    public SysUser buildSysUser(CreateSysUserVO createSysUserVO) {
        return SysUser.builder()
                .age(createSysUserVO.getAge())
                .avatar(createSysUserVO.getAvatar())
                .birthday(createSysUserVO.getBirthday())
                .createTime(LocalDateTime.now())
                .email(createSysUserVO.getEmail())
                .username(createSysUserVO.getUsername())
                .sex(createSysUserVO.getSex())
                .password(createSysUserVO.getPassword())
                .isDeleted(false)
                .loginName(createSysUserVO.getLoginName())
                .phone(createSysUserVO.getPhone())
                .status(createSysUserVO.getStatus())
                .build();
    }

    /**
     * 校验编辑系统用户参数
     *
     * @param modifySysUserVO
     */
    public void checkModifySysUserParam(ModifySysUserVO modifySysUserVO) {
        AssertUtil.isEmpty(modifySysUserVO.getUserId(), SysPostExceptionConstant.NOT_USER_ID);
        this.checkCreateSysUserParam(modifySysUserVO);
    }

    /**
     * 查询系统用户通过用户ID
     *
     * @param userId
     * @return SysUser
     */
    public SysUser selectSysUserByUserId(Long userId) {
        return this.getOne(Wrappers.lambdaQuery(SysUser.class).eq(SysUser::getUserId, userId).eq(SysUser::getIsDeleted, false));
    }

    /**
     * 查询系统用户缓存通过用户ID
     *
     * @param userId 用户ID
     * @return SysUser
     */
    public SysUser selectSysUserCacheByUserId(Long userId) {
        // 查询: 查询缓存
        String sysUserInfo = redisOperator.get(SysUserKey.SYS_USER_INFO_MAP + userId);
        // 校验: 校验缓存不为空直接返回
        if (StringUtil.isNotEmpty(sysUserInfo)) {
            return JSON.parseObject(sysUserInfo, SysUser.class);
        }
        // 获取: 校验系统用户是否存在，存在直接返回，不存在直接抛异常
        SysUser sysUser = this.selectSysUserByUserId(userId);
        // 设置缓存: 设置系统用户信息缓存
        if (null == sysUser) {
            return null;
        }
        redisOperator.set(SysUserKey.SYS_USER_INFO_MAP + userId, JSON.toJSONString(sysUser), 36000);
        return sysUser;
    }
}
