package com.example.layer.auth.component;

import com.example.layer.auth.domain.SecurityUser;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @Author Hzhi
 * @Date 2022/3/21 10:15\
 * @description JWT内容增强器
 *
 */
@Component
public class JwtTokenEnhancer implements TokenEnhancer {

    /**
     * 设置个人信息到JWT (用户id设置到JWT)
     *
     * @param accessToken
     * @param authentication
     * @return OAuth2AccessToken
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        Map<String, Object> info = new HashMap<>(6);
        info.put("id", securityUser.getId());
        info.put("username", securityUser.getUsername());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
        return accessToken;
    }
}