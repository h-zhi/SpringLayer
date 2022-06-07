package com.example.layer.sys.engine.domain.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springlayer.core.boot.context.CurrentUser;
import org.springlayer.core.redis.helper.RedisOperator;
import org.springlayer.core.tool.utils.AssertUtil;
import org.springlayer.core.tool.utils.StringUtil;
import com.example.layer.sys.engine.components.redis.SysDictTypeKey;
import com.example.layer.sys.engine.domain.core.SysDictType;
import com.example.layer.sys.engine.mapper.SysDictTypeMapper;
import com.example.layer.sys.infrastructure.exception.SysDictTypeExceptionConstant;
import com.example.layer.sys.vo.ModifySysDictTypeVO;
import com.example.layer.sys.vo.SysDictTypeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @Author zhaoyl
 * @Date 2022-04-23
 * @description
 **/
@Slf4j
@Service
public class SysDictTypeDomainService extends ServiceImpl<SysDictTypeMapper, SysDictType> {

    @Resource
    private RedisOperator redisOperator;

    /**
     * 校验字典类型是否为空
     *
     * @param sysDictTypeVO
     */
    public void checkCreatIFNotDictTypeParam(SysDictTypeVO sysDictTypeVO) {
        AssertUtil.isEmpty(sysDictTypeVO.getDictName(), SysDictTypeExceptionConstant.NOT_DICT_NAME);
        AssertUtil.isEmpty(sysDictTypeVO.getDictType(), SysDictTypeExceptionConstant.NOT_DICT_TYPE);
    }

    /**
     * 校验字典类型是否为空
     *
     * @param modifySysDictTypeVO
     */
    public void checkModifyIFNotDictTypeParam(ModifySysDictTypeVO modifySysDictTypeVO) {
        AssertUtil.isEmpty(modifySysDictTypeVO.getDictId(), SysDictTypeExceptionConstant.NOT_DICT_ID);
        SysDictType sysDictType = this.getById(Long.valueOf(modifySysDictTypeVO.getDictId()));
        AssertUtil.isEmpty(sysDictType, SysDictTypeExceptionConstant.NOT_EXIST);
        this.checkCreatIFNotDictTypeParam(modifySysDictTypeVO);
    }


    /**
     * 判断字典名称是否存在
     *
     * @param dictName
     */
    public void checkCreatDictTypeByName(String dictName) {
        SysDictType sysDictType = this.getOne(Wrappers.lambdaQuery(SysDictType.class)
                .eq(SysDictType::getDictName, dictName)
                .eq(SysDictType::getIsDeleted, false));
        AssertUtil.isNotEmpty(sysDictType, SysDictTypeExceptionConstant.EXIST_DICT_NAME);
    }

    /**
     * 判断字典类型是否存在
     *
     * @param dictType
     */
    public void checkCreatDictTypeByType(String dictType) {
        SysDictType sysDictType = this.getOne(Wrappers.lambdaQuery(SysDictType.class)
                .eq(SysDictType::getDictType, dictType)
                .eq(SysDictType::getIsDeleted, false));
        AssertUtil.isNotEmpty(sysDictType, SysDictTypeExceptionConstant.EXIST_DICT_TYPE);
    }


    /**
     * 修改:
     * 判断字典名称是否存在
     *
     * @param modifySysDictTypeVO
     */
    public void checkModifyDictTypeByName(ModifySysDictTypeVO modifySysDictTypeVO) {

        SysDictType sysDictType = this.getOne(Wrappers.lambdaQuery(SysDictType.class)
                .eq(SysDictType::getDictName, modifySysDictTypeVO.getDictName())
                .eq(SysDictType::getIsDeleted, false));
        if (sysDictType != null && !modifySysDictTypeVO.getDictId().equals(sysDictType.getDictId().toString())) {
            AssertUtil.isNotEmpty(sysDictType, SysDictTypeExceptionConstant.EXIST_DICT_NAME);
        }
    }

    /**
     * 修改:
     * 判断字典类型是否存在
     *
     * @param modifySysDictTypeVO
     */
    public void checkModifyDictTypeByType(ModifySysDictTypeVO modifySysDictTypeVO) {
        SysDictType sysDictType = this.getOne(Wrappers.lambdaQuery(SysDictType.class)
                .eq(SysDictType::getDictType, modifySysDictTypeVO.getDictType())
                .eq(SysDictType::getIsDeleted, false));
        if (sysDictType != null && !modifySysDictTypeVO.getDictId().equals(sysDictType.getDictId().toString())) {
            AssertUtil.isNotEmpty(sysDictType, SysDictTypeExceptionConstant.EXIST_DICT_TYPE);
        }
    }


    /**
     * 构建新增字典类型
     *
     * @param sysDictTypeVO
     * @return SysDictType
     */
    public SysDictType buildSysDictTypeVO(SysDictTypeVO sysDictTypeVO, CurrentUser currentUser) {
        return SysDictType.builder()
                .dictName(sysDictTypeVO.getDictName())
                .dictType(sysDictTypeVO.getDictType())
                .isDeleted(false)
                .status(0)
                .remark(sysDictTypeVO.getRemark())
                .createBy(currentUser.getUserId())
                .createTime(LocalDateTime.now())
                .build();
    }

    /**
     * 构建修改字典类型
     *
     * @param modifySysDictTypeVO
     * @return SysDictType
     */
    public SysDictType buildModifySysDictType(ModifySysDictTypeVO modifySysDictTypeVO, CurrentUser currentUser) {
        return SysDictType.builder()
                .dictId(Long.parseLong(modifySysDictTypeVO.getDictId()))
                .dictName(modifySysDictTypeVO.getDictName())
                .dictType(modifySysDictTypeVO.getDictType())
                .isDeleted(false)
                .remark(modifySysDictTypeVO.getRemark())
                .updateBy(currentUser.getUserId())
                .updateTime(LocalDateTime.now())
                .build();
    }

    /**
     * 查询字典类型
     *
     * @param dictId
     * @return SysDictType
     */
    public SysDictType selectCacheSysDataTypeById(Long dictId) {
        // 查询: 查询缓存
        String sysDictTypeInfo = redisOperator.get(SysDictTypeKey.SYS_DICT_TYPE_INFO_MAP + dictId);
        // 校验: 校验缓存不为空直接返回
        if (StringUtil.isNotEmpty(sysDictTypeInfo)) {
            return JSON.parseObject(sysDictTypeInfo, SysDictType.class);
        }
        SysDictType sysDictType = this.getById(dictId);
        redisOperator.set(SysDictTypeKey.SYS_DICT_TYPE_INFO_MAP + dictId, JSON.toJSONString(sysDictType), 36000);
        return sysDictType;
    }

}
