package com.example.layer.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.layer.sys.application.SysRoleApplicationService;
import org.springlayer.core.boot.context.CurrentUser;
import org.springlayer.core.boot.context.CurrentUserHolder;
import org.springlayer.core.tool.api.R;
import com.example.layer.sys.dto.SysRoleDTO;
import com.example.layer.sys.vo.ModifySysRoleVO;
import com.example.layer.sys.vo.SysRoleDeptVO;
import com.example.layer.sys.vo.SysRoleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author zhaoyl
 * @Date 2022-04-21
 * @description 系统角色控制层
 **/
@Api(tags = "SY1002-角色管理")
@RestController
@RequestMapping(value = "/sys/role")
public class SysRoleController {

    @Resource
    private SysRoleApplicationService sysRoleApplicationService;

    @ApiOperation(value = "SY1002001-创建角色信息")
    @PostMapping(value = "/create")
    public R createSysPost(@RequestBody SysRoleVO sysRoleVo) {
        CurrentUser currentUser = CurrentUserHolder.getCurrentUser();
        return R.status(sysRoleApplicationService.createSysRole(sysRoleVo, currentUser));
    }

    @ApiOperation(value = "SY1002002-查询角色详情")
    @GetMapping(value = "/query/detail")
    @ApiImplicitParam(name = "roleId", value = "角色ID", required = true, dataType = "Long")
    public R<SysRoleDTO> querySysUserDetail(@RequestParam("roleId") Long roleId) {
        return R.data(sysRoleApplicationService.querySysRoleDetail(roleId));
    }

    @ApiOperation(value = "SY1002003-编辑角色信息")
    @PostMapping(value = "/modify")
    public R modifySysRole(@RequestBody ModifySysRoleVO modifySysRoleVO) {
        CurrentUser currentUser = CurrentUserHolder.getCurrentUser();
        return R.status(sysRoleApplicationService.modifySysRole(modifySysRoleVO, currentUser));
    }

    @ApiOperation(value = "SY1002004-移除角色")
    @ApiImplicitParam(name = "roleId", value = "角色ID", required = true, dataType = "Long")
    @GetMapping(value = "/remove")
    public R removeSysRole(@RequestParam(value = "roleId", required = true) Long roleId) {
        return R.status(sysRoleApplicationService.removeSysRole(roleId));
    }

    @ApiOperation(value = "SY1002005-分页查询角色信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "size", value = "每页条数", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "roleName", value = "角色名称", required = false, dataType = "String"),
            @ApiImplicitParam(name = "category", value = "来源组", required = false, dataType = "String"),
            @ApiImplicitParam(name = "status", value = "角色状态", required = false, dataType = "Integer"),
    })
    @GetMapping(value = "/query/page")
    public R<IPage<SysRoleDTO>> querySysUserPage(@RequestParam(value = "current", required = true, defaultValue = "1") Integer current,
                                                 @RequestParam(value = "size", required = true, defaultValue = "10") Integer size,
                                                 @RequestParam(value = "roleName", required = false) String roleName,
                                                 @RequestParam(value = "category", required = false) String category,
                                                 @RequestParam(value = "status", required = false) Integer status) {
        return R.data(sysRoleApplicationService.querySysRolePage(current, size, roleName, category, status));
    }
    @ApiOperation(value = "SY1002006-保存角色部门信息")
    @PostMapping(value = "/saveRoleDept")
    public R saveRoleDept(SysRoleDeptVO sysRoleDeptVO){
        return R.status(sysRoleApplicationService.saveRoleDept(sysRoleDeptVO));
    }

}
