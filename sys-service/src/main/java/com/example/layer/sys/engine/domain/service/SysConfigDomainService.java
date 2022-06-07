package com.example.layer.sys.engine.domain.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.layer.sys.engine.domain.core.SysConfig;
import com.example.layer.sys.engine.mapper.SysConfigMapper;
import com.example.layer.sys.infrastructure.exception.SysConfigExceptionConstant;
import com.example.layer.sys.vo.ModifySysConfigVO;
import com.example.layer.sys.vo.SysConfigVO;
import org.springframework.stereotype.Service;
import org.springlayer.core.boot.context.CurrentUser;
import org.springlayer.core.tool.utils.AssertUtil;

import java.time.LocalDateTime;

/**
 * @Author Hzhi
 * @Date 2022-04-25 9:22
 * @description
 **/
@Service
public class SysConfigDomainService extends ServiceImpl<SysConfigMapper, SysConfig> {

    /**
     * 校验系统参数
     *
     * @param sysConfigVO
     */
    public void checkSysConfigParam(SysConfigVO sysConfigVO) {
        AssertUtil.isEmpty(sysConfigVO.getConfigName(), SysConfigExceptionConstant.NOT_CONFIG_NAME);
        AssertUtil.isEmpty(sysConfigVO.getConfigKey(), SysConfigExceptionConstant.NOT_CONFIG_KEY);
        AssertUtil.isEmpty(sysConfigVO.getConfigValue(), SysConfigExceptionConstant.NOT_CONFIG_VAL);
    }

    /**
     * 是否存在系统参数KEY
     *
     * @param key
     * @return SysConfig
     */
    public SysConfig existSysConfigByKey(String key) {
        SysConfig sysConfig = this.getOne(Wrappers.lambdaQuery(SysConfig.class).eq(SysConfig::getConfigKey, key).eq(SysConfig::getIsDeleted, false));
        AssertUtil.isNotEmpty(sysConfig, SysConfigExceptionConstant.EXIST_KEY);
        return sysConfig;
    }

    /**
     * 构建系统参数
     *
     * @param sysConfigVO
     * @param currentUser
     * @return SysConfig
     */
    public SysConfig buildSysConfig(SysConfigVO sysConfigVO, CurrentUser currentUser) {
        return SysConfig.builder()
                .configKey(sysConfigVO.getConfigKey())
                .configName(sysConfigVO.getConfigName())
                .createBy(currentUser.getUserId())
                .createTime(LocalDateTime.now())
                .isDeleted(false)
                .configValue(sysConfigVO.getConfigValue())
                .remark(sysConfigVO.getRemark()).build();
    }

    /**
     * 查询系统参数通过ID
     *
     * @param configId
     * @return SysConfig
     */
    public SysConfig selectSysConfigById(Long configId) {
        return this.getOne(Wrappers.lambdaQuery(SysConfig.class).eq(SysConfig::getConfigId, configId).eq(SysConfig::getIsDeleted, false));
    }

    /**
     * 校验修改的系统参数
     *
     * @param modifySysConfigVO
     */
    public void checkModifySysConfigParam(ModifySysConfigVO modifySysConfigVO) {
        AssertUtil.isEmpty(modifySysConfigVO.getConfigId(), SysConfigExceptionConstant.NOT_CONFIG_ID);
        this.checkSysConfigParam(modifySysConfigVO);
    }

    /**
     * 校验系统参数是否存在
     *
     * @param configId
     */
    public SysConfig isExistSysConfig(Long configId) {
        SysConfig sysConfig = this.selectSysConfigById(configId);
        AssertUtil.isEmpty(sysConfig, SysConfigExceptionConstant.NOT_EXIST);
        return sysConfig;
    }

    /**
     * 构建修改参数
     *
     * @param sysConfig
     * @param modifySysConfigVO
     * @param currentUser
     */
    public void buildModifySysConfig(SysConfig sysConfig, ModifySysConfigVO modifySysConfigVO, CurrentUser currentUser) {
        sysConfig.setConfigName(modifySysConfigVO.getConfigName());
        sysConfig.setRemark(modifySysConfigVO.getRemark());
        sysConfig.setConfigValue(modifySysConfigVO.getConfigValue());
        sysConfig.setUpdateBy(currentUser.getUserId());
        sysConfig.setUpdateTime(LocalDateTime.now());
    }

}