package com.example.layer.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.layer.sys.application.SysUserApplicationService;
import com.example.layer.sys.engine.domain.core.SysUser;
import org.springlayer.core.boot.context.CurrentUser;
import org.springlayer.core.boot.context.CurrentUserHolder;
import org.springlayer.core.poi.EasyPoiUtil;
import org.springlayer.core.tool.api.R;
import com.example.layer.sys.dto.SysUserDTO;
import com.example.layer.sys.vo.CreateSysUserVO;
import com.example.layer.sys.vo.ModifySysUserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Author Hzhi
 * @Date 2022-04-19 9:37
 * @description 系统用户控制层
 **/
@Api(tags = "SY1001-用户管理")
@RestController
@RequestMapping(value = "/sys/user")
public class SysUserController {

    @Resource
    private SysUserApplicationService sysUserApplicationService;

    @ApiOperation(value = "SY1001001-获取当前用户")
    @GetMapping(value = "/query/current/user")
    public R<SysUserDTO> queryCurrentUser() {
        CurrentUser currentUser = CurrentUserHolder.getCurrentUser();
        return R.data(sysUserApplicationService.queryCurrentUser(currentUser.getUserId()));
    }

    @ApiOperation(value = "SY1001002-查询用户详情")
    @GetMapping(value = "/query/detail")
    @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "Long")
    public R<SysUserDTO> querySysUserDetail(@RequestParam("userId") Long userId) {
        return R.data(sysUserApplicationService.querySysUserDetail(userId));
    }

    @ApiOperation(value = "SY1001003-分页查询系统用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "size", value = "每页条数", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "username", value = "用户名称", required = false, dataType = "String"),
            @ApiImplicitParam(name = "loginName", value = "登录名称", required = false, dataType = "String"),
            @ApiImplicitParam(name = "phone", value = "手机号", required = false, dataType = "String"),
            @ApiImplicitParam(name = "deptId", value = "组织ID", required = true, dataType = "Long")
    })
    @GetMapping(value = "/query/page")
    public R<IPage<SysUserDTO>> querySysUserPage(@RequestParam(value = "current", required = true, defaultValue = "1") Integer current, @RequestParam(value = "size", required = true, defaultValue = "10") Integer size, @RequestParam(value = "username", required = false) String username,
                                                 @RequestParam(value = "loginName", required = false) String loginName, @RequestParam(value = "phone", required = false) String phone, @RequestParam(value = "deptId", required = true) Long deptId) {
        return R.data(sysUserApplicationService.querySysUserPage(current, size, username, loginName, phone, deptId));
    }

    @ApiOperation(value = "SY1001004-创建系统用户")
    @PostMapping(value = "/create")
    public R createSysUser(@RequestBody CreateSysUserVO createSysUserVO) {
        return R.status(sysUserApplicationService.createSysUser(createSysUserVO));
    }

    @ApiOperation(value = "SY1001005-编辑系统用户")
    @PostMapping(value = "/modify")
    public R modifySysUser(@RequestBody ModifySysUserVO modifySysUserVO) {
        return R.status(sysUserApplicationService.modifySysUser(modifySysUserVO));
    }

    @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "Long")
    @ApiOperation(value = "SY1001006-移除系统用户")
    @GetMapping(value = "/remove")
    public R removeSysUser(@RequestParam(value = "userId", required = true) Long userId) {
        return R.status(sysUserApplicationService.removeSysUser(userId));
    }

    @ApiOperation(value = "SY1001007-导出系统用户")
    @GetMapping(value = "/exporExcel")
    public R exporExcelUser(HttpServletResponse response){
        List<SysUser> sysUserList = sysUserApplicationService.queryUserList();
        try {
            EasyPoiUtil.exportExcel(sysUserList, "系统用户", "用户表" ,SysUser.class, "user", response);
        } catch (IOException e) {
            e.printStackTrace();
            return R.data("导出失败");
        }
        return R.data("导出成功");
    }

}
