package com.example.layer.sys.application;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.layer.sys.engine.service.SysApiLogService;
import com.example.layer.sys.engine.service.SysLogininforService;
import com.example.layer.sys.dto.SysApiLogDTO;
import com.example.layer.sys.dto.SysLogininforDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author Hzhi
 * @Date 2022-04-25 11:38
 * @description
 **/
@Service
public class SysLogApplicationService {

    @Resource
    private SysLogininforService sysLogininforService;
    @Resource
    private SysApiLogService sysApiLogService;

    /**
     * 分页查询系统登录信息
     *
     * @param current
     * @param size
     * @param loginName
     * @return IPage<SysLogininforDTO>
     */
    public IPage<SysLogininforDTO> querySysLoginInfoPage(Integer current, Integer size, String loginName) {
        return sysLogininforService.querySysLoginInfoPage(current, size, loginName);
    }

    /**
     * 分页查询API日志
     *
     * @param current
     * @param size
     * @param title
     * @return IPage<SysApiLogDTO>
     */
    public IPage<SysApiLogDTO> querySysApiPage(Integer current, Integer size, String title) {
        return sysApiLogService.querySysApiPage(current, size, title);
    }
}
