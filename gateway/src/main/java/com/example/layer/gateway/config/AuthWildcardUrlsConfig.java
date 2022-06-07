package com.example.layer.gateway.config;

import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author Hzhi
 * @Date 2022/3/23 14:21
 * @description 网关白名单通用配置
 **/
@EqualsAndHashCode(callSuper = false)
@Component
@ConfigurationProperties(prefix = "secure.wildcard")
public class AuthWildcardUrlsConfig {

    /**
     * 白名单路径
     */
    private List<String> urls;

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public List<String> getUrls() {
        return urls;
    }
}
