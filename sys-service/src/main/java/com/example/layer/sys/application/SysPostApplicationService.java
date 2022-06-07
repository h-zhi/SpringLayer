package com.example.layer.sys.application;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.layer.sys.engine.domain.core.SysPost;
import com.example.layer.sys.engine.domain.service.SysPostDomainService;
import com.example.layer.sys.engine.service.SysPostService;
import org.springlayer.core.boot.context.CurrentUser;
import com.example.layer.sys.dto.SysPostDTO;
import com.example.layer.sys.vo.ModifySysPostVO;
import com.example.layer.sys.vo.SysPostVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Author zhaoyl
 * @Date 2022-04-20
 * @description 系统用户应用层:岗位管理
 **/
@Service
public class SysPostApplicationService {
    @Resource
    private SysPostService sysPostService;
    @Resource
    private SysPostDomainService sysPostDomainService;

    /**
     * 创建岗位信息
     *
     * @param postVO
     * @return boolean
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean createSysPost(SysPostVO postVO, CurrentUser currentUser) {
        // 校验: 校验创建岗位信息参数
        sysPostDomainService.checkCreateSysPostParam(postVO);
        // 保存: 保存岗位信息
        return sysPostService.savaSysPost(postVO, currentUser);
    }

    /**
     * 删除岗位信息
     *
     * @param id
     * @return boolean
     */
    public boolean deleteSysPost(Long id) {
        //校验: 校验岗位下是否存在用户
        sysPostDomainService.checkExitsPostUser(id);
        return sysPostService.deleteSysPost(id);
    }

    /**
     * 查询当前岗位信息
     *
     * @param postId
     * @return SysPostDTO
     */
    public SysPostDTO queryCurrentPost(Long postId) {
        return sysPostService.selectSysPostCacheByPostId(postId);
    }

    /**
     * 分页查询岗位信息
     *
     * @param current  当前页
     * @param size     每页条数
     * @param postName 岗位名称
     * @param postCode 岗位编码
     * @param status   状态
     * @return IPage<SysPostDTO> 分页岗位信息
     */
    public IPage<SysPostDTO> querySysPostPage(Integer current, Integer size, String postName, String postCode, Integer status) {
        // 查询: 查询系统用户分页集合
        return sysPostService.querySysPostPage(current, size, postName, postCode, status);
    }

    /**
     * 修改岗位信息
     *
     * @param sysPost 编辑岗位参数
     * @return boolean
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateSysPost(SysPost sysPost) {
        return sysPostService.updateSysPost(sysPost);
    }

    /**
     * 编辑岗位信息
     *
     * @param modifySysPostVo 编辑参数
     * @return boolean
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean modifySysPost(ModifySysPostVO modifySysPostVo, CurrentUser currentUser) {
        // 校验: 校验创建岗位信息参数
        sysPostDomainService.checkModifySysPostParam(modifySysPostVo);
        // 查询: 查询岗位信息
        SysPost sysPost = sysPostService.selectSysPostByUserId(Long.valueOf(modifySysPostVo.getPostId()));
        // 修改: 修改岗位信息
        return sysPostService.modifySysPost(sysPost, modifySysPostVo, currentUser);
    }

}
