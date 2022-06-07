package com.example.layer.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.layer.sys.application.SysDictDataApplicationService;
import org.springlayer.core.boot.context.CurrentUser;
import org.springlayer.core.boot.context.CurrentUserHolder;
import org.springlayer.core.tool.api.R;
import com.example.layer.sys.dto.SysDictDataDTO;
import com.example.layer.sys.vo.ModifySysDictDataVO;
import com.example.layer.sys.vo.SysDictDataVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author zhaoyl
 * @Date 2022-04-23
 * @description 字典数据控制层
 **/
@Api(tags = "SY1008-字典数据")
@RestController
@RequestMapping(value = "/sys/dict/data")
public class SysDictDataController {

    @Resource
    private SysDictDataApplicationService sysDictDataApplicationService;

    @ApiOperation(value = "SY1008001-创建字典数据")
    @PostMapping(value = "/create")
    public R createSysDictData(@RequestBody SysDictDataVO sysDictDataVO) {
        CurrentUser currentUser = CurrentUserHolder.getCurrentUser();
        return R.status(sysDictDataApplicationService.createDictData(sysDictDataVO, currentUser));
    }

    @ApiOperation(value = "SY1008002-编辑字典数据")
    @PostMapping(value = "/modify")
    public R modifySysDictData(@RequestBody ModifySysDictDataVO modifySysDictDataVO) {
        CurrentUser currentUser = CurrentUserHolder.getCurrentUser();
        return R.status(sysDictDataApplicationService.modifySysDictData(modifySysDictDataVO, currentUser));
    }

    @ApiOperation(value = "SY1008003-查询字典数据详情")
    @GetMapping(value = "/query/detail")
    @ApiImplicitParam(name = "dictCode", value = "字典数据ID", required = true, dataType = "Long")
    public R<SysDictDataDTO> querySysDictTypeDetail(@RequestParam("dictCode") Long dictCode) {
        return R.data(sysDictDataApplicationService.querySysDictDataDetail(dictCode));
    }

    @ApiImplicitParam(name = "dictCode", value = "字典类型ID", required = true, dataType = "Long")
    @ApiOperation(value = "SY1008004-移除字典数据")
    @GetMapping(value = "/remove")
    public R removeSysDictType(@RequestParam(value = "dictCode", required = true) Long dictCode) {
        return R.status(sysDictDataApplicationService.removeSysDictData(dictCode));
    }

    @ApiOperation(value = "SY1008005-分页查询字典数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "size", value = "每页条数", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "dictLabel", value = "字典标签", required = false, dataType = "String"),
            @ApiImplicitParam(name = "dictValue", value = "字典键值", required = false, dataType = "String"),
            @ApiImplicitParam(name = "dictType", value = "字典类型", required = false, dataType = "Integer"),
            @ApiImplicitParam(name = "status", value = "状态", required = false, dataType = "Integer"),

    })
    @GetMapping(value = "/query/page")
    public R<IPage<SysDictDataDTO>> querySysDictDataPage(@RequestParam(value = "current", required = true, defaultValue = "1") Integer current,
                                                         @RequestParam(value = "size", required = true, defaultValue = "10") Integer size,
                                                         @RequestParam(value = "dictLabel", required = false) String dictLabel,
                                                         @RequestParam(value = "dictValue", required = false) String dictValue,
                                                         @RequestParam(value = "dictType", required = false) String dictType,
                                                         @RequestParam(value = "status", required = false) Integer status) {
        return R.data(sysDictDataApplicationService.querySysDictDataPage(current, size, dictLabel, dictValue, dictType, status));
    }
}