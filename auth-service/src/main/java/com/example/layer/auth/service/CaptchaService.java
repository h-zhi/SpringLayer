package com.example.layer.auth.service;

import com.example.layer.auth.constants.CaptchaConstant;
import com.example.layer.auth.exception.OauthExceptionConstant;
import org.springframework.stereotype.Service;
import org.springlayer.core.redis.helper.RedisOperator;
import org.springlayer.core.secure.constant.AuthConstant;
import org.springlayer.core.secure.constant.RedisConstant;
import org.springlayer.core.tool.exception.BusinessException;
import org.springlayer.core.tool.utils.AssertUtil;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author Hzhi
 * @Date 2022/3/22 11:10
 * @description 验证码
 **/
@Service
public class CaptchaService {

    @Resource
    private RedisOperator redisOperator;

    /**
     * 校验验证码
     *
     * @param parameters 验证码参数MAP
     */
    public void checkCaptcha(Map<String, String> parameters) {
        String captcha = parameters.get(CaptchaConstant.CAPTCHA);
        AssertUtil.isEmpty(captcha, OauthExceptionConstant.NOT_CAPTCHA);
        String captchaKey = parameters.get(CaptchaConstant.CAPTCHA_KEY);
        AssertUtil.isEmpty(captchaKey, OauthExceptionConstant.NOT_KEY_CAPTCHA);
        String redisCaptcha = redisOperator.get(CaptchaConstant.REDIS_CAPTCHA + captchaKey);
        AssertUtil.isEmpty(redisCaptcha, OauthExceptionConstant.EXPIRED_CAPTCHA);
        AssertUtil.isBolEmpty(!captcha.equalsIgnoreCase(redisCaptcha), OauthExceptionConstant.ERROR_CAPTCHA);
    }

    /**
     * 校验用户锁定
     *
     * @param parameters
     */
    public void checkLockAccount(Map<String, String> parameters) {
        String username = parameters.get("username");
        String count = redisOperator.get(RedisConstant.ACCOUNT_LOCK + username);
        if (null == count) {
            redisOperator.set(RedisConstant.ACCOUNT_LOCK + username, String.valueOf(1), 60 * 60);
            throw new BusinessException(String.format(OauthExceptionConstant.ACCOUNT_ERROR, AuthConstant.LOCK_COUNT));
        } else {
            if (AuthConstant.LOCK_COUNT.equals(count)) {
                throw new BusinessException(OauthExceptionConstant.ACCOUNT_LOCK);
            } else {
                redisOperator.set(RedisConstant.ACCOUNT_LOCK + username, String.valueOf(Integer.valueOf(count) + 1), redisOperator.getExpire(RedisConstant.ACCOUNT_LOCK + username));
                throw new BusinessException(String.format(OauthExceptionConstant.ACCOUNT_ERROR, Integer.valueOf(AuthConstant.LOCK_COUNT) - Integer.valueOf(count)));
            }
        }
    }
}
