package com.example.layer.sys.controller;

import com.example.layer.sys.application.SysAclModuleApplicationService;
import org.springlayer.core.tool.api.R;
import com.example.layer.sys.vo.ModifySysAclModuleVO;
import com.example.layer.sys.vo.SysAclModuleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author zhaoyl
 * @Date 2022-04-26
 * @description 权限管理
 **/
@Api(tags = "SY1009-权限管理")
@RestController
@RequestMapping(value = "/sys/aclmodule")
public class SysAclModuleController {
    @Resource
    private SysAclModuleApplicationService sysAclModuleApplicationService;

    @ApiOperation(value = "SY1009001-创建模块权限")
    @PostMapping(value = "/create")
    public R createSysAclModule(@RequestBody SysAclModuleVO sysAclModuleVO) {
        return R.status(sysAclModuleApplicationService.createAclModule(sysAclModuleVO));
    }

    @ApiOperation(value = "SY1009002-编辑模块权限")
    @PostMapping(value = "/modify")
    public R modifySysAclModule(@RequestBody ModifySysAclModuleVO modifySysAclModuleVO) {
        return R.status(sysAclModuleApplicationService.modifyAclModule(modifySysAclModuleVO));
    }

    @ApiImplicitParam(name = "aclModuleId", value = "ID", required = true, dataType = "Long")
    @ApiOperation(value = "SY1009003-移除模块权限")
    @GetMapping(value = "/remove")
    public R removeSysAclModule(@RequestParam(value = "aclModuleId", required = true) Long aclModuleId) {
        return R.status(sysAclModuleApplicationService.removeAclModule(aclModuleId));
    }

    @ApiImplicitParam(name = "sourceId", value = "ID", required = true, dataType = "Long")
    @ApiOperation(value = "SY1009004-根据来源移除模块权限")
    @GetMapping(value = "/remove/source")
    public R removeSysAclModuleBySource(@RequestParam(value = "sourceId", required = true) Long sourceId) {
        return R.status(sysAclModuleApplicationService.removeAclModule(sourceId));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "状态值", dataType = "int", example = "0"),
            @ApiImplicitParam(name = "aclModuleId", value = "模块ID", dataType = "long")})
    @ApiOperation(value = "SY1009010-启用或停用")
    @GetMapping(value = "/update/status")
    public R updateSysAclModuleStatus(@RequestParam(value = "status", required = true) Integer status,
                                      @RequestParam(value = "aclModuleId", required = true) Long aclModuleId) {
        return R.status(sysAclModuleApplicationService.updateAclModuleStatus(aclModuleId, status));
    }
}