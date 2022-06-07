package com.example.layer.sys.engine.domain.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springlayer.core.boot.context.CurrentUser;
import org.springlayer.core.redis.helper.RedisOperator;
import org.springlayer.core.tool.utils.AssertUtil;
import org.springlayer.core.tool.utils.StringUtil;
import com.example.layer.sys.engine.components.redis.SysDictDataKey;
import com.example.layer.sys.engine.domain.core.SysDictData;
import com.example.layer.sys.engine.mapper.SysDictDataMapper;
import com.example.layer.sys.infrastructure.exception.SysDictDataExceptionConstant;
import com.example.layer.sys.infrastructure.exception.SysDictTypeExceptionConstant;
import com.example.layer.sys.vo.ModifySysDictDataVO;
import com.example.layer.sys.vo.SysDictDataVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;


/**
 * @Author zhaoyl
 * @Date 2022-04-23
 * @description
 **/
@Slf4j
@Service
public class SysDictDataDomainService extends ServiceImpl<SysDictDataMapper, SysDictData> {

    @Resource
    private RedisOperator redisOperator;

    /**
     * 构建新增字典数据
     *
     * @param sysDictDataVO
     * @return
     */
    public SysDictData buildSysDictDataVO(SysDictDataVO sysDictDataVO, CurrentUser currentUser) {
        return SysDictData.builder()
                .dictSort(sysDictDataVO.getDictSort())
                .dictType(sysDictDataVO.getDictType())
                .dictValue(sysDictDataVO.getDictValue())
                .dictLabel(sysDictDataVO.getDictLabel())
                .cssClass(sysDictDataVO.getCssClass())
                .isDefault(sysDictDataVO.getIsDefault())
                .remark(sysDictDataVO.getRemark())
                .isDeleted(false)
                .status(sysDictDataVO.getStatus())
                .listClass(sysDictDataVO.getListClass())
                .createBy(currentUser.getUserId())
                .createTime(LocalDateTime.now())
                .build();
    }

    /**
     * 构建修改字典数据
     *
     * @param modifySysDictDataVO
     * @return
     */
    public SysDictData buildModifySysDictData(ModifySysDictDataVO modifySysDictDataVO, CurrentUser currentUser) {
        return SysDictData.builder()
                .dictCode(Long.parseLong(modifySysDictDataVO.getDictCode()))
                .dictSort(modifySysDictDataVO.getDictSort())
                .dictType(modifySysDictDataVO.getDictType())
                .dictValue(modifySysDictDataVO.getDictValue())
                .dictLabel(modifySysDictDataVO.getDictLabel())
                .cssClass(modifySysDictDataVO.getCssClass())
                .isDefault(modifySysDictDataVO.getIsDefault())
                .listClass(modifySysDictDataVO.getListClass())
                .remark(modifySysDictDataVO.getRemark())
                .isDeleted(false)
                .status(modifySysDictDataVO.getStatus())
                .updateBy(currentUser.getUserId())
                .updateTime(LocalDateTime.now())
                .build();
    }

    /**
     * 校验字典数据是否为空
     *
     * @param sysDictDataVO
     */
    public void checkCreatIFNotDictDataParam(SysDictDataVO sysDictDataVO) {
        AssertUtil.isEmpty(sysDictDataVO.getDictType(), SysDictTypeExceptionConstant.NOT_DICT_TYPE);
        AssertUtil.isEmpty(sysDictDataVO.getDictLabel(), SysDictDataExceptionConstant.NOT_DICT_LABLE);
        AssertUtil.isEmpty(sysDictDataVO.getDictValue(), SysDictDataExceptionConstant.NOT_DICT_VALUE);
        AssertUtil.isEmpty(sysDictDataVO.getDictSort(), SysDictDataExceptionConstant.NOT_DICT_SORT);
        AssertUtil.isEmpty(sysDictDataVO.getIsDefault(), SysDictDataExceptionConstant.NOT_DICT_IS_DEFAULT);
        AssertUtil.isEmpty(sysDictDataVO.getStatus(), SysDictDataExceptionConstant.NOT_DICT_IS_STSTUS);
    }

