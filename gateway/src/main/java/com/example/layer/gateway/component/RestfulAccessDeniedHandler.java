package com.example.layer.gateway.component;

import cn.hutool.json.JSONUtil;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springlayer.core.tool.api.R;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;

/**
 * @Author Hzhi
 * @Date 2022/3/23 13:56
 * @description 自定义返回结果：没有权限访问时
 **/
@Component
public class RestfulAccessDeniedHandler implements ServerAccessDeniedHandler {

    /**
     * 自定义返回结果：没有权限访问时
     *
     * @param exchange
     * @param denied
     * @return Mono
     */
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        String body = JSONUtil.toJsonStr(R.fail(401, denied.getMessage()));
        DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(Charset.forName("UTF-8")));
        return response.writeWith(Mono.just(buffer));
    }
}
