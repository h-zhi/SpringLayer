package com.example.layer.gateway.authorization;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONObject;
import com.example.layer.gateway.support.UserSupport;
import com.nimbusds.jose.JWSObject;
import com.example.layer.gateway.config.AuthBaseUrlsConfig;
import com.example.layer.gateway.config.AuthWildcardUrlsConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springlayer.core.secure.constant.AuthConstant;
import org.springlayer.core.secure.constant.RedisConstant;
import org.springlayer.core.tool.utils.StringUtil;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.net.URI;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Hzhi
 * @Date 2022/3/23 10:40
 * @description 鉴权管理器，用于判断是否有资源的访问权限
 **/
@Slf4j
@Component
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private UserSupport userSupport;
    @Resource
    private AuthBaseUrlsConfig authBaseUrlsConfig;
    @Resource
    private AuthWildcardUrlsConfig authWildcardUrlsConfig;

    /**
     * 鉴权管理器，用于判断是否有资源的访问权限
     *
     * @param mono
     * @param authorizationContext
     * @return Mono<AuthorizationDecision>
     */
    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        ServerHttpRequest request = authorizationContext.getExchange().getRequest();
        //从Redis中获取当前路径可访问角色列表
        URI uri = request.getURI();
        // 获取当前用户信息从认证上下文中
        JSONObject userJsonObject = this.getCurrentUserInfo(authorizationContext);
        HttpHeaders headers = authorizationContext.getExchange().getRequest().getHeaders();
        List<String> urls = authBaseUrlsConfig.getUrls();
        if (urls.contains(uri.getPath())) {
            String token = request.getHeaders().getFirst(AuthConstant.AUTHORIZATION);
            if (StringUtil.isEmpty(token)) {
                return this.monoFilter(new ArrayList<>(), mono);
            }
            return this.dischargeMonoFilter(mono);
        }
        List<String> wildcardUrl = authWildcardUrlsConfig.getUrls();
        if (StringUtil.isNotEmpty(wildcardUrl)) {
            for (String url : wildcardUrl) {
                String substring = url.substring(0, url.length() - 3);
                if (uri.getPath().contains(substring)) {
                    String token = request.getHeaders().getFirst(AuthConstant.AUTHORIZATION);
                    if (StringUtil.isEmpty(token)) {
                        return this.monoFilter(new ArrayList<>(), mono);
                    }
                    return this.dischargeMonoFilter(mono);
                }
            }
        }
        List<String> authPath = null;
        if (!StringUtil.isEmpty(headers.get(AuthConstant.AUTHORIZATION)) && headers.get(AuthConstant.AUTHORIZATION).get(0).contains(AuthConstant.BEARER)) {
            Object userId = userJsonObject.get("id");
            authPath = this.authoritiesUri(userId, uri);
        }
        return this.monoFilter(authPath, mono);
    }

    /**
     * 放行路径
     *
     * @param mono
     * @return Mono<AuthorizationDecision>
     */
    private Mono<AuthorizationDecision> dischargeMonoFilter(Mono<Authentication> mono) {
        return mono.filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .all(new ArrayList<>()::add)
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }

    /**
     * 认证URI
     *
     * @param userId 用户ID
     * @param uri    访问路径
     * @return List<String> 返回角色ID集合
     */
    private List<String> authoritiesUri(Object userId, URI uri) {
        List<String> authorities = new ArrayList<>();
        if (null != userId) {
            log.info("try to determine if you have access to resources");
            try {
                // redis连接失败
                Object obj = redisTemplate.opsForHash().get(RedisConstant.RESOURCE_ROLES_MAP + userId, uri.getPath());
                if (null == obj) {
                    log.info("redis keys is null. redis authentication failed, call interface authentication");
                    authorities = userSupport.authenticationTenant(userId, uri.getPath());
                } else {
                    //认证通过且角色匹配的用户可访问当前路径
                    log.info("successfully connect to Redis, try authentication");
                    authorities = Convert.toList(String.class, obj);
                }
                authorities = authorities.stream().map(i -> i = AuthConstant.AUTHORITY_PREFIX + i).collect(Collectors.toList());
            } catch (Exception e) {
                authorities = userSupport.authenticationTenant(userId, uri.getPath());
                log.info("connect to redis fail. redis authentication failed, call interface authentication");
            }
        }
        return authorities;
    }

    /**
     * 返回Authorization 认证过滤器
     *
     * @param authPath 权限
     * @param mono
     * @return Mono<AuthorizationDecision>
     */
    private Mono<AuthorizationDecision> monoFilter(List<String> authPath, Mono<Authentication> mono) {
        return mono.filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(authPath::contains)
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }

    /**
     * 获取当前用户信息从认证上下文中
     *
     * @param authorizationContext
     * @return JSONObject
     */
    private JSONObject getCurrentUserInfo(AuthorizationContext authorizationContext) {
        String token = authorizationContext.getExchange().getRequest().getHeaders().getFirst(AuthConstant.AUTHORIZATION);
        if (StringUtil.isEmpty(token)) {
            return null;
        }
        String realToken = token.replace(AuthConstant.BEARER, "");
        JWSObject jwsObject = null;
        try {
            jwsObject = JWSObject.parse(realToken);
        } catch (ParseException e) {
            return null;
        }
        String userStr = jwsObject.getPayload().toString();
        return new JSONObject(userStr);
    }
}