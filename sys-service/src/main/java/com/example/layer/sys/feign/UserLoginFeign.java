package com.example.layer.sys.feign;

import com.example.layer.sys.engine.service.SysAclModuleService;
import com.example.layer.sys.engine.service.SysUserService;
import com.example.layer.sys.client.LoginClient;
import com.example.layer.sys.dto.LoginUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhihou
 * @date 2022/04/11 16:39
 * @description
 */
@Slf4j
@RestController
public class UserLoginFeign implements LoginClient {

    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysAclModuleService sysAclModuleService;

    /**
     * 获取登录参数
     *
     * @param param 手机号或者用户名
     * @return TenantUserDTO
     */
    @Override
    public LoginUserDTO login(String param) {
        // 登录获取登录用户
        LoginUserDTO loginUserDTO = sysUserService.login(param);
        if (null == loginUserDTO) {
            return null;
        }
        List<Long> aclModules = sysAclModuleService.getAclModules(loginUserDTO.getId());
        loginUserDTO.setRoles(aclModules);
        return loginUserDTO;
    }
}
