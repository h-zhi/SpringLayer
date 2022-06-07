package com.example.layer.sys.engine.domain.convertot;

import com.example.layer.sys.engine.domain.core.SysRole;
import com.example.layer.sys.dto.SysRoleDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Hzhi
 * @Date 2022-05-17 14:39
 * @description
 **/
@Service
public class SysRoleConvertor {

    /**
     * 转换角色信息
     *
     * @param sysRole
     * @return SysPostDTO
     */
    public static SysRoleDTO convertToSysRoleDTO(SysRole sysRole) {
        SysRoleDTO roleDTO = new SysRoleDTO();
        BeanUtils.copyProperties(sysRole, roleDTO);
        roleDTO.setRoleId(sysRole.getRoleId().toString());
        return roleDTO;
    }

    /**
     * 转换系统角色DTO集合
     *
     * @param sysRoleList
     * @return List<SysPostDTO>
     */
    public static List<SysRoleDTO> convertToSysPostDTOList(List<SysRole> sysRoleList) {
        return sysRoleList.stream().map(tenantRole -> convertToSysRoleDTO(tenantRole)).collect(Collectors.toList());
    }
}
