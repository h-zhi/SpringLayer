package com.example.layer.sys.engine.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.layer.sys.dto.SysConfigDTO;
import com.example.layer.sys.engine.domain.convertot.SysConfigConvertor;
import com.example.layer.sys.engine.domain.core.SysConfig;
import com.example.layer.sys.engine.domain.service.SysConfigDomainService;
import com.example.layer.sys.engine.mapper.SysConfigMapper;
import com.example.layer.sys.vo.ModifySysConfigVO;
import com.example.layer.sys.vo.SysConfigVO;
import org.springframework.stereotype.Service;
import org.springlayer.core.boot.context.CurrentUser;
import org.springlayer.core.mp.support.Condition;
import org.springlayer.core.tool.utils.StringUtil;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Hzhi
 * @Date 2022-04-25 9:24
 * @description
 **/
@Service
public class SysConfigsService {

    @Resource
    private SysConfigDomainService sysConfigDomainService;
    @Resource
    private SysConfigMapper sysConfigMapper;

    /**
     * 创建系统参数
     *
     * @param sysConfigVO
     * @param currentUser
     * @return boolean
     */
    public boolean createSysConfig(SysConfigVO sysConfigVO, CurrentUser currentUser) {
        // 构建系统参数配置
        SysConfig sysConfig = sysConfigDomainService.buildSysConfig(sysConfigVO, currentUser);
        return sysConfigDomainService.save(sysConfig);
    }

    /**
     * 移除系统参数
     *
     * @param sysConfig
     * @return boolean
     */
    public boolean removeSysConfig(SysConfig sysConfig) {
        sysConfig.setIsDeleted(true);
        return sysConfigDomainService.updateById(sysConfig);
    }

    /**
     * 修改系统参数
     *
     * @param sysConfig
     * @param modifySysConfigVO
     * @param currentUser
     * @return boolean
     */
    public boolean modifySysConfig(SysConfig sysConfig, ModifySysConfigVO modifySysConfigVO, CurrentUser currentUser) {
        sysConfigDomainService.buildModifySysConfig(sysConfig, modifySysConfigVO, currentUser);
        return sysConfigDomainService.updateById(sysConfig);
    }

    /**
     * 分页查询系统配置
     *
     * @param current
     * @param size
     * @param configName
     * @param configKey
     * @return IPage<SysConfigDTO>
     */
    public IPage<SysConfigDTO> querySysConfigPage(Integer current, Integer size, String configName, String configKey) {
        Page page = new Page(current, size);
        SysConfig sysConfig = SysConfig.builder().build();
        QueryWrapper<SysConfig> queryWrapper = Condition.getQueryWrapper(sysConfig);
        if (StringUtil.isNotEmpty(configName)) {
            queryWrapper.lambda().like(SysConfig::getConfigName, configName);
        }
        if (StringUtil.isNotEmpty(configKey)) {
            queryWrapper.lambda().like(SysConfig::getConfigKey, configKey);
        }
        Page<SysConfig> sysPostPage = sysConfigMapper.selectPage(page, queryWrapper);
        // 转换: 转换系统岗位分页
        return this.convertToSysConfigDTOPage(sysPostPage);
    }

    /**
     * 转换系统参数分页数据
     *
     * @param sysConfigPage
     * @return Page<SysConfigDTO>
     */
    private IPage<SysConfigDTO> convertToSysConfigDTOPage(Page<SysConfig> sysConfigPage) {
        List<SysConfig> records = sysConfigPage.getRecords();
        if (StringUtil.isEmpty(records)) {
            return new Page<>(sysConfigPage.getCurrent(), sysConfigPage.getSize(), sysConfigPage.getTotal());
        }
        List<SysConfigDTO> list = SysConfigConvertor.convertToSysConfigDTOList(records);
        IPage<SysConfigDTO> pageVo = new Page<>(sysConfigPage.getCurrent(), sysConfigPage.getSize(), sysConfigPage.getTotal());
        pageVo.setRecords(list);
        return pageVo;
    }
}