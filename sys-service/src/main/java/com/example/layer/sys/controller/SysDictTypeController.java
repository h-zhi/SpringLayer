package com.example.layer.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.layer.sys.application.SysDictTypeApplicationService;
import org.springlayer.core.boot.context.CurrentUser;
import org.springlayer.core.boot.context.CurrentUserHolder;
import org.springlayer.core.tool.api.R;
import com.example.layer.sys.dto.SysDictTypeDTO;
import com.example.layer.sys.vo.ModifySysDictTypeVO;
import com.example.layer.sys.vo.SysDictTypeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author zhaoyl
 * @Date 2022-04-23
 * @description 字典类型控制层
 **/
@Api(tags = "SY1007-字典类型")
@RestController
@RequestMapping(value = "/sys/dict/type")
public class SysDictTypeController {
    @Resource
    private SysDictTypeApplicationService sysDictTypeApplicationService;

    @ApiOperation(value = "SY1007001-创建字典类型")
    @PostMapping(value = "/create")
    public R createSysDictType(@RequestBody SysDictTypeVO sysDictTypeVO) {
        CurrentUser currentUser = CurrentUserHolder.getCurrentUser();
        return R.status(sysDictTypeApplicationService.createDictType(sysDictTypeVO, currentUser));
    }

    @ApiOperation(value = "SY1007002-编辑字典信息")
    @PostMapping(value = "/modify")
    public R modifySysDictType(@RequestBody ModifySysDictTypeVO modifySysDictTypeVO) {
        CurrentUser currentUser = CurrentUserHolder.getCurrentUser();
        return R.status(sysDictTypeApplicationService.modifySysDictType(modifySysDictTypeVO, currentUser));
    }

    @ApiOperation(value = "SY1007003-查询字典详情")
    @GetMapping(value = "/query/detail")
    @ApiImplicitParam(name = "dictId", value = "字典类型ID", required = true, dataType = "Long")
    public R<SysDictTypeDTO> querySysDictTypeDetail(@RequestParam("dictId") Long dictId) {
        return R.data(sysDictTypeApplicationService.querySysDictTypeDetail(dictId));
    }

    @ApiImplicitParam(name = "dictId", value = "字典类型ID", required = true, dataType = "Long")
    @ApiOperation(value = "SY1007004-移除字典类型")
    @GetMapping(value = "/remove")
    public R removeSysDictType(@RequestParam(value = "dictId", required = true) Long dictId) {
        return R.status(sysDictTypeApplicationService.removeSysDictType(dictId));
    }

    @ApiOperation(value = "SY1007005-分页查询字典类型")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "size", value = "每页条数", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "dictName", value = "字典名称", required = false, dataType = "String"),
            @ApiImplicitParam(name = "dictType", value = "字典类型", required = false, dataType = "String"),
            @ApiImplicitParam(name = "status", value = "状态", required = false, dataType = "Integer"),
    })
    @GetMapping(value = "/query/page")
    public R<IPage<SysDictTypeDTO>> querySysDataTypePage(@RequestParam(value = "current", required = true, defaultValue = "1") Integer current,
                                                         @RequestParam(value = "size", required = true, defaultValue = "10") Integer size,
                                                         @RequestParam(value = "dictName", required = false) String dictName,
                                                         @RequestParam(value = "dictType", required = false) String dictType,
                                                         @RequestParam(value = "status", required = false) Integer status) {
        return R.data(sysDictTypeApplicationService.querySysDictTypePage(current, size, dictName, dictType, status));
    }
}