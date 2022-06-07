package com.example.layer.auth.service;

import com.example.layer.auth.domain.SecurityUser;
import com.example.layer.auth.exception.OauthExceptionConstant;
import com.example.layer.sys.client.LoginClient;
import com.example.layer.sys.dto.LoginUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springlayer.core.tool.exception.BusinessException;

import javax.annotation.Resource;

/**
 * @Author Hzhi
 * @Date 2022/3/22 10:29
 * @description 用户业务实现
 **/
@Slf4j
@Service
public class UserServiceImpl implements UserDetailsService {

    @Resource
    private LoginClient loginClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginUserDTO loginUserDTO = loginClient.login(username);
        if (null == loginUserDTO) {
            throw new BusinessException(OauthExceptionConstant.USERNAME_INVALID_ERROR);
        }
        loginUserDTO.setPassword(new BCryptPasswordEncoder().encode(loginUserDTO.getPassword()));
        return new SecurityUser(loginUserDTO);
    }
}