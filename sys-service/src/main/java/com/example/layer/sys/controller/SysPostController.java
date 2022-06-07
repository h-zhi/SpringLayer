package com.example.layer.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.layer.sys.application.SysPostApplicationService;
import org.springlayer.core.boot.context.CurrentUser;
import org.springlayer.core.boot.context.CurrentUserHolder;
import org.springlayer.core.tool.api.R;
import com.example.layer.sys.dto.SysPostDTO;
import com.example.layer.sys.vo.ModifySysPostVO;
import com.example.layer.sys.vo.SysPostVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author zhaoyl
 * @Date 2022-04-20
 * @description 岗位信息控制层
 **/
@Api(tags = "SY1003-岗位管理")
@RestController
@RequestMapping(value = "/sys/post")
public class SysPostController {

    @Resource
    private SysPostApplicationService sysPostApplicationService;

    @ApiOperation(value = "SY1003001-创建岗位信息")
    @PostMapping(value = "/create")
    public R createSysPost(@RequestBody SysPostVO sysPostVO) {
        CurrentUser currentUser = CurrentUserHolder.getCurrentUser();
        return R.status(sysPostApplicationService.createSysPost(sysPostVO, currentUser));
    }

    @ApiImplicitParam(name = "postId", value = "岗位ID", required = true, dataType = "Long")
    @ApiOperation(value = "SY1003002-删除岗位信息")
    @GetMapping(value = "/remove")
    public R removeSysUser(@RequestParam(value = "postId", required = true) Long postId) {
        return R.status(sysPostApplicationService.deleteSysPost(postId));
    }

    @ApiOperation(value = "SY1003003-编辑岗位信息")
    @PostMapping(value = "/modify")
    public R modifySysUser(@RequestBody ModifySysPostVO modifySysPostVO) {
        CurrentUser currentUser = CurrentUserHolder.getCurrentUser();
        return R.status(sysPostApplicationService.modifySysPost(modifySysPostVO, currentUser));
    }

    @ApiOperation(value = "SY1003004-查询岗位详情")
    @GetMapping(value = "/query/detail")
    @ApiImplicitParam(name = "postId", value = "岗位ID", required = true, dataType = "Long")
    public R<SysPostDTO> querySysUserDetail(@RequestParam("postId") Long postId) {
        return R.data(sysPostApplicationService.queryCurrentPost(postId));
    }

    @ApiOperation(value = "SY1003005-分页查询岗位信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "size", value = "每页条数", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "postName", value = "岗位名称", required = false, dataType = "String"),
            @ApiImplicitParam(name = "postCode", value = "岗位编码", required = false, dataType = "String"),
            @ApiImplicitParam(name = "status", value = "岗位状态", required = false, dataType = "Integer"),
    })
    @GetMapping(value = "/query/page")
    public R<IPage<SysPostDTO>> querySysUserPage(@RequestParam(value = "current", required = true, defaultValue = "1") Integer current,
                                                 @RequestParam(value = "size", required = true, defaultValue = "10") Integer size,
                                                 @RequestParam(value = "postName", required = false) String postName,
                                                 @RequestParam(value = "postCode", required = false) String postCode,
                                                 @RequestParam(value = "status", required = false) Integer status) {
        return R.data(sysPostApplicationService.querySysPostPage(current, size, postName, postCode, status));
    }
}
