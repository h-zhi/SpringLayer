package com.example.layer.auth.domain;

import com.example.layer.sys.dto.LoginUserDTO;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author zhihou
 * @date 2022/03/22 13:22
 * @description Oauth2获取Token返回信息封装
 */
@Data
public class SecurityUser implements UserDetails {

    /**
     * ID
     */
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户状态
     */
    private Boolean enabled;
    /**
     * 用户状态
     */
    private Integer status;
    /**
     * 权限数据
     */
    private Collection<SimpleGrantedAuthority> authorities;

    public SecurityUser() {

    }

    /**
     * 处理用户角色权限
     *
     * @param tenantUserDTO
     */
    public SecurityUser(LoginUserDTO tenantUserDTO) {
        this.setId(tenantUserDTO.getId());
        this.setUsername(tenantUserDTO.getUsername());
        this.setPassword(tenantUserDTO.getPassword());
        this.setStatus(tenantUserDTO.getStatus());
        this.setEnabled(true);
        if (tenantUserDTO.getRoles() != null) {
            authorities = new ArrayList<>();
            tenantUserDTO.getRoles().forEach(item -> authorities.add(new SimpleGrantedAuthority(item.toString())));
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

}