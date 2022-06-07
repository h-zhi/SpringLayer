package com.example.layer.sys.datascope.querywrapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.layer.sys.engine.domain.core.SysRole;
import com.example.layer.sys.engine.service.SysUserDeptService;
import org.springlayer.core.boot.context.CurrentUser;
import org.springlayer.core.boot.context.CurrentUserHolder;
import com.example.layer.sys.engine.service.SysRoleService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author zhaoyl
 * @Date 2022-05-30 16:25
 * @description 数据权限范围过滤
 */
@Component
public class DataScopeFilter {

    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private SysUserDeptService sysUserDeptService;

    /**
     * 全部数据权限
     */
    public static final String DATA_SCOPE_ALL = "1";

    /**
     * 自定数据权限
     */
    public static final String DATA_SCOPE_CUSTOM = "2";

    /**
     * 部门数据权限
     */
    public static final String DATA_SCOPE_DEPT = "3";

    /**
     * 部门及以下数据权限
     */
    public static final String DATA_SCOPE_DEPT_AND_CHILD = "4";

    /**
     * 仅本人数据权限
     */
    public static final String DATA_SCOPE_SELF = "5";

    /**
     * 根据用户id查询全部权限
     * @param userId
     * @return
     */
    public Set<String> queryDataScope(Long userId){
        List<SysRole> list = sysRoleService.queryUserRolesByUserId(userId);
        Set<String> dataScope = new HashSet<>();
        list.forEach(sysRole -> {
            dataScope.add(sysRole.getDataScope());
        });
        return dataScope;
    }

    /**
     * 查询用户所在部门的人员
     * @param userId
     */
    public List<Long>  getDeptUserByUserId(Long userId){
        Set<Long> set = sysUserDeptService.selectDeptUserIdsByUser(userId);
        List<Long> list = new ArrayList<Long>();
        list.addAll(set);
        return list;
    }

    /**
     * 查询用户所在部门及其子部门人员
     * @param userId
     */
    public  List<Long> getAllDeptUserByUserId(Long userId){
        Set<Long> set = sysUserDeptService.selectAllDeptUserIdsByUser(userId);
        List<Long> list = new ArrayList<Long>();
        list.addAll(set);
        return list;
    }

    /**
     * 查询角色所在部门全部人员
     * @param userId
     * @return
     */
    public  List<Long> getRoleDeptByUserId(Long userId){
        Set<Long> deptIds = sysRoleService.selectAllRoleDeptUserIdsByUser(userId);
        Set<Long> userIds = new HashSet<>();
        deptIds.forEach(deptId->{
            List<Long> list = sysUserDeptService.selectUserIdsByDeptId(deptId);
            userIds.addAll(list);
        });
        List<Long> list = new ArrayList<Long>();
        list.addAll(userIds);
        return list;
    }

    /**
     * 数据权限过滤条件
     * @param queryWrapper
     * @param userId
     */
    public QueryWrapper authorityFilter(QueryWrapper queryWrapper, Long userId){
        CurrentUser currentUser = CurrentUserHolder.getCurrentUser();
        if (currentUser.isAdmin()){
            return queryWrapper;
        }
        Set<String> dataScope = queryDataScope(userId);
        for (String scope:dataScope){
            //全部数据权限
            if (DATA_SCOPE_ALL.equals(scope)){
                queryWrapper.or();
                queryWrapper.apply("1=1");
            //本部门数据权限
            }else if (DATA_SCOPE_DEPT.equals(scope)){
                List<Long> deptUserId = getDeptUserByUserId(userId);
                queryWrapper.or();
                queryWrapper.in("user_id",deptUserId);
            //个人数据权限
            }else if (DATA_SCOPE_SELF.equals(scope)){
                queryWrapper.or();
                queryWrapper.apply(
                        "user_id = {0}",
                        userId );
            //自定数据权限
            }else if (DATA_SCOPE_CUSTOM.equals(scope)){
                List<Long> roleDeptUserId = getRoleDeptByUserId(userId);
                queryWrapper.or();
                queryWrapper.in("user_id",roleDeptUserId);
             //本部门及以下数据权限
            }else if (DATA_SCOPE_DEPT_AND_CHILD.equals(scope)){
                List<Long> deptAllUserId = getAllDeptUserByUserId(userId);
                queryWrapper.or();
                queryWrapper.in("user_id",deptAllUserId);
            }
        }
        return queryWrapper;
    }


}

