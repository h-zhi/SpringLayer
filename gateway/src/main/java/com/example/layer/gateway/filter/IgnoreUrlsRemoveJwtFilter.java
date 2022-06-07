package com.example.layer.gateway.filter;

import cn.hutool.json.JSONUtil;
import com.example.layer.gateway.config.IgnoreUrlsConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.springlayer.core.secure.constant.AuthConstant;
import org.springlayer.core.tool.api.R;
import org.springlayer.core.tool.utils.StringUtil;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @Author Hzhi
 * @Date 2022/3/23 18:42
 * @description 白名单路径访问时需要移除JWT请求头
 **/
@Slf4j
@Component
public class IgnoreUrlsRemoveJwtFilter implements WebFilter {

    private static final String emptyString = "";
    @Resource
    private IgnoreUrlsConfig ignoreUrlsConfig;

    /**
     * 白名单路径访问时需要移除JWT请求头
     *
     * @param exchange
     * @param chain
     * @return Mono
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        URI uri = request.getURI();
        PathMatcher pathMatcher = new AntPathMatcher();
        //白名单路径移除JWT请求头
        List<String> ignoreUrls = ignoreUrlsConfig.getUrls();
        for (String ignoreUrl : ignoreUrls) {
            if (pathMatcher.match(ignoreUrl, uri.getPath())) {
                request = exchange.getRequest().mutate().header(AuthConstant.AUTHORIZATION, emptyString).build();
                exchange = exchange.mutate().request(request).build();
                return chain.filter(exchange);
            }
        }
        HttpHeaders headers = request.getHeaders();
        if (StringUtil.isEmpty(headers.get(AuthConstant.AUTHORIZATION))) {
            return this.returnUnauthorizedResponse(response);
        } else if (!headers.get(AuthConstant.AUTHORIZATION).get(0).contains(AuthConstant.BEARER)) {
            return this.returnUnauthorizedResponse(response);
        }
        return chain.filter(exchange);
    }

    /**
     * 返回无权限响应
     *
     * @param response
     * @return Mono<Void>
     */
    private Mono<Void> returnUnauthorizedResponse(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        String body = JSONUtil.toJsonStr(R.fail(401, "No request header was found or the request header format is wrong."));
        DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(Charset.forName("UTF-8")));
        return response.writeWith(Mono.just(buffer));
    }
}
