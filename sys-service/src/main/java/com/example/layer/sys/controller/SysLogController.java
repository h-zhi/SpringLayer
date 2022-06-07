package com.example.layer.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.layer.sys.application.SysLogApplicationService;
import org.springlayer.core.tool.api.R;
import com.example.layer.sys.dto.SysApiLogDTO;
import com.example.layer.sys.dto.SysLogininforDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author Hzhi
 * @Date 2022-04-25 11:38
 * @description
 **/
@Api(tags = "SY1006-日志管理")
@RestController
@RequestMapping(value = "/sys/log")
public class SysLogController {

    @Resource
    private SysLogApplicationService sysLogApplicationService;

    @ApiOperation(value = "SY1006001-分页查询登录日志")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "size", value = "每页条数", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "loginName", value = "登录名称", required = false, dataType = "String")
    })
    @GetMapping(value = "/query/login/info/page")
    public R<IPage<SysLogininforDTO>> querySysLoginInfoPage(@RequestParam(value = "current", required = true, defaultValue = "1") Integer current,
                                                            @RequestParam(value = "size", required = true, defaultValue = "10") Integer size,
                                                            @RequestParam(value = "loginName", required = false) String loginName) {
        return R.data(sysLogApplicationService.querySysLoginInfoPage(current, size, loginName));
    }

    @ApiOperation(value = "SY1006002-分页查询操作日志")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "size", value = "每页条数", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "title", value = "标题模块", required = false, dataType = "String")
    })
    @GetMapping(value = "/query/api/page")
    public R<IPage<SysApiLogDTO>> querySysApiPage(@RequestParam(value = "current", required = true, defaultValue = "1") Integer current,
                                                  @RequestParam(value = "size", required = true, defaultValue = "10") Integer size,
                                                  @RequestParam(value = "title", required = false) String title) {
        return R.data(sysLogApplicationService.querySysApiPage(current, size, title));
    }
}