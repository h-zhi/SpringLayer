package com.example.layer.gateway.config;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * @Author Hzhi
 * @Date 2022/3/23 16:03
 * @description 统一配置跨域
 **/
@Configuration
public class ICorsConfiguration {

    String allowedOrigin;

    @EventListener
    public void envListener(EnvironmentChangeEvent event) {
        corsFilter();
    }

    private CorsConfiguration buildConfig() {
        CorsConfiguration config = new CorsConfiguration();
        if (!StringUtils.isEmpty(allowedOrigin)) {
            for (String origin : Arrays.asList(StringUtils.delimitedListToStringArray(allowedOrigin, ","))) {
                config.addAllowedOrigin(origin);
            }
        } else {
            config.addAllowedOrigin("*");
        }
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);
        return config;
    }

    @Bean
    @RefreshScope
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public CorsWebFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsWebFilter(source);
    }
}