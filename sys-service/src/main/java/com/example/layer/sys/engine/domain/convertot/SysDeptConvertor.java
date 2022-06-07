package com.example.layer.sys.engine.domain.convertot;

import com.example.layer.sys.engine.domain.core.SysDept;
import com.example.layer.sys.dto.SysDeptDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Hzhi
 * @Date 2022-05-17 14:25
 * @description
 **/
@Service
public class SysDeptConvertor {

    /**
     * 转换系统组织信息集合
     *
     * @param sysDeptList
     * @return List<SysDept>
     */
    public static List<SysDeptDTO> convertToSysDeptDTOs(List<SysDept> sysDeptList) {
        return sysDeptList.stream().map(sysDept -> convertToSysDeptDTO(sysDept)).collect(Collectors.toList());
    }

    /**
     * 转换系统组织详情信息
     *
     * @param sysDept 系统组织
     * @return SysDeptDTO
     */
    public static SysDeptDTO convertToSysDeptDTO(SysDept sysDept) {
        SysDeptDTO sysDeptDTO = new SysDeptDTO();
        BeanUtils.copyProperties(sysDept, sysDeptDTO);
        sysDeptDTO.setDeptId(sysDept.getDeptId().toString());
        return sysDeptDTO;
    }

}