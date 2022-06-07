package com.example.layer.sys.engine.domain.convertot;

import com.example.layer.sys.engine.domain.core.SysLogininfor;
import com.example.layer.sys.dto.SysLogininforDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Hzhi
 * @Date 2022-05-17 14:31
 * @description
 **/
@Service
public class SysLogininforConvertor {

    /**
     * 集合转换DTO
     *
     * @param records
     * @return List<SysLogininforDTO>
     */
    public static List<SysLogininforDTO> convertToSysLoginInfoDTOList(List<SysLogininfor> records) {
        return records.stream().map(sys -> convertToSysLoginInfoDTO(sys)).collect(Collectors.toList());
    }

    /**
     * 转换系统登录DTO
     *
     * @param sysLogininfor
     * @return SysLogininforDTO
     */
    private static SysLogininforDTO convertToSysLoginInfoDTO(SysLogininfor sysLogininfor) {
        SysLogininforDTO sysLogininforDTO = new SysLogininforDTO();
        BeanUtils.copyProperties(sysLogininfor, sysLogininforDTO);
        sysLogininforDTO.setInfoId(sysLogininfor.getInfoId().toString());
        return sysLogininforDTO;
    }
}
