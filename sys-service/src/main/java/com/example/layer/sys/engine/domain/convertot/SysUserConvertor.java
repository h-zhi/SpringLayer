package com.example.layer.sys.engine.domain.convertot;

import com.example.layer.sys.engine.domain.core.SysUser;
import com.example.layer.sys.dto.SysUserDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Hzhi
 * @Date 2022-05-17 14:17
 * @description
 **/
@Service
public class SysUserConvertor {

    /**
     * 转换用户信息
     *
     * @param sysUser
     * @return SysUserDTO
     */
    public static SysUserDTO convertToSysUserDTO(SysUser sysUser) {
        SysUserDTO sysUserDTO = new SysUserDTO();
        BeanUtils.copyProperties(sysUser, sysUserDTO);
        sysUserDTO.setUserId(sysUser.getUserId().toString());
        return sysUserDTO;
    }

    /**
     * 转换系统用户信息集合
     *
     * @param sysUserList
     * @return List<SysUserDTO>
     */
    public static List<SysUserDTO> convertToSysUserDTOList(List<SysUser> sysUserList) {
        return sysUserList.stream().map(sysUser -> convertToSysUserDTO(sysUser)).collect(Collectors.toList());
    }
}
