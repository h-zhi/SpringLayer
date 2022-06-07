package com.example.layer.auth.controller;

import com.example.layer.auth.constants.CaptchaConstant;
import com.wf.captcha.SpecCaptcha;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springlayer.core.redis.helper.RedisOperator;
import org.springlayer.core.tool.api.R;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author Hzhi
 * @Date 2022/3/22 10:09
 * @description 验证码
 **/
@RestController
public class CaptchaController {

    @Resource
    private RedisOperator redisOperator;

    @ApiOperation(value = "获取验证码")
    @GetMapping("/obtain/captcha")
    @ResponseBody
    public R captcha() {
        String captchaKey = UUID.randomUUID().toString().replace("-", "");
        // 使用字符验证码
        SpecCaptcha captcha = new SpecCaptcha(130, 48, 4);
        // 存入redis ，默认五分钟
        redisOperator.set(CaptchaConstant.REDIS_CAPTCHA + captchaKey, captcha.text(), 5 * 60);
        Map map = new HashMap();
        map.put(CaptchaConstant.CAPTCHA_KEY, captchaKey);
        map.put(CaptchaConstant.IMAGE, captcha.toBase64());
        return R.data(map);
    }
}