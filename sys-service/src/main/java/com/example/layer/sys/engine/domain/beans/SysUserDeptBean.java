package com.example.layer.sys.engine.domain.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Hzhi
 * @Date 2022-04-19 10:20
 * @description 用户部门Bean
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysUserDeptBean {
    private String username;
    private String loginName;
    private String phone;
    private Long deptId;
}