package com.example.layer.sys.engine.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.layer.sys.engine.domain.core.SysPost;
import com.example.layer.sys.engine.domain.service.SysPostDomainService;
import org.springlayer.core.boot.context.CurrentUser;
import org.springlayer.core.mp.support.Condition;
import org.springlayer.core.redis.helper.RedisOperator;
import org.springlayer.core.tool.utils.StringUtil;
import com.example.layer.sys.dto.SysPostDTO;
import com.example.layer.sys.engine.components.redis.SysPostKey;
import com.example.layer.sys.engine.domain.convertot.SysPostConvertor;
import com.example.layer.sys.engine.mapper.SysPostMapper;
import com.example.layer.sys.vo.ModifySysPostVO;
import com.example.layer.sys.vo.SysPostVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Hzhi
 * @Date 2022-04-19 12:00
 * @description
 **/
@Service
public class SysPostService {
    @Resource
    private SysPostDomainService postDomainService;
    @Resource
    private RedisOperator redisOperator;
    @Resource
    private SysPostMapper sysPostMapper;

    /**
     * 新增系统岗位信息
     */
    public Boolean savaSysPost(SysPostVO sysPostVO, CurrentUser currentUser) {
        SysPost sysPost = postDomainService.buildSysPost(sysPostVO, currentUser);
        return postDomainService.save(sysPost);
    }

    /**
     * 删除系统岗位信息
     */
    public Boolean deleteSysPost(Long postId) {
        redisOperator.del(SysPostKey.sys_post_INFO_MAP + postId);
        return postDomainService.removeById(postId);
    }

    /**
     * 查询系统岗位信息通过id
     *
     * @param postId 系统岗位ID
     * @return SysPostDTO
     */
    public SysPostDTO selectSysPostCacheByPostId(Long postId) {
        SysPost sysPost = postDomainService.selectSysPostById(postId);
        if (sysPost == null) {
            return null;
        }
        return SysPostConvertor.convertToSysPostDTO(sysPost);
    }

    /**
     * 查询岗位信息
     *
     * @param postId 岗位ID
     * @return SysPost
     */
    public SysPost selectSysPostByUserId(Long postId) {
        return postDomainService.selectSysPostById(postId);
    }

    /**
     * 修改岗位信息
     */
    public Boolean updateSysPost(SysPost sysPost) {
        redisOperator.set(SysPostKey.sys_post_INFO_MAP + sysPost.getPostId(), JSON.toJSONString(sysPost), 36000);
        return postDomainService.updateById(sysPost);

    }


    /**
     * 修改岗位信息
     *
     * @param currentUser     用户信息
     * @param modifySysPostVo 修改岗位数据
     */
    public boolean modifySysPost(SysPost sysPost, ModifySysPostVO modifySysPostVo, CurrentUser currentUser) {
        SysPost build = postDomainService.buildModifyPost(sysPost, modifySysPostVo, currentUser);
        redisOperator.set(SysPostKey.sys_post_INFO_MAP + modifySysPostVo.getPostId(), JSON.toJSONString(build), 36000);
        return postDomainService.updateById(build);
    }

    /**
     * 查询系统分页岗位
     *
     * @param current  当前页
     * @param size     每页条数
     * @param postName 岗位名称
     * @param postCode 岗位编号
     * @param status   状态
     * @return Page<SysPostDTO>
     */
    public IPage<SysPostDTO> querySysPostPage(Integer current, Integer size, String postName, String postCode, Integer status) {
        Page page = new Page(current, size);
        SysPost sysPost = SysPost.builder().build();
        QueryWrapper<SysPost> queryWrapper = Condition.getQueryWrapper(sysPost);
        if (StringUtil.isNotEmpty(postName)) {
            queryWrapper.lambda().like(SysPost::getPostName, postName);
        }
        if (StringUtil.isNotEmpty(postCode)) {
            queryWrapper.lambda().like(SysPost::getPostCode, postCode);
        }
        if (!StringUtil.isEmpty(status)) {
            queryWrapper.lambda().eq(SysPost::getStatus, status);
        }
        queryWrapper.lambda().orderByAsc(SysPost::getPostSort);
        Page<SysPost> sysPostPage = sysPostMapper.selectPage(page, queryWrapper);
        // 转换: 转换系统岗位分页
        return this.convertToSysPostDTOPage(sysPostPage);
    }

    /**
     * 转换系统岗位分页数据
     *
     * @param sysPostPage
     * @return Page<SysPostDTO>
     */
    private IPage<SysPostDTO> convertToSysPostDTOPage(Page<SysPost> sysPostPage) {
        List<SysPost> records = sysPostPage.getRecords();
        if (StringUtil.isEmpty(records)) {
            return new Page<>(sysPostPage.getCurrent(), sysPostPage.getSize(), sysPostPage.getTotal());
        }
        List<SysPostDTO> list = SysPostConvertor.convertToSysPostDTOList(records);
        IPage<SysPostDTO> pageVo = new Page<>(sysPostPage.getCurrent(), sysPostPage.getSize(), sysPostPage.getTotal());
        pageVo.setRecords(list);
        return pageVo;
    }

}
