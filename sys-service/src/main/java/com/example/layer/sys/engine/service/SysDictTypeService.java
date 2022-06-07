package com.example.layer.sys.engine.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.layer.sys.engine.domain.core.SysDictType;
import com.example.layer.sys.engine.domain.service.SysDictTypeDomainService;
import org.springlayer.core.boot.context.CurrentUser;
import org.springlayer.core.mp.support.Condition;
import org.springlayer.core.redis.helper.RedisOperator;
import org.springlayer.core.tool.utils.StringUtil;
import com.example.layer.sys.dto.SysDictTypeDTO;
import com.example.layer.sys.engine.components.redis.SysDictTypeKey;
import com.example.layer.sys.engine.domain.convertot.SysDictTypeConvertor;
import com.example.layer.sys.engine.mapper.SysDictTypeMapper;
import com.example.layer.sys.vo.ModifySysDictTypeVO;
import com.example.layer.sys.vo.SysDictTypeVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author zhaoyl
 * @Date 2022-04-21
 * @description
 **/
@Service
public class SysDictTypeService {
    @Resource
    private RedisOperator redisOperator;
    @Resource
    private SysDictTypeDomainService sysDictTypeDomainService;
    @Resource
    private SysDictTypeMapper sysDictTypeMapper;

    /**
     * 保存字典类型
     *
     * @param sysDictTypeVO
     * @param currentUser
     * @return
     */
    public boolean savaSysDictType(SysDictTypeVO sysDictTypeVO, CurrentUser currentUser) {
        SysDictType sysDictType = sysDictTypeDomainService.buildSysDictTypeVO(sysDictTypeVO, currentUser);
        return sysDictTypeDomainService.save(sysDictType);
    }

    /**
     * 修改字典类型
     *
     * @param modifySysDictTypeVO
     * @param currentUser
     * @return
     */
    public Boolean ModifySysDictType(ModifySysDictTypeVO modifySysDictTypeVO, CurrentUser currentUser) {
        SysDictType sysDictType = sysDictTypeDomainService.buildModifySysDictType(modifySysDictTypeVO, currentUser);
        return sysDictTypeDomainService.updateById(sysDictType);
    }

    /**
     * 查询字典类型
     *
     * @param dictId
     * @return
     */
    public SysDictTypeDTO queryDetail(Long dictId) {
        SysDictType sysDictType = sysDictTypeDomainService.selectCacheSysDataTypeById(dictId);
        if (sysDictType==null){
            return null;
        }
        return SysDictTypeConvertor.convertToSysDictTypeDTO(sysDictType);
    }

    /**
     * 删除字典类型
     *
     * @param dictId
     * @return
     */
    public Boolean removeDicType(Long dictId) {
        redisOperator.del(SysDictTypeKey.SYS_DICT_TYPE_INFO_MAP);
        SysDictType sysDicType = sysDictTypeDomainService.getById(dictId);
        sysDicType.setIsDeleted(true);
        return sysDictTypeDomainService.updateById(sysDicType);
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
        Page page = new Page(current, size);
        SysDictType sysDictType = SysDictType.builder().build();
        QueryWrapper<SysDictType> queryWrapper = Condition.getQueryWrapper(sysDictType);
        if (StringUtil.isNotBlank(dictName)) {
            queryWrapper.lambda().like(SysDictType::getDictName, dictName);
        }
        if (StringUtil.isNotBlank(dictType)) {
            queryWrapper.lambda().eq(SysDictType::getDictType, dictType);
        }
        if (!StringUtil.isEmpty(status)) {
            queryWrapper.lambda().eq(SysDictType::getStatus, status);
        }
        queryWrapper.lambda().eq(SysDictType::getIsDeleted, false);
        Page<SysDictType> sysDictTypePage = sysDictTypeMapper.selectPage(page, queryWrapper);
        IPage<SysDictTypeDTO> sysDictTypeDTOIPage = convertToSysDictTypeDTOPage(sysDictTypePage);
        return sysDictTypeDTOIPage;
    }

    /**
     * 转换字典类型分页数据
     *
     * @param sysDictTypePage
     * @return
     */
    private IPage<SysDictTypeDTO> convertToSysDictTypeDTOPage(Page<SysDictType> sysDictTypePage) {
        List<SysDictType> records = sysDictTypePage.getRecords();
        if (StringUtil.isEmpty(records)) {
            return new Page<>(sysDictTypePage.getCurrent(), sysDictTypePage.getSize(), sysDictTypePage.getTotal());
        }
        List<SysDictTypeDTO> list = SysDictTypeConvertor.convertToSysDictTypeDTOList(records);
        IPage<SysDictTypeDTO> pageVo = new Page<>(sysDictTypePage.getCurrent(), sysDictTypePage.getSize(), sysDictTypePage.getTotal());
        pageVo.setRecords(list);
        return pageVo;
    }
}

