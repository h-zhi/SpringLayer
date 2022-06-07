package com.example.layer.sys.engine.service;

import com.example.layer.sys.engine.domain.core.SysUserPost;
import com.example.layer.sys.engine.domain.service.SysUserPostDomainService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Hzhi
 * @Date 2022-04-19 12:00
 * @description
 **/
@Service
public class SysUserPostService {

    @Resource
    private SysUserPostDomainService sysUserPostDomainService;

    /**
     * 保存系统用户岗位
     *
     * @param postIds 岗位ID集合
     * @param userId  用户ID
     * @return boolean
     */
    public boolean savaSysUserPost(List<String> postIds, Long userId) {
        List<SysUserPost> sysUserPosts = new ArrayList<>();
        postIds.stream().forEach(postId -> {
            sysUserPosts.add(new SysUserPost(Long.valueOf(postId), userId));
        });
        return sysUserPostDomainService.saveBatch(sysUserPosts);
    }

    /**
     * 修改系统用户岗位
     *
     * @param userId  用户ID
     * @param postIds 岗位ID集合
     * @return boolean
     */
    public boolean modifySysUserPost(Long userId, List<String> postIds) {
        // 删除用户岗位关系
        sysUserPostDomainService.delSysUserPostByUserId(userId);
        // 批量保存系统用户岗位
        return this.savaSysUserPost(postIds, userId);
    }

    /**
     * 根据岗位postId判断是否存在用户
     * @param postId
     * @return 存在true
     */
    public boolean existUserByPost(long postId){
        List<SysUserPost> sysUserPosts = sysUserPostDomainService.queryUserPostBypostId(postId);
        if (sysUserPosts.size()>0){
            return true;
        }
        return false;
    }


}