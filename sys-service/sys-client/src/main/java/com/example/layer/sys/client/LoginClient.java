package com.example.layer.sys.client;

import com.example.layer.sys.client.impl.LoginClientFallback;
import com.example.layer.sys.dto.LoginUserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springlayer.core.cloud.config.FeignErrorConfig;

/**
 * @author zhihou
 * @date 2022/04/11 13:39
 * @description 登录
 */
@FeignClient(value = "sys-service", url = "${service-url.sys-service:}", fallback = LoginClientFallback.class, configuration = FeignErrorConfig.class)
public interface LoginClient {

    /**
     * 登录
     *
     * @param param 参数
     * @return LoginUserDTO
     */
    @GetMapping("/sys/login")
    @ResponseBody
    LoginUserDTO login(@RequestParam("param") String param);
}