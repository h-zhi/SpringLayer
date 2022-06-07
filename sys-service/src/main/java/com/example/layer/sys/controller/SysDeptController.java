package com.example.layer.sys.controller;

import com.example.layer.sys.application.SysDeptApplicationService;
import com.example.layer.sys.engine.domain.core.SysDept;
import org.springlayer.core.boot.context.CurrentUser;
import org.springlayer.core.boot.context.CurrentUserHolder;
import org.springlayer.core.tool.api.R;
import com.example.layer.sys.dto.SysDeptDTO;
import com.example.layer.sys.vo.ModifySysDeptVO;
import com.example.layer.sys.vo.SysDeptVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Hzhi
 * @Date 2022-04-21 9:22
 * @description 系统组织管理
 **/
@Api(tags = "SY1004-组织管理")
@RestController
@RequestMapping(value = "/sys/dept")
public class SysDeptController {

    @Resource
    private SysDeptApplicationService sysDeptApplicationService;

    @ApiOperation(value = "SY1004001-查询组织树")
    @GetMapping(value = "/tree")
    public R<List<SysDept>> deptTreeData() {
        return R.data(sysDeptApplicationService.deptTreeData());
    }

    @ApiOperation(value = "SY1004002-查询组织详情")
    @GetMapping(value = "/query/detail")
    @ApiImplicitParam(name = "deptId", value = "组织ID", required = true, dataType = "Long")
    public R<SysDeptDTO> querySysDeptDetail(@RequestParam("deptId") Long deptId) {
        return R.data(sysDeptApplicationService.querySysDeptDetail(deptId));
    }

    @ApiOperation(value = "SY1004003-创建部门")
    @PostMapping(value = "/create")
    public R createSysDept(@RequestBody SysDeptVO sysDeptVO) {
        CurrentUser currentUser = CurrentUserHolder.getCurrentUser();
        return R.status(sysDeptApplicationService.createSysDept(sysDeptVO, currentUser));
    }

    @ApiOperation(value = "SY1004004-移除组织")
    @GetMapping(value = "/remove")
    @ApiImplicitParam(name = "deptId", value = "组织ID", required = true, dataType = "Long")
    public R removeSysDept(@RequestParam("deptId") Long deptId) {
        CurrentUser currentUser = CurrentUserHolder.getCurrentUser();
        return R.status(sysDeptApplicationService.removeSysDept(deptId, currentUser));
    }

    @ApiOperation(value = "SY1004005-修改组织")
    @PostMapping(value = "/modify")
    public R modifySysDept(@RequestBody ModifySysDeptVO modifySysDeptVO) {
        CurrentUser currentUser = CurrentUserHolder.getCurrentUser();
        return R.status(sysDeptApplicationService.modifySysDept(modifySysDeptVO, currentUser));
    }

    @ApiOperation(value = "SY1004006-查询组织")
    @GetMapping(value = "/list")
    public R<List<SysDeptDTO>> querySysDeptList() {
        return R.data(sysDeptApplicationService.querySysDeptList());
    }
}