package com.example.layer.sys.engine.domain.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.layer.sys.engine.domain.core.SysUserPost;
import com.example.layer.sys.engine.mapper.SysUserPostMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Hzhi
 * @Date 2022-04-19 11:59
 * @description
 **/
@Slf4j
@Service
public class SysUserPostDomainService extends ServiceImpl<SysUserPostMapper, SysUserPost> {

    /**
     * 删除用户岗位
     *
     * @param userId
     */
    public void delSysUserPostByUserId(Long userId) {
        this.remove(Wrappers.lambdaQuery(SysUserPost.class).eq(SysUserPost::getUserId, userId));
    }

    /**
     * 查询用户通过岗位postId
     *
     * @param postId
     * @return
     */
    public List<SysUserPost> queryUserPostBypostId(long postId) {
        return this.list(Wrappers.lambdaQuery(SysUserPost.class).eq(SysUserPost::getPostId, postId));
    }

}