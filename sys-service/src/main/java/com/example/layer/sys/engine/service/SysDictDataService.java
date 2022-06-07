package com.example.layer.sys.engine.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.layer.sys.engine.domain.core.SysDictData;
import com.example.layer.sys.engine.domain.service.SysDictDataDomainService;
import org.springlayer.core.boot.context.CurrentUser;
import org.springlayer.core.mp.support.Condition;
import org.springlayer.core.redis.helper.RedisOperator;
import org.springlayer.core.tool.utils.StringUtil;
import com.example.layer.sys.dto.SysDictDataDTO;
import com.example.layer.sys.engine.components.redis.SysDictDataKey;
import com.example.layer.sys.engine.domain.convertot.SysDictDataConvertor;
import com.example.layer.sys.engine.mapper.SysDictDataMapper;
import com.example.layer.sys.vo.ModifySysDictDataVO;
import com.example.layer.sys.vo.SysDictDataVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author zhaoyl
 * @Date 2022-04-23
 * @description
 **/
@Service
public class SysDictDataService {
    @Resource
    private RedisOperator redisOperator;
    @Resource
    private SysDictDataDomainService sysDictDataDomainService;
    @Resource
    private SysDictDataMapper sysDictDataMapper;

    /**
     * 保存字典数据
     *
     * @param sysDictDataVO
     * @param currentUser
     * @return
     */
    public boolean savaSysDictData(SysDictDataVO sysDictDataVO, CurrentUser currentUser) {
        SysDictData sysDictData = sysDictDataDomainService.buildSysDictDataVO(sysDictDataVO, currentUser);
        return sysDictDataDomainService.save(sysDictData);
    }

    /**
     * 修改字典数据
     *
     * @param modifySysDictDataVO
     * @param currentUser
     * @return
     */
    public boolean ModifySysDictData(ModifySysDictDataVO modifySysDictDataVO, CurrentUser currentUser) {
        SysDictData sysDictData = sysDictDataDomainService.buildModifySysDictData(modifySysDictDataVO, currentUser);
        return sysDictDataDomainService.updateById(sysDictData);
    }

    /**
     * 查询字典数据详情
     *
     * @param dictCode
     * @return
     */
    public SysDictDataDTO queryDetail(Long dictCode) {
        SysDictData sysDictData = sysDictDataDomainService.selectCacheSysDictDataById(dictCode);
        if (sysDictData==null){
            return null;
        }
        return SysDictDataConvertor.convertToSysDictDataDTO(sysDictData);
    }

    /**
     * 移除字典数据
     *
     * @param dictCode
     * @return
     */
    public Boolean removeDictData(Long dictCode) {
        redisOperator.del(SysDictDataKey.SYS_DICT_DATA_INFO_MAP);
        return sysDictDataDomainService.removeById(dictCode);
    }

    /**
     * 移除字典数据通过类型
     */
    public void removeDictDataByType(String dictType){
        List<SysDictData> sysDictDatas = sysDictDataDomainService.selectDictDataByType(dictType);
        if (sysDictDatas.size()>0){
            sysDictDatas.forEach(sysDictData -> {this.removeDictData(sysDictData.getDictCode());});
        }
    }
    /**
     * 分页查询字典数据
     *
     * @param current
     * @param size
     * @param dictLabel
     * @param dictValue
     * @param dictType
     * @param status
     * @return
     */
    public IPage<SysDictDataDTO> querySysDictDataPage(Integer current, Integer size, String dictLabel, String dictValue, String dictType, Integer status) {
        Page page = new Page(current, size);
        SysDictData sysDictData = SysDictData.builder().build();
        QueryWrapper<SysDictData> queryWrapper = Condition.getQueryWrapper(sysDictData);
        if (StringUtil.isNotBlank(dictLabel)) {
            queryWrapper.lambda().like(SysDictData::getDictLabel, dictLabel);
        }
        if (StringUtil.isNotBlank(dictType)) {
            queryWrapper.lambda().eq(SysDictData::getDictType, dictType);
        }
        if (StringUtil.isNotBlank(dictValue)) {
            queryWrapper.lambda().eq(SysDictData::getDictValue, dictValue);
        }
        if (!StringUtil.isEmpty(status)) {
            queryWrapper.lambda().eq(SysDictData::getStatus, status);
        }
        Page<SysDictData> sysDictDataPage = sysDictDataMapper.selectPage(page, queryWrapper);
        IPage<SysDictDataDTO> sysDictTypeDTOIPage = convertToSysDictTypeDTOPage(sysDictDataPage);
        return sysDictTypeDTOIPage;
    }

    /**
     * 转换分页数据
     *
     * @param sysDictDataPage
     * @return
     */
    private IPage<SysDictDataDTO> convertToSysDictTypeDTOPage(Page<SysDictData> sysDictDataPage) {
        List<SysDictData> records = sysDictDataPage.getRecords();
        if (StringUtil.isEmpty(records)) {
            return new Page<>(sysDictDataPage.getCurrent(), sysDictDataPage.getSize(), sysDictDataPage.getTotal());
        }
        List<SysDictDataDTO> list = SysDictDataConvertor.convertToSysDictDataDTOList(records);
        IPage<SysDictDataDTO> pageVo = new Page<>(sysDictDataPage.getCurrent(), sysDictDataPage.getSize(), sysDictDataPage.getTotal());
        pageVo.setRecords(list);
        return pageVo;
    }
}
