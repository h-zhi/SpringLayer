package com.example.layer.auth.service;

import com.example.layer.auth.domain.Oauth2TokenDto;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springlayer.core.redis.helper.RedisOperator;
import org.springlayer.core.secure.constant.AuthConstant;
import org.springlayer.core.secure.constant.RedisConstant;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.Map;

/**
 * @Author Hzhi
 * @Date 2022/3/22 9:57
 * @description
 **/
@Service
public class AuthService {

    @Resource
    private CaptchaService captchaService;
    @Resource
    private RedisOperator redisOperator;
    @Resource
    private TokenEndpoint tokenEndpoint;

    /**
     * auth 认证方式计价系统
     *
     * @param principal  信息
     * @param parameters 参数
     * @return Oauth2TokenDto
     * @throws HttpRequestMethodNotSupportedException
     */
    public Oauth2TokenDto authAccessToken(Principal principal, Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        OAuth2AccessToken oAuth2AccessToken = null;
        Oauth2TokenDto oauth2TokenDto = null;
        captchaService.checkCaptcha(parameters);
        try {
            oAuth2AccessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        } catch (InvalidGrantException e) {
            captchaService.checkLockAccount(parameters);
        }
        oauth2TokenDto = Oauth2TokenDto.builder()
                .token(oAuth2AccessToken.getValue())
                .refreshToken(oAuth2AccessToken.getRefreshToken().getValue())
                .expiresIn(oAuth2AccessToken.getExpiresIn())
                .tokenHead(AuthConstant.BEARER).build();
        redisOperator.del(RedisConstant.ACCOUNT_LOCK + parameters.get("username"));
        return oauth2TokenDto;
    }
}
