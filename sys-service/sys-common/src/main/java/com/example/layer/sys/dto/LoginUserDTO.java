package com.example.layer.sys.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author Hzhi
 * @Date 2022/4/11 9:31
 * @description 登录用户 dto
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserDTO {
    private Long id;
    private String username;
    private String loginName;
    private String password;
    private Integer status;
    private List<Long> roles;

    public LoginUserDTO(Long id, String username, String loginName, String password, Integer status) {
        this.id = id;
        this.username = username;
        this.loginName = loginName;
        this.password = password;
        this.status = status;
    }
}