package com.example.layer.sys.application;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.layer.sys.engine.domain.service.SysDictDataDomainService;
import com.example.layer.sys.engine.service.SysDictDataService;
import com.example.layer.sys.dto.SysDictDataDTO;
import com.example.layer.sys.vo.ModifySysDictDataVO;
import com.example.layer.sys.vo.SysDictDataVO;
import org.springframework.stereotype.Service;
import org.springlayer.core.boot.context.CurrentUser;

import javax.annotation.Resource;

/**
 * @Author zhaoyl
 * @Date 2022-04-23
 * @description 字典值应用层
 **/
@Service
public class SysDictDataApplicationService {

    @Resource
    private SysDictDataDomainService sysDictDataDomainService;
    @Resource
    private SysDictDataService sysDictDataService;

    /**
     * 创建字典数据
     *
     * @param sysDictDataVO
     * @param currentUser
     * @return boolean
     */
    public boolean createDictData(SysDictDataVO sysDictDataVO, CurrentUser currentUser) {
        //非空校验
        sysDictDataDomainService.checkCreatIFNotDictDataParam(sysDictDataVO);
        //字典数据-标签唯一校验
        sysDictDataDomainService.checkCreateDictDataByLabel(sysDictDataVO.getDictLabel());
        //字典数据-键值唯一校验
        sysDictDataDomainService.checkCreateDictDataByValue(sysDictDataVO.getDictValue(),sysDictDataVO.getDictType());
        //保存
        return sysDictDataService.savaSysDictData(sysDictDataVO, currentUser);
    }

    /**
     * 修改字典数据
     *
     * @param modifySysDictDataVO
     * @param currentUser
     * @return Boolean
     */
    public boolean modifySysDictData(ModifySysDictDataVO modifySysDictDataVO, CurrentUser currentUser) {
        //非空校验
        sysDictDataDomainService.checkModifyIFNotDictDataParam(modifySysDictDataVO);
        //字典数据-标签唯一校验
        sysDictDataDomainService.checkModifyDictDataByLabel(modifySysDictDataVO);
        //字典数据-键值唯一校验
        sysDictDataDomainService.checkModifyDictDataByValue(modifySysDictDataVO);
        //修改
        return sysDictDataService.ModifySysDictData(modifySysDictDataVO, currentUser);
    }

    /**
     * 查询字典数据
     *
     * @param dictCode
     * @return SysDictDataDTO
     */
    public SysDictDataDTO querySysDictDataDetail(Long dictCode) {
        return sysDictDataService.queryDetail(dictCode);
    }

    /**
     * 删除字典数据根据dictCode
     *
     * @param dictCode
     * @return Boolean
     */
    public Boolean removeSysDictData(Long dictCode) {
        return sysDictDataService.removeDictData(dictCode);
    }

    /**
     * 分页查询
     *
     * @param current
     * @param size
     * @param dictLabel
     * @param dictValue
     * @param dictType
     * @param status
     * @return IPage<SysDictDataDTO>
     */
    public IPage<SysDictDataDTO> querySysDictDataPage(Integer current, Integer size, String dictLabel, String dictValue, String dictType, Integer status) {
        return sysDictDataService.querySysDictDataPage(current, size, dictLabel, dictValue, dictType, status);
    }
}
