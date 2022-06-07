package com.example.layer.gateway.support;

import com.example.layer.sys.client.UserAuthClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author zhihou
 * @date 2022/03/22 19:40
 * @description 租户鉴权
 */
@Service
public class UserSupport {

    @Resource
    private UserAuthClient userAuthClient;

    /**
     * 网关鉴权降级到接口鉴权
     *
     * @param userId
     * @param uri
     * @return List<String> 角色ID集合
     */
    public List<String> authenticationTenant(Object userId, String uri) {
        return userAuthClient.authenticationTenant(Long.valueOf(String.valueOf(userId)), uri);
    }
}