package com.example.layer.sys.engine.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.layer.sys.engine.domain.core.SysLogininfor;
import org.springlayer.core.mp.support.Condition;
import org.springlayer.core.tool.utils.StringUtil;
import com.example.layer.sys.dto.SysLogininforDTO;
import com.example.layer.sys.engine.domain.convertot.SysLogininforConvertor;
import com.example.layer.sys.engine.mapper.SysLogininforMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Hzhi
 * @Date 2022-04-26 13:49
 * @description
 **/
@Service
public class SysLogininforService {

    @Resource
    private SysLogininforMapper sysLogininforMapper;

    /**
     * 查询系统登录信息
     *
     * @param current
     * @param size
     * @param loginName
     * @return Page<SysLogininforDTO>
     */
    public IPage<SysLogininforDTO> querySysLoginInfoPage(Integer current, Integer size, String loginName) {
        Page page = new Page(current, size);
        SysLogininfor sysLogininfor = new SysLogininfor().builder().build();
        QueryWrapper<SysLogininfor> queryWrapper = Condition.getQueryWrapper(sysLogininfor);
        if (StringUtil.isNotBlank(loginName)) {
            queryWrapper.lambda().like(SysLogininfor::getLoginName, loginName);
        }
        queryWrapper.lambda().orderByAsc(SysLogininfor::getLoginTime);
        Page<SysLogininfor> sysRolePage = sysLogininforMapper.selectPage(page, queryWrapper);
        return this.convertToSysLoginInfoDTOPage(sysRolePage);
    }

    /**
     * 转换系统登录DTO
     *
     * @param sysLogininforPage
     * @return IPage<SysLogininforDTO>
     */
    private IPage<SysLogininforDTO> convertToSysLoginInfoDTOPage(Page<SysLogininfor> sysLogininforPage) {
        List<SysLogininfor> records = sysLogininforPage.getRecords();
        if (StringUtil.isEmpty(records)) {
            return new Page<>(sysLogininforPage.getCurrent(), sysLogininforPage.getSize(), sysLogininforPage.getTotal());
        }
        List<SysLogininforDTO> list = SysLogininforConvertor.convertToSysLoginInfoDTOList(records);
        IPage<SysLogininforDTO> pageVo = new Page<>(sysLogininforPage.getCurrent(), sysLogininforPage.getSize(), sysLogininforPage.getTotal());
        pageVo.setRecords(list);
        return pageVo;
    }
}
