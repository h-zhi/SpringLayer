package com.example.layer.sys.engine.domain.convertot;

import com.example.layer.sys.engine.domain.core.SysDictData;
import com.example.layer.sys.dto.SysDictDataDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Hzhi
 * @Date 2022-05-17 14:27
 * @description
 **/
@Service
public class SysDictDataConvertor {

    /**
     * 转换 字典数据集合
     *
     * @param sysDictDataList
     * @return
     */
    public static List<SysDictDataDTO> convertToSysDictDataDTOList(List<SysDictData> sysDictDataList) {
        return sysDictDataList.stream().map(sysDictData -> convertToSysDictDataDTO(sysDictData)).collect(Collectors.toList());
    }

    /**
     * 转换 字典数据
     *
     * @param sysDictData
     * @return
     */
    public static SysDictDataDTO convertToSysDictDataDTO(SysDictData sysDictData) {
        SysDictDataDTO sysDictDataDTO = new SysDictDataDTO();
        BeanUtils.copyProperties(sysDictData, sysDictDataDTO);
        sysDictDataDTO.setDictCode(sysDictData.getDictCode().toString());
        return sysDictDataDTO;
    }

}
