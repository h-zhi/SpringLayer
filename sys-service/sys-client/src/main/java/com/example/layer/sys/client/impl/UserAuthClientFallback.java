package com.example.layer.sys.client.impl;

import com.example.layer.sys.client.UserAuthClient;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhihou
 * @date 2022/04/11 14:31
 * @description 用户授权认证
 */
@Service
public class UserAuthClientFallback implements UserAuthClient {

    @Override
    public List<String> authenticationTenant(Long userId, String uri) {
        return null;
    }
}