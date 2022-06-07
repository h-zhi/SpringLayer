package com.example.layer.sys.engine.service;

import com.example.layer.sys.engine.domain.core.SysDept;
import com.example.layer.sys.engine.domain.core.SysUserDept;
import com.example.layer.sys.engine.domain.service.SysDeptDomainService;
import com.example.layer.sys.engine.domain.service.SysUserDeptDomainService;
import com.example.layer.sys.infrastructure.exception.SysDeptExceptionConstant;
import com.example.layer.sys.vo.ModifySysDeptVO;
import com.example.layer.sys.vo.SysDeptVO;
import org.springframework.stereotype.Service;
import org.springlayer.core.boot.context.CurrentUser;
import org.springlayer.core.tool.utils.AssertUtil;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author Hzhi
 * @Date 2022-04-21 9:37
 * @description 系统部门逻辑层
 **/
@Service
public class SysDeptService {

    @Resource
    private SysDeptDomainService sysDeptDomainService;
    @Resource
    private SysUserDeptDomainService sysUserDeptDomainService;

    /**
     * 组装自己数据
     *
     * @param sysDeptList 组装数据
     * @param i           父类节点ID
     * @return List<SysDept>
     */
    public List<SysDept> assembleChildPerms(List<SysDept> sysDeptList, int i) {
        List<SysDept> returnList = new ArrayList<SysDept>();
        for (Iterator<SysDept> iterator = sysDeptList.iterator(); iterator.hasNext(); ) {
            SysDept t = (SysDept) iterator.next();
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t.getParentId() == i) {
                this.recursionFn(sysDeptList, t);
                returnList.add(t);
            }
        }
        return returnList;
    }

    /**
     * 递归列表
     *
     * @param list
     * @param t
     */
    private void recursionFn(List<SysDept> list, SysDept t) {
        // 得到子节点列表
        List<SysDept> childList = this.getChildList(list, t);
        t.setChildren(childList);
        for (SysDept tChild : childList) {
            if (this.hasChild(list, tChild)) {
                // 判断是否有子节点
                Iterator<SysDept> it = childList.iterator();
                while (it.hasNext()) {
                    SysDept n = (SysDept) it.next();
                    recursionFn(list, n);
                }
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysDept> getChildList(List<SysDept> list, SysDept t) {
        List<SysDept> menuList = new ArrayList<SysDept>();
        Iterator<SysDept> it = list.iterator();
        while (it.hasNext()) {
            SysDept n = (SysDept) it.next();
            if (n.getParentId().longValue() == t.getDeptId().longValue()) {
                menuList.add(n);
            }
        }
        return menuList;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysDept> list, SysDept t) {
        return this.getChildList(list, t).size() > 0 ? true : false;
    }

    /**
     * 通过组织ID查询系统系统组织
     *
     * @param deptId 组织ID
     * @return SysDept
     */
    public SysDept querySysDeptByDeptId(Long deptId) {
        return sysDeptDomainService.selectSysDeptByDeptId(deptId);
    }

    /**
     * 移除系统组织
     *
     * @param sysDept 系统部门
     * @param userId  用户ID
     * @return boolean
     */
    public boolean removeSysDept(SysDept sysDept, Long userId) {
        sysDept.setIsDeleted(true);
        sysDept.setUpdateBy(userId);
        return sysDeptDomainService.updateById(sysDept);
    }

    /**
     * 创建系统组织
     *
     * @param sysDeptVO     组织参数
     * @param currentUser   当前用户
     * @param parentSysDept 父系统组织
     * @return boolean
     */
    public boolean createSysDept(SysDeptVO sysDeptVO, CurrentUser currentUser, SysDept parentSysDept) {
        SysDept sysDept = sysDeptDomainService.buildSysDept(sysDeptVO, currentUser, parentSysDept);
        return sysDeptDomainService.saveDelCache(sysDept);
    }

    /**
     * 更新子组织
     *
     * @param sysDept
     */
    public void updateDeptChildren(SysDept sysDept) {
        if (sysDept.getParentId() == 0) {
            return;
        }
        SysDept newParentDept = sysDeptDomainService.selectSysDeptByDeptId(sysDept.getDeptId());
        AssertUtil.isEmpty(newParentDept, SysDeptExceptionConstant.NOT_PARENT_EXIST);
        // 更新子部门
        sysDeptDomainService.updateDeptChildren(sysDept.getDeptId(), newParentDept, sysDept);
    }

    /**
     * 更新系统组织
     *
     * @param sysDept
     * @param modifySysDeptVO
     * @param currentUser
     * @return boolean
     */
    public boolean modifySysDept(SysDept sysDept, ModifySysDeptVO modifySysDeptVO, CurrentUser currentUser) {
        // 构建修改系统组织
        sysDeptDomainService.buildModifySysDept(sysDept, modifySysDeptVO, currentUser);
        // 执行修改组织
        return sysDeptDomainService.updateDelCacheById(sysDept);
    }

    /**
     * 根据用户id查询用户所在部门
     * @param userId
     * @return
     */
    public List<SysDept> queryDeptByUserId(Long userId){
        List<SysUserDept> sysUserDepts = sysUserDeptDomainService.queryUserDeptByUserId(userId);
        List<SysDept> list = new ArrayList<>();
        sysUserDepts.forEach(sysUserDept -> {
            SysDept dept = sysDeptDomainService.getById(sysUserDept.getDeptId());
            list.add(dept);
        });
       return list;
    }

    /**
     * 根据用户id查询本部门及其子部门
     * @param userId
     * @return
     */
    public List<SysDept> queryAllDeptByUserId(Long userId){
        List<SysUserDept> sysUserDeptList = sysUserDeptDomainService.queryUserDeptByUserId(userId);
        List<SysDept> list = new ArrayList<>();
        sysUserDeptList.forEach(sysUserDept -> {
            List<SysDept> listDept = sysDeptDomainService.selectAllDeptList(sysUserDept.getDeptId());
            list.addAll(listDept);
        });
        return list;
    }

    /**
     * 查询用户所在部门及其全部子部门
     * @param userId
     * @return
     */
    public  List<SysDept> selectAllUserIdsByUser(Long userId){
        List<SysDept> list = new ArrayList<>();
        List<SysUserDept> sysUserDeptList = sysUserDeptDomainService.queryUserDeptByUserId(userId);
        sysUserDeptList.forEach(sysUserDept ->{
            List<SysDept> sysDeptList = sysDeptDomainService.selectAllDeptList(sysUserDept.getDeptId());
            list.addAll(sysDeptList);
        });
        return list;
    }

}