    /**
     * 校验修改字典数据是否为空
     *
     * @param modifySysDictDataVO
     */
    public void checkModifyIFNotDictDataParam(ModifySysDictDataVO modifySysDictDataVO) {
        AssertUtil.isEmpty(modifySysDictDataVO.getDictCode(), SysDictDataExceptionConstant.NOT_DICT_CODE);
        this.checkCreatIFNotDictDataParam(modifySysDictDataVO);
    }

    /**
     * 校验字典字典标签是否存在
     *
     * @param dictLabel
     */
    public void checkCreateDictDataByLabel(String dictLabel) {
        SysDictData dictData = this.getOne(Wrappers.lambdaQuery(SysDictData.class)
                .eq(SysDictData::getDictLabel, dictLabel)
                .eq(SysDictData::getIsDeleted, false));
        AssertUtil.isNotEmpty(dictData, SysDictDataExceptionConstant.EXIST_DICT_LABLE);
    }

    /**
     * 校验字典键值签是否存在
     *
     * @param dictValue ,dictType
     */
    public void checkCreateDictDataByValue(String dictValue, String dictType) {
        //防止脏数据 查询list
        SysDictData sysDictData = this.getOne(Wrappers.lambdaQuery(SysDictData.class)
                .eq(SysDictData::getDictValue, dictValue)
                .eq(SysDictData::getIsDeleted, false)
                .eq(SysDictData::getDictType, dictType));
        AssertUtil.isNotEmpty(sysDictData, SysDictDataExceptionConstant.EXIST_DICT_VALUE);
    }

    /**
     * 修改
     * 字典数据-标签唯一校验
     *
     * @param modifySysDictDataVO
     */
    public void checkModifyDictDataByLabel(ModifySysDictDataVO modifySysDictDataVO) {
        SysDictData dictData = this.getOne(Wrappers.lambdaQuery(SysDictData.class)
                .eq(SysDictData::getDictLabel, modifySysDictDataVO.getDictLabel())
                .eq(SysDictData::getIsDeleted, false));
        if (dictData != null && !modifySysDictDataVO.getDictCode().equals(dictData.getDictCode().toString())) {
            AssertUtil.isNotEmpty(dictData, SysDictDataExceptionConstant.EXIST_DICT_LABLE);
        }
    }

    /**
     * 修改
     * 字典数据-键值唯一校验
     *
     * @param modifySysDictDataVO
     */
    public void checkModifyDictDataByValue(ModifySysDictDataVO modifySysDictDataVO) {
        SysDictData sysDictData = this.getOne(Wrappers.lambdaQuery(SysDictData.class)
                .eq(SysDictData::getDictValue, modifySysDictDataVO.getDictValue())
                .eq(SysDictData::getDictType, modifySysDictDataVO.getDictType())
                .eq(SysDictData::getIsDeleted, false));
        if (sysDictData != null && !modifySysDictDataVO.getDictCode().equals(sysDictData.getDictCode().toString())) {
            AssertUtil.isNotEmpty(sysDictData, SysDictDataExceptionConstant.EXIST_DICT_VALUE);
        }
    }

    /**
     * 根据 字典数据ID 查询 SysDictData
     *
     * @param dictCode
     * @return
     */
    public SysDictData selectCacheSysDictDataById(Long dictCode) {
        // 查询: 查询缓存
        String sysDictDataInfo = redisOperator.get(SysDictDataKey.SYS_DICT_DATA_INFO_MAP + dictCode);
        // 校验: 校验缓存不为空直接返回
        if (StringUtil.isNotEmpty(sysDictDataInfo)) {
            return JSON.parseObject(sysDictDataInfo, SysDictData.class);
        }
        SysDictData sysDictData = this.getById(dictCode);
        redisOperator.set(SysDictDataKey.SYS_DICT_DATA_INFO_MAP + dictCode, JSON.toJSONString(sysDictData), 36000);
        return sysDictData;
    }

    /**
     * 查询字典数据通过类型
     *
     * @param key
     * @return
     */
    public List<SysDictData> selectDictDataByType(String key) {
        List<SysDictData> list = this.list(Wrappers.lambdaQuery(SysDictData.class)
                .eq(SysDictData::getDictType, key)
                .eq(SysDictData::getIsDeleted, false));
        return list;
    }

}
