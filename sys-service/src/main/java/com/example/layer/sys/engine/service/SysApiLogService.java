package com.example.layer.sys.engine.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.layer.sys.engine.domain.service.SysApiLogDomainService;
import org.springlayer.core.log.model.LogApi;
import org.springlayer.core.mp.support.Condition;
import org.springlayer.core.tool.utils.StringUtil;
import com.example.layer.sys.dto.SysApiLogDTO;
import com.example.layer.sys.engine.mapper.SysApiLogMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Hzhi
 * @Date 2022-04-23 16:36
 * @description 操作API日子
 **/
@Service
public class SysApiLogService {

    @Resource
    private SysApiLogMapper sysApiLogMapper;
    @Resource
    private SysApiLogDomainService sysApiLogDomainService;

    /**
     * 查询系统API分页
     *
     * @param current
     * @param size
     * @param title
     * @return IPage<SysApiLogDTO>
     */
    public IPage<SysApiLogDTO> querySysApiPage(Integer current, Integer size, String title) {
        Page page = new Page(current, size);
        LogApi logApi = new LogApi();
        QueryWrapper<LogApi> queryWrapper = Condition.getQueryWrapper(logApi);
        if (StringUtil.isNotBlank(title)) {
            queryWrapper.lambda().like(LogApi::getTitle, title);
        }
        queryWrapper.lambda().orderByAsc(LogApi::getOperTime);
        Page<LogApi> logApiPage = sysApiLogMapper.selectPage(page, queryWrapper);
        return this.convertToSysApiLogDTOPage(logApiPage);
    }

    /**
     * 转换系统API分页
     *
     * @param logApiPage
     * @return IPage<SysApiLogDTO>
     */
    private IPage<SysApiLogDTO> convertToSysApiLogDTOPage(Page<LogApi> logApiPage) {
        List<LogApi> records = logApiPage.getRecords();
        if (StringUtil.isEmpty(records)) {
            return new Page<>(logApiPage.getCurrent(), logApiPage.getSize(), logApiPage.getTotal());
        }
        List<SysApiLogDTO> list = sysApiLogDomainService.convertToSysApiDTOList(records);
        IPage<SysApiLogDTO> pageVo = new Page<>(logApiPage.getCurrent(), logApiPage.getSize(), logApiPage.getTotal());
        pageVo.setRecords(list);
        return pageVo;
    }
}