package com.example.layer.auth.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springlayer.core.tool.api.R;
import org.springlayer.core.tool.exception.BusinessException;

/**
 * @author zhihou
 * @date 2020/12/15 19:22
 * @description 全局处理Oauth2抛出的异常
 */
@Slf4j
@ControllerAdvice
public class Oauth2ExceptionHandler {

    /**
     * OAuth2 Exception处理
     *
     * @param e
     * @return OauthR
     */
    @ResponseBody
    @ExceptionHandler(value = OAuth2Exception.class)
    public R handleOauth2(OAuth2Exception e) {
        return R.fail(e.getMessage());
    }

    /**
     * 业务异常处理
     *
     * @param e
     * @return OauthR
     */
    @ResponseBody
    @ExceptionHandler(value = BusinessException.class)
    public R handleOauth2(BusinessException e) {
        return R.fail(e.getMessage());
    }
}
