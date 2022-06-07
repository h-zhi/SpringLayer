package com.example.layer.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.layer.sys.application.SysConfigApplicationService;
import org.springlayer.core.boot.context.CurrentUser;
import org.springlayer.core.boot.context.CurrentUserHolder;
import org.springlayer.core.tool.api.R;
import com.example.layer.sys.dto.SysConfigDTO;
import com.example.layer.sys.vo.ModifySysConfigVO;
import com.example.layer.sys.vo.SysConfigVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author Hzhi
 * @Date 2022-04-25 9:14
 * @description
 **/
@Api(tags = "SY1005-系统参数")
@RestController
@RequestMapping(value = "/sys/config")
public class SysConfigController {

    @Resource
    private SysConfigApplicationService sysConfigApplicationService;

    @ApiOperation(value = "SY1005001-创建系统参数")
    @PostMapping(value = "/create")
    public R createSysConfig(@RequestBody SysConfigVO sysConfigVO) {
        CurrentUser currentUser = CurrentUserHolder.getCurrentUser();
        return R.status(sysConfigApplicationService.createSysConfig(sysConfigVO, currentUser));
    }

    @ApiImplicitParam(name = "configId", value = "参数ID", required = true, dataType = "Long")
    @ApiOperation(value = "SY1005002-删除系统参数")
    @GetMapping(value = "/remove")
    public R removeSysConfig(@RequestParam(value = "configId", required = true) Long configId) {
        return R.status(sysConfigApplicationService.removeSysConfig(configId));
    }

    @ApiOperation(value = "SY1005003-编辑系统参数")
    @PostMapping(value = "/modify")
    public R modifySysConfig(@RequestBody ModifySysConfigVO modifySysConfigVO) {
        CurrentUser currentUser = CurrentUserHolder.getCurrentUser();
        return R.status(sysConfigApplicationService.modifySysConfig(modifySysConfigVO, currentUser));
    }

    @ApiOperation(value = "SY1005004-分页查询系统管理")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "size", value = "每页条数", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "configName", value = "系统名称", required = false, dataType = "String"),
            @ApiImplicitParam(name = "configKey", value = "系统KEY", required = false, dataType = "String")
    })
    @GetMapping(value = "/query/page")
    public R<IPage<SysConfigDTO>> querySysConfigPage(@RequestParam(value = "current", required = true, defaultValue = "1") Integer current,
                                                     @RequestParam(value = "size", required = true, defaultValue = "10") Integer size,
                                                     @RequestParam(value = "configName", required = false) String configName,
                                                     @RequestParam(value = "configKey", required = false) String configKey) {
        return R.data(sysConfigApplicationService.querySysConfigPage(current, size, configName, configKey));
    }
}