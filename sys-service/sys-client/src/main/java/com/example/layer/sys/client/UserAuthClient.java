package com.example.layer.sys.client;

import com.example.layer.sys.client.impl.UserAuthClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springlayer.core.cloud.config.FeignErrorConfig;

import java.util.List;

/**
 * @author zhihou
 * @date 2022/04/11 13:31
 * @description 用户授权认证
 */
@FeignClient(value = "sys-service", url = "${service-url.sys-service:}", fallback = UserAuthClientFallback.class, configuration = FeignErrorConfig.class)
public interface UserAuthClient {

    /**
     * 网关鉴权降级到接口鉴权
     *
     * @param userId
     * @param uri
     * @return List<String> 角色ID集合
     */
    @GetMapping("/authentication")
    List<String> authenticationTenant(@RequestParam("userId") Long userId, @RequestParam("uri") String uri);

}
