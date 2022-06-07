package com.example.layer.sys.controller;

import com.example.layer.sys.application.SysAclModuleMenuApplication;
import com.example.layer.sys.engine.domain.core.SysMenu;
import org.springlayer.core.tool.api.R;
import com.example.layer.sys.dto.SysMenuDTO;
import com.example.layer.sys.vo.CreateSysAclModuleMenuVO;
import com.example.layer.sys.vo.ModifySysAclModuleMenuVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author zhaoyl
 * @Date 2022-04-27
 * @description 权限模块菜单
 **/
@Api(tags = "SY1009-权限管理")
@RestController
@RequestMapping(value = "/sys/acl/module/menu")
public class SysAclModuleMenuController {
    @Resource
    private SysAclModuleMenuApplication sysAclModuleMenuApplication;

    @ApiOperation(value = "SY1009005-添加菜单")
    @PostMapping(value = "/create")
    public R createSysAclModuleMenu(@RequestBody CreateSysAclModuleMenuVO createSysAclModuleMenuVO) {
        return R.status(sysAclModuleMenuApplication.createAclModuleMenu(createSysAclModuleMenuVO));
    }

    @ApiOperation(value = "SY1009006-编辑菜单")
    @PostMapping(value = "/modify")
    public R modifyAclModuleMenu(@RequestBody ModifySysAclModuleMenuVO modifySysAclModuleMenuVO) {
        return R.status(sysAclModuleMenuApplication.modifyAclModuleMenu(modifySysAclModuleMenuVO));
    }

    @ApiOperation(value = "SY1009007-查询模块权限")
    @GetMapping(value = "/query")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sourceId", value = "资源ID", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "sourceModule", value = "资源模块", required = true, dataType = "String", example = "DEPT,ROLE"),
    })
    public R<List<SysMenu>> queryAclModuleMenuList(@RequestParam("sourceId") Long sourceId, @RequestParam("sourceModule") String sourceModule) {
        return R.data(sysAclModuleMenuApplication.queryAclModuleMenuList(sourceId, sourceModule));
    }

    @ApiOperation(value = "SY1009008-查询模块权限树")
    @GetMapping(value = "/query/tree")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sourceId", value = "资源ID", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "sourceModule", value = "资源模块", required = true, dataType = "String", example = "DEPT,ROLE"),
    })
    public R<List<SysMenu>> queryAclModuleMenuTree(@RequestParam("sourceId") Long sourceId, @RequestParam("sourceModule") String sourceModule) {
        return R.data(sysAclModuleMenuApplication.queryAclModuleMenuTree(sourceId, sourceModule));
    }

    @ApiOperation(value = "SY1009009-查询用户权限菜单")
    @GetMapping(value = "/user/menu")
    @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "Long")
    public R<List<SysMenuDTO>> queryMenuListByUser(@RequestParam("userId") Long userId) {
        return R.data(sysAclModuleMenuApplication.userMenuList(userId));
    }

    @ApiOperation(value = "SY1009010-查询用户权限菜单树")
    @GetMapping(value = "/user/menu/tree")
    @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "Long")
    public R<List<SysMenu>> queryMenuTreeByUser(@RequestParam("userId") Long userId) {
        return R.data(sysAclModuleMenuApplication.userMenuTreeData(userId));
    }
}
