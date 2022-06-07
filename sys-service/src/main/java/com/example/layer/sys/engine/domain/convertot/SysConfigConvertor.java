package com.example.layer.sys.engine.domain.convertot;

import com.example.layer.sys.engine.domain.core.SysConfig;
import com.example.layer.sys.dto.SysConfigDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Hzhi
 * @Date 2022-05-17 14:22
 * @description
 **/
@Service
public class SysConfigConvertor {

    /**
     * 转换系统参数配置集合
     *
     * @param records
     * @return List<SysConfigDTO>
     */
    public static List<SysConfigDTO> convertToSysConfigDTOList(List<SysConfig> records) {
        return records.stream().map(sysConfig -> convertToSysConfigDTO(sysConfig)).collect(Collectors.toList());
    }

    /**
     * 转换系统参数
     *
     * @param sysConfig
     * @return SysConfigDTO
     */
    public static SysConfigDTO convertToSysConfigDTO(SysConfig sysConfig) {
        SysConfigDTO sysConfigDTO = new SysConfigDTO();
        BeanUtils.copyProperties(sysConfig, sysConfigDTO);
        sysConfigDTO.setConfigId(sysConfig.getConfigId().toString());
        return sysConfigDTO;
    }
}
