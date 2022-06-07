package com.example.layer.auth.config;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springlayer.core.secure.ignore.OauthIgnorePath;
import org.springlayer.core.tool.utils.StringUtil;

/**
 * @Author Hzhi
 * @Date 2022/3/22 10:24
 * @description SpringSecurity配置
 **/
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 放行路径
     */
    public static final String[] RELEASE_PATH = new String[]{
            "/rsa/publicKey",
            "/oauth/token",
            "/obtain/captcha",
            "/oauth/logout",
    };

    /**
     * 置Security的认证策略, 每个模块配置使用and结尾。
     * authorizeRequests()配置路径拦截，表明路径访问所对应的权限，角色，认证信息。
     * formLogin()对应表单认证相关的配置
     * logout()对应了注销相关的配置
     * httpBasic()可以配置basic登录
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String[] allIgnoringUrls = RELEASE_PATH;
        if (!StringUtil.isEmpty(OauthIgnorePath.DEFAULT_IGNORE_URLS)) {
            allIgnoringUrls = ArrayUtils.addAll(RELEASE_PATH, OauthIgnorePath.DEFAULT_IGNORE_URLS);
        }
        http.authorizeRequests()
                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                .antMatchers(allIgnoringUrls).permitAll()
                .anyRequest().authenticated();
    }

    /**
     * 重新实例化 AuthenticationManager Bean
     *
     * @return AuthenticationManager
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * BCryptPasswordEncoder方法采用SHA-256 +随机盐+密钥对密码进行加密，SHA系列是Hash算法，不是加密算法，
     * 使用加密算法意味着可以解密（这个与编码/解码一样），但是采用Hash处理，其过程是不可逆的。
     *
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
