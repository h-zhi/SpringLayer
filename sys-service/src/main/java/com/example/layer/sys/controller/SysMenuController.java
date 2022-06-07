package com.example.layer.sys.controller;

import com.example.layer.sys.application.SysMenuApplicationService;
import com.example.layer.sys.engine.domain.core.SysMenu;
import org.springlayer.core.boot.context.CurrentUser;
import org.springlayer.core.boot.context.CurrentUserHolder;
import org.springlayer.core.tool.api.R;
import com.example.layer.sys.dto.SysMenuDTO;
import com.example.layer.sys.vo.ModifySysMenuVO;
import com.example.layer.sys.vo.SysMenuVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author zhaoyl
 * @Date 2022-04-28
 * @description 系统菜单控制层
 **/
@Api(tags = "SY1011-菜单管理")
@RestController
@RequestMapping(value = "/sys/menu")
public class SysMenuController {

    @Resource
    private SysMenuApplicationService sysMenuApplicationService;

    @ApiOperation(value = "SY1011001-创建系统菜单")
    @PostMapping(value = "/create")
    public R createSysMenu(@RequestBody SysMenuVO sysMenuVO) {
        CurrentUser currentUser = CurrentUserHolder.getCurrentUser();
        return R.status(sysMenuApplicationService.createSysMenu(sysMenuVO, currentUser));
    }

    @ApiOperation(value = "SY1011002-编辑系统菜单")
    @PostMapping(value = "/modify")
    public R modifySysMenu(@RequestBody ModifySysMenuVO modifySysMenuVO, CurrentUser currentUser) {
        return R.status(sysMenuApplicationService.modifySysMenu(modifySysMenuVO, currentUser));
    }

    @ApiOperation(value = "SY1011003-查询系统菜单")
    @GetMapping(value = "/list")
    public R<List<SysMenuDTO>> querySysMenuList() {
        return R.data(sysMenuApplicationService.querySysMenuList());
    }

    @ApiOperation(value = "SY1011004-移除系统菜单")
    @GetMapping(value = "/remove")
    @ApiImplicitParam(name = "menuId", value = "菜单ID", required = true, dataType = "Long")
    public R removeSysMenu(@RequestParam("menuId") Long menuId) {
        return R.status(sysMenuApplicationService.removeSysMenu(menuId));
    }

    @ApiOperation(value = "SY1011005-查询菜单树")
    @GetMapping(value = "/tree")
    public R<List<SysMenu>> menuTreeData() {
        return R.data(sysMenuApplicationService.menuTreeData());
    }
}
