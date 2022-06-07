package com.example.layer.sys.engine.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.layer.sys.engine.domain.core.SysRole;
import com.example.layer.sys.engine.domain.core.SysRoleDept;
import com.example.layer.sys.engine.domain.core.SysUserRole;
import com.example.layer.sys.engine.domain.service.SysRoleDeptDomainService;
import com.example.layer.sys.engine.domain.service.SysRoleDomainService;
import com.example.layer.sys.engine.domain.service.SysUserRoleDomainService;
import org.springlayer.core.boot.context.CurrentUser;
import org.springlayer.core.mp.support.Condition;
import org.springlayer.core.redis.helper.RedisOperator;
import org.springlayer.core.tool.utils.StringUtil;
import com.example.layer.sys.dto.SysRoleDTO;
import com.example.layer.sys.engine.components.redis.SysRoleKey;
import com.example.layer.sys.engine.domain.convertot.SysRoleConvertor;
import com.example.layer.sys.engine.mapper.SysRoleMapper;
import com.example.layer.sys.vo.ModifySysRoleVO;
import com.example.layer.sys.vo.SysRoleDeptVO;
import com.example.layer.sys.vo.SysRoleVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;


/**
 * @Author zhaoyl
 * @Date 2022-04-21
 * @description
 **/
@Service
public class SysRoleService {
    @Resource
    private SysRoleDomainService sysRoleDomainService;
    @Resource
    private RedisOperator redisOperator;
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private SysUserRoleDomainService sysUserRoleDomainService;
    @Resource
    private SysRoleDeptDomainService sysRoleDeptDomainService;
    /**
     * 创建角色
     * @param sysRoleVO
     * @param currentUser
     * @return boolean
     */
    public boolean savaSysRole(SysRoleVO sysRoleVO, CurrentUser currentUser) {
        SysRole sysRole = sysRoleDomainService.buildSysRole(sysRoleVO, currentUser);
        return sysRoleDomainService.save(sysRole);
    }


    public SysRoleDTO selectSysRoleCacheByRoleId(Long roleId) {
        SysRole sysRole = sysRoleDomainService.selectSysRoleById(roleId);
        if (sysRole==null){
            return null;
        }
        return SysRoleConvertor.convertToSysRoleDTO(sysRole);
    }

    /**
     * 查询角色信息
     * @param roleId 角色Id
     * @return SysRole
     */
    public SysRole selectSysRoleByRoleId(Long roleId) {
        return sysRoleDomainService.selectSysRoleById(roleId);
    }

    /**
     * 修改角色
     */
    public boolean modifySysRole(SysRole sysRole, ModifySysRoleVO modifySysRoleVO, CurrentUser currentUser) {
        SysRole build = sysRoleDomainService.buildModifySysRole(modifySysRoleVO, currentUser);
        build.setRoleId(sysRole.getRoleId());
        redisOperator.set(SysRoleKey.SYS_ROLE_INFO_MAP + modifySysRoleVO.getRoleId(), JSON.toJSONString(build), 36000);
        return sysRoleDomainService.updateById(build);
    }

    /**
     * 删除角色
     * @param sysRole
     * @return boolean
     */
    public boolean delSysRole(SysRole sysRole) {
        sysRole.setIsDeleted(true);
        redisOperator.del(SysRoleKey.SYS_ROLE_INFO_MAP + sysRole.getRoleId());
        return sysRoleDomainService.updateById(sysRole);
    }

