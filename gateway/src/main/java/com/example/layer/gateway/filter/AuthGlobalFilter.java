package com.example.layer.gateway.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.alibaba.fastjson.JSON;
import com.nimbusds.jose.JWSObject;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springlayer.core.secure.constant.AuthConstant;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.function.Consumer;

/**
 * @Author Hzhi
 * @Date 2022/3/23 17:56
 * @description 将登录用户的JWT转化成用户信息的全局过滤器
 **/
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    private static Logger LOGGER = LoggerFactory.getLogger(AuthGlobalFilter.class);

    /**
     * 将登录用户的JWT转化成用户信息的全局过滤器
     *
     * @param exchange
     * @param chain
     * @return filter
     */
    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst(AuthConstant.AUTHORIZATION);
        if (StrUtil.isEmpty(token)) {
            return chain.filter(exchange);
        }
        try {
            //从token中解析用户信息并设置到Header中去
            String realToken = token.replace(AuthConstant.BEARER, "");
            JWSObject jwsObject = JWSObject.parse(realToken);
            String userStr = jwsObject.getPayload().toString();
            String encode = buildUserInfo(userStr);
            LOGGER.info("AuthGlobalFilter.filter() user:{}", encode);
            Consumer<HttpHeaders> httpHeaders = httpHeader -> {
                httpHeader.set("user", encode);
            };
            ServerHttpRequest serverHttpRequest = exchange.getRequest().mutate().headers(httpHeaders).build();
            exchange.mutate().request(serverHttpRequest).build();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return chain.filter(exchange);
    }

    /**
     * 构建用户信息
     *
     * @param userStr
     * @return
     * @throws UnsupportedEncodingException
     */
    private String buildUserInfo(String userStr) throws UnsupportedEncodingException {
        JSONObject userJsonObject = new JSONObject(userStr);
        String username = userJsonObject.get("username").toString();
        String encodeUserName = URLEncoder.encode(username, "UTF-8");
        userJsonObject.set("username", encodeUserName);
        return JSON.toJSONString(userJsonObject);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}