package com.example.layer.sys.engine.domain.convertot;

import com.example.layer.sys.engine.domain.core.SysDictType;
import com.example.layer.sys.dto.SysDictTypeDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Hzhi
 * @Date 2022-05-17 14:29
 * @description
 **/
@Service
public class SysDictTypeConvertor {

    /**
     * 转换 字典类型集合
     *
     * @param sysDictTypeList
     * @return
     */
    public static List<SysDictTypeDTO> convertToSysDictTypeDTOList(List<SysDictType> sysDictTypeList) {
        return sysDictTypeList.stream().map(sysDictType -> convertToSysDictTypeDTO(sysDictType)).collect(Collectors.toList());
    }

    /**
     * 转换 字典类型
     *
     * @param sysDictType
     * @return
     */
    public static SysDictTypeDTO convertToSysDictTypeDTO(SysDictType sysDictType) {
        SysDictTypeDTO sysDictTypeDTO = new SysDictTypeDTO();
        BeanUtils.copyProperties(sysDictType, sysDictTypeDTO);
        sysDictTypeDTO.setDictId(sysDictType.getDictId().toString());
        return sysDictTypeDTO;
    }
}