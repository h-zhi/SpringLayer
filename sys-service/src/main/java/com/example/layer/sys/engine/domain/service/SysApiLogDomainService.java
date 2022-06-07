package com.example.layer.sys.engine.domain.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springlayer.core.log.model.LogApi;
import com.example.layer.sys.dto.SysApiLogDTO;
import com.example.layer.sys.engine.mapper.SysApiLogMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Hzhi
 * @Date 2022-04-23 16:37
 * @description api操作日志细粒度逻辑
 **/
@Service
public class SysApiLogDomainService extends ServiceImpl<SysApiLogMapper, LogApi> {

    /**
     * 转换系统API集合
     *
     * @param records
     * @return List<SysApiLogDTO>
     */
    public List<SysApiLogDTO> convertToSysApiDTOList(List<LogApi> records) {
        return records.stream().map(sysApi -> this.convertToSysApiDTO(sysApi)).collect(Collectors.toList());
    }

    /**
     * 转换系统API
     *
     * @param sysApi
     * @return SysApiLogDTO
     */
    private SysApiLogDTO convertToSysApiDTO(LogApi sysApi) {
        SysApiLogDTO sysApiLogDTO = new SysApiLogDTO();
        BeanUtils.copyProperties(sysApi, sysApiLogDTO);
        return sysApiLogDTO;
    }
}