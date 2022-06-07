package com.example.layer.sys.client.impl;

import com.example.layer.sys.client.LoginClient;
import com.example.layer.sys.dto.LoginUserDTO;
import org.springframework.stereotype.Service;

/**
 * @author zhihou
 * @date 2022/04/11 13:39
 * @description
 */
@Service
public class LoginClientFallback implements LoginClient {

    @Override
    public LoginUserDTO login(String param) {
        return null;
    }
}
