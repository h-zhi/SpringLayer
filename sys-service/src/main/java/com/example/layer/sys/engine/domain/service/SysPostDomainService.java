package com.example.layer.sys.engine.domain.service;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springlayer.core.boot.context.CurrentUser;
import org.springlayer.core.redis.helper.RedisOperator;
import org.springlayer.core.tool.utils.AssertUtil;
import org.springlayer.core.tool.utils.StringUtil;
import com.example.layer.sys.engine.components.redis.SysPostKey;
import com.example.layer.sys.engine.domain.core.SysPost;
import com.example.layer.sys.engine.mapper.SysPostMapper;
import com.example.layer.sys.engine.service.SysUserPostService;
import com.example.layer.sys.infrastructure.exception.SysPostExceptionConstant;
import com.example.layer.sys.vo.ModifySysPostVO;
import com.example.layer.sys.vo.SysPostVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @Author Hzhi
 * @Date 2022-04-19 11:59
 * @description
 **/
@Slf4j
@Service
public class SysPostDomainService extends ServiceImpl<SysPostMapper, SysPost> {

    @Resource
    private RedisOperator redisOperator;
    @Resource
    private SysUserPostService sysUserPostService;

    /**
     * 构建岗位信息
     *
     * @param sysPostVO
     * @return SysPost
     */
    public SysPost buildSysPost(SysPostVO sysPostVO, CurrentUser currentUser) {
        return SysPost.builder()
                .postName(sysPostVO.getPostName())
                .postCode(sysPostVO.getPostCode())
                .status(sysPostVO.getStatus())
                .postSort(sysPostVO.getPostSort())
                .createBy(currentUser.getUserId())
                .createTime(LocalDateTime.now())
                .remark(sysPostVO.getRemark())
                .build();
    }

    /**
     * 构建修改岗位
     *
     * @param sysPost
     * @param modifySysPostVo
     * @param currentUser
     * @return SysPost
     */
    public SysPost buildModifyPost(SysPost sysPost, ModifySysPostVO modifySysPostVo, CurrentUser currentUser) {
        return SysPost.builder()
                .postId(sysPost.getPostId())
                .postName(modifySysPostVo.getPostName())
                .postCode(modifySysPostVo.getPostCode())
                .status(modifySysPostVo.getStatus())
                .postSort(modifySysPostVo.getPostSort())
                .createBy(sysPost.getCreateBy())
                .createTime(sysPost.getCreateTime())
                .remark(modifySysPostVo.getRemark())
                .build();
    }

    /**
     * 查询系统岗位信息通过postId
     *
     * @param postId
     * @return SysPost
     */
    public SysPost selectSysPostById(Long postId) {
        // 查询: 查询缓存
        String sysPostInfo = redisOperator.get(SysPostKey.sys_post_INFO_MAP + postId);
        // 校验: 校验缓存不为空直接返回
        if (StringUtil.isNotEmpty(sysPostInfo)) {
            return JSON.parseObject(sysPostInfo, SysPost.class);
        }
        SysPost sysPost = this.getById(postId);
        redisOperator.set(SysPostKey.sys_post_INFO_MAP + postId, JSON.toJSONString(sysPost), 36000);
        return sysPost;
    }

    /**
     * 校验创建岗位信息参数
     *
     * @param sysPostVO
     */
    public void checkCreateSysPostParam(SysPostVO sysPostVO) {
        AssertUtil.isEmpty(sysPostVO.getPostName(), SysPostExceptionConstant.NOT_POST_NAME);
        AssertUtil.isEmpty(sysPostVO.getPostCode(), SysPostExceptionConstant.NOT_POST_CODE);
        AssertUtil.isEmpty(sysPostVO.getPostSort(), SysPostExceptionConstant.NOT_POST_SORT);
        AssertUtil.isEmpty(sysPostVO.getStatus(), SysPostExceptionConstant.NOT_POST_STATUS);
    }

    /**
     * 校验编辑岗位信息参数
     *
     * @param modifySysPostVO
     */
    public void checkModifySysPostParam(ModifySysPostVO modifySysPostVO) {
        AssertUtil.isEmpty(modifySysPostVO.getPostId(), SysPostExceptionConstant.NOT_POST_POSTID);
        AssertUtil.isEmpty(modifySysPostVO.getPostName(), SysPostExceptionConstant.NOT_POST_NAME);
        AssertUtil.isEmpty(modifySysPostVO.getPostCode(), SysPostExceptionConstant.NOT_POST_CODE);
        AssertUtil.isEmpty(modifySysPostVO.getPostSort(), SysPostExceptionConstant.NOT_POST_SORT);
        AssertUtil.isEmpty(modifySysPostVO.getStatus(), SysPostExceptionConstant.NOT_POST_STATUS);
    }

    /**
     * 校验岗位下是否存在用户
     *
     * @param postId
     */
    public void checkExitsPostUser(Long postId) {
        AssertUtil.isBolEmpty(sysUserPostService.existUserByPost(postId), SysPostExceptionConstant.EXITS_POST_USER);
    }

}