    /**
     * 分页查询系统角色
     * @param current  当前页
     * @param size     每页条数
     * @param roleName 角色名称
     * @param category 角色组
     * @param status   状态
     * @return Page<SysRoleDTO>
     */
    public IPage<SysRoleDTO> querySysRolePage(Integer current, Integer size, String roleName, String category, Integer status) {
        Page page = new Page(current, size);
        SysRole sysRole =  SysRole.builder().build();
        QueryWrapper<SysRole> queryWrapper = Condition.getQueryWrapper(sysRole);
        if (StringUtil.isNotBlank(roleName)) {
            queryWrapper.lambda().like(SysRole::getRoleName, roleName);
        }
        if (StringUtil.isNotBlank(category)) {
            queryWrapper.lambda().eq(SysRole::getCategory, category);
        }
        if (!StringUtil.isEmpty(status)) {
            queryWrapper.lambda().eq(SysRole::getStatus, status);
        }
        queryWrapper.lambda().orderByAsc(SysRole::getRoleSort);
        Page<SysRole> sysRolePage = sysRoleMapper.selectPage(page, queryWrapper);
        IPage<SysRoleDTO> sysRoleDTOIPage = convertToSysPostDTOPage(sysRolePage);
        return sysRoleDTOIPage;
    }

    /**
     * 转换系统角色分页数据
     * @param sysRolePage
     * @return Page<SysRoleDTO>
     */
    private IPage<SysRoleDTO> convertToSysPostDTOPage(Page<SysRole> sysRolePage) {
        List<SysRole> records = sysRolePage.getRecords();
        if (StringUtil.isEmpty(records)) {
            return new Page<>(sysRolePage.getCurrent(), sysRolePage.getSize(), sysRolePage.getTotal());
        }
        List<SysRoleDTO> list = SysRoleConvertor.convertToSysPostDTOList(records);
        IPage<SysRoleDTO> pageVo = new Page<>(sysRolePage.getCurrent(), sysRolePage.getSize(), sysRolePage.getTotal());
        pageVo.setRecords(list);
        return pageVo;
    }

    /**
     * 根据用户id查询用户角色信息
     * @param userId
     * @return
     */
    public List<SysRole> queryUserRolesByUserId(Long userId){
        List<SysUserRole> sysUserRoles = sysUserRoleDomainService.queryUserRolesByUserId(userId);
        List<SysRole> list = new ArrayList<>();
        sysUserRoles.forEach(sysUserRole -> {
            SysRole sysRole = sysRoleDomainService.selectSysRoleById(sysUserRole.getRoleId());
            list.add(sysRole);
        });
        return list;
    }
    /**
     * 保存角色部门数据
     * @param sysRoleDeptVO
     */
    public boolean saveRoleDept(SysRoleDeptVO sysRoleDeptVO){
        //保存数据
        List<String> deptList = sysRoleDeptVO.getDeptId();
        String roleId = sysRoleDeptVO.getRoleId();
        List<SysRoleDept> sysRoleDeptList = new ArrayList<>();
        deptList.forEach(deptId->{
            if (StringUtils.isNotBlank(deptId)){
                SysRoleDept sysRoleDept = new SysRoleDept();
                sysRoleDept.setDeptId(Long.parseLong(deptId));
                sysRoleDept.setRoleId(Long.parseLong(roleId));
                sysRoleDeptList.add(sysRoleDept);
            }
        });
      return  sysRoleDeptDomainService.saveBatch(sysRoleDeptList);

    }

    /**
     * 查询角色自定义部门数据
     * @param roleIdList
     * @return
     */
    public  List<SysRoleDept> queryRoleDept(List<Long> roleIdList){
        List<SysRoleDept> sysRoleDeptList = new ArrayList<>();
        roleIdList.forEach(roleId->{
            List<SysRoleDept> list = sysRoleDeptDomainService.list(Wrappers.lambdaQuery(SysRoleDept.class).eq(SysRoleDept::getRoleId, roleId));
            sysRoleDeptList.addAll(list);
        });
      return sysRoleDeptList;
    }

    /**
     * 查询角色所在部门id
     * @param userId
     * @return
     */
    public Set<Long> selectAllRoleDeptUserIdsByUser(Long userId){
        Set<Long> set = new HashSet<>();
        List<SysRole> list = queryUserRolesByUserId(userId);
        List<Long> roleIdList = list.stream().map(s->s.getRoleId()).collect(toList());
        List<SysRoleDept> sysRoleDeptList = queryRoleDept(roleIdList);
        sysRoleDeptList.forEach(roleDept->{
            set.add(roleDept.getDeptId());
        });
        return set;
    }

}
