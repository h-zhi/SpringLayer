package com.example.layer.sys.application;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.layer.sys.engine.domain.core.SysDictType;
import com.example.layer.sys.engine.domain.service.SysDictTypeDomainService;
import com.example.layer.sys.engine.service.SysDictDataService;
import com.example.layer.sys.engine.service.SysDictTypeService;
import org.springlayer.core.boot.context.CurrentUser;
import com.example.layer.sys.dto.SysDictTypeDTO;
import com.example.layer.sys.vo.ModifySysDictTypeVO;
import com.example.layer.sys.vo.SysDictTypeVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author zhaoyl
 * @Date 2022-04-23
 * @description 字典类型应用层
 **/
@Service
public class SysDictTypeApplicationService {
    @Resource
    private SysDictTypeService sysDictTypeService;
    @Resource
    private SysDictTypeDomainService sysDictTypeDomainService;
    @Resource
    private SysDictDataService sysDictDataService;
    /**
     * 创建字典类型
     *
     * @param sysDictTypeVO
     * @param currentUser
     * @return
     */
    public boolean createDictType(SysDictTypeVO sysDictTypeVO, CurrentUser currentUser) {
        //非空校验
        sysDictTypeDomainService.checkCreatIFNotDictTypeParam(sysDictTypeVO);
        //字典类型-名称唯一校验
        sysDictTypeDomainService.checkCreatDictTypeByName(sysDictTypeVO.getDictName());
        //字典类型-类型唯一校验
        sysDictTypeDomainService.checkCreatDictTypeByType(sysDictTypeVO.getDictType());
        //保存
        return sysDictTypeService.savaSysDictType(sysDictTypeVO, currentUser);
    }

    /**
     * 修改字典类型
     *
     * @param modifySysDictTypeVO
     * @param currentUser
     * @return
     */
    public Boolean modifySysDictType(ModifySysDictTypeVO modifySysDictTypeVO, CurrentUser currentUser) {
        //非空校验
        sysDictTypeDomainService.checkModifyIFNotDictTypeParam(modifySysDictTypeVO);
        //字典类型-名称唯一校验
       sysDictTypeDomainService.checkModifyDictTypeByName(modifySysDictTypeVO);
        //字典类型-类型唯一校验
       sysDictTypeDomainService.checkModifyDictTypeByType(modifySysDictTypeVO);
        //保存
        return sysDictTypeService.ModifySysDictType(modifySysDictTypeVO, currentUser);
    }

    /**
     * 查询字典类型详情通过 dictId
     *
     * @param dictId
     * @return
     */
    public SysDictTypeDTO querySysDictTypeDetail(Long dictId) {
        return sysDictTypeService.queryDetail(dictId);
    }

    /**
     * 移除字典类型
     *
     * @param dictId
     * @return
     */
    public Boolean removeSysDictType(Long dictId) {
        SysDictType dictType = sysDictTypeDomainService.getById(dictId);
        sysDictDataService.removeDictDataByType(dictType.getDictType());
        return sysDictTypeService.removeDicType(dictId);
    }

    /**
     * 分页查询字典类型
     *
     * @param current
     * @param size
     * @param dictName
     * @param dictType
     * @param status
     * @return IPage<SysDictTypeDTO>
     */
    public IPage<SysDictTypeDTO> querySysDictTypePage(Integer current, Integer size, String dictName, String dictType, Integer status) {
        return sysDictTypeService.querySysDictTypePage(current, size, dictName, dictType, status);
    }
}
