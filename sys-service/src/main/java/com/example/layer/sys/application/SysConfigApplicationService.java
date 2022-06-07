package com.example.layer.sys.application;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.layer.sys.dto.SysConfigDTO;
import com.example.layer.sys.engine.domain.core.SysConfig;
import com.example.layer.sys.engine.domain.service.SysConfigDomainService;
import com.example.layer.sys.engine.service.SysConfigsService;
import com.example.layer.sys.vo.ModifySysConfigVO;
import com.example.layer.sys.vo.SysConfigVO;
import org.springframework.stereotype.Service;
import org.springlayer.core.boot.context.CurrentUser;

import javax.annotation.Resource;

/**
 * @Author Hzhi
 * @Date 2022-04-25 9:25
 * @description
 **/
@Service
public class SysConfigApplicationService {

    @Resource
    private SysConfigDomainService sysConfigDomainService;
    @Resource
    private SysConfigsService sysConfigsService;

    /**
     * 创建系统参数配置
     *
     * @param sysConfigVO 系统参数
     * @param currentUser
     * @return boolean
     */
    public boolean createSysConfig(SysConfigVO sysConfigVO, CurrentUser currentUser) {
        // 校验: 校验系统参数
        sysConfigDomainService.checkSysConfigParam(sysConfigVO);
        // 校验: 校验系统是否存在KEY
        sysConfigDomainService.existSysConfigByKey(sysConfigVO.getConfigKey());
        // 保存: 保存系统参数配置
        return sysConfigsService.createSysConfig(sysConfigVO, currentUser);
    }

    /**
     * 移除系统参数
     *
     * @param configId 参数ID
     * @return boolean
     */
    public boolean removeSysConfig(Long configId) {
        // 查询: 查询系统参数通过ID
        SysConfig sysConfig = sysConfigDomainService.selectSysConfigById(configId);
        if (null == sysConfig) {
            return true;
        }
        return sysConfigsService.removeSysConfig(sysConfig);
    }

    /**
     * 编辑系统参数
     *
     * @param modifySysConfigVO 修改的系统参数
     * @return boolean
     */
    public boolean modifySysConfig(ModifySysConfigVO modifySysConfigVO, CurrentUser currentUser) {
        // 校验: 校验系统参数
        sysConfigDomainService.checkModifySysConfigParam(modifySysConfigVO);
        // 校验: 校验系统是否存在
        SysConfig sysConfig = sysConfigDomainService.isExistSysConfig(Long.valueOf(modifySysConfigVO.getConfigId()));
        // 保存: 保存系统参数配置
        return sysConfigsService.modifySysConfig(sysConfig, modifySysConfigVO, currentUser);
    }

    /**
     * 分页查询系统参数
     *
     * @param current    当前页
     * @param size       每页条数
     * @param configName 参数名称
     * @param configKey  参数KEY
     * @return IPage<SysConfigDTO>
     */
    public IPage<SysConfigDTO> querySysConfigPage(Integer current, Integer size, String configName, String configKey) {
        return sysConfigsService.querySysConfigPage(current, size, configName, configKey);
    }
}