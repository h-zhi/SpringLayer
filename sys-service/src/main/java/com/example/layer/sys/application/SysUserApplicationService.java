package com.example.layer.sys.application;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.layer.sys.engine.domain.core.SysUser;
import com.example.layer.sys.engine.domain.service.SysUserDomainService;
import com.example.layer.sys.engine.service.SysUserDeptService;
import com.example.layer.sys.engine.service.SysUserPostService;
import com.example.layer.sys.engine.service.SysUserRoleService;
import com.example.layer.sys.engine.service.SysUserService;
import com.example.layer.sys.dto.SysUserDTO;
import com.example.layer.sys.vo.CreateSysUserVO;
import com.example.layer.sys.vo.ModifySysUserVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Hzhi
 * @Date 2022-04-19 9:33
 * @description 系统用户应用层
 **/
@Service
public class SysUserApplicationService {

    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysUserDomainService sysUserDomainService;
    @Resource
    private SysUserPostService sysUserPostService;
    @Resource
    private SysUserRoleService sysUserRoleService;
    @Resource
    private SysUserDeptService sysUserDeptService;

    /**
     * 查询当前用户
     *
     * @param userId
     * @return SysUserDTO
     */
    public SysUserDTO queryCurrentUser(Long userId) {
        return sysUserService.querySysUserCacheByUserId(userId);
    }

    /**
     * 查询当前用户
     *
     * @param userId
     * @return SysUserDTO
     */
    public SysUserDTO querySysUserDetail(Long userId) {
        return sysUserService.querySysUserCacheByUserId(userId);
    }

    /**
     * 分页查询系统用户
     *
     * @param current   当前页
     * @param size      每页条数
     * @param username  用户名
     * @param loginName 登录名
     * @param phone     手机号
     * @param deptId    部门ID
     * @return IPage<SysUserDTO> 分页用户信息
     */
    public IPage<SysUserDTO> querySysUserPage(Integer current, Integer size, String username, String loginName, String phone, Long deptId) {
        // 查询: 查询系统用户分页集合
        return sysUserService.querySysUserPage(current, size, username, loginName, phone, deptId);
    }

    /**
     * 创建系统用户
     *
     * @param createSysUserVO
     * @return boolean
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean createSysUser(CreateSysUserVO createSysUserVO) {
        // 校验: 校验创建系统用户参数
        sysUserDomainService.checkCreateSysUserParam(createSysUserVO);
        // 校验: 校验手机是否存在
        sysUserDomainService.isExistUserByPhone(createSysUserVO.getPhone());
        // 校验: 校验登录名是否存在
        sysUserDomainService.isExistUserByLoginName(createSysUserVO.getLoginName());
        // 保存: 保存系统用户
        SysUser sysUser = sysUserService.savaSysUser(createSysUserVO);
        // 保存: 保存系统用户岗位
        sysUserPostService.savaSysUserPost(createSysUserVO.getPostIds(), sysUser.getUserId());
        //保存: 保存系统用户与部门关系
        sysUserDeptService.savaSysUserDept(createSysUserVO.getDeptIds(), sysUser.getUserId());
        // 保存: 保存系统用户与角色关系
        return sysUserRoleService.savaSysUserRole(createSysUserVO.getRoleIds(), sysUser.getUserId());
    }

    /**
     * 编辑系统用户
     *
     * @param modifySysUserVO 编辑用户参数
     * @return boolean
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean modifySysUser(ModifySysUserVO modifySysUserVO) {
        // 校验: 校验编辑系统用户参数
        sysUserDomainService.checkModifySysUserParam(modifySysUserVO);
        // 查询: 查询系统用户
        SysUser sysUser = sysUserService.querySysUserByUserId(Long.valueOf(modifySysUserVO.getUserId()));
        // 校验: 校验手机是否存在
        sysUserService.checkSamePhoneAndLoginName(sysUser, modifySysUserVO);
        // 修改: 修改系统用户
        sysUserService.modifySysUser(sysUser, modifySysUserVO);
        // 修改: 修改用户岗位
        sysUserPostService.modifySysUserPost(sysUser.getUserId(), modifySysUserVO.getPostIds());
        // 修改: 修改用户与部门关系
        sysUserDeptService.modifySysUserDept(modifySysUserVO.getDeptIds(), sysUser.getUserId());
        // 修改: 修改用户与角色关系
        return sysUserRoleService.modifySysUserRole(modifySysUserVO.getRoleIds(), sysUser.getUserId());
    }

    /**
     * 移除系统用户
     *
     * @param userId 用户ID
     * @return boolean
     */
    public boolean removeSysUser(Long userId) {
        // 查询: 查询系统用户
        SysUser sysUser = sysUserService.querySysUserByUserId(userId);
        // 删除: 删除用户
        return sysUserService.delSysUser(sysUser);
    }

    /**
     * 查询全部用户list
     * @return
     */
    public  List<SysUser> queryUserList(){
        List<SysUser> list = sysUserDomainService.list();
        return  list;
    }
}
