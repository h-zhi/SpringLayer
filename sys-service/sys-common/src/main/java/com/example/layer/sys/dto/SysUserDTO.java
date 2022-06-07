package com.example.layer.sys.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author Hzhi
 * @Date 2022-04-19 9:44
 * @description
 **/
@Data
@NoArgsConstructor
public class SysUserDTO {
    @ApiModelProperty(value = "用户ID")
    private String userId;
    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty(value = "登录名")
    private String loginName;
    @ApiModelProperty(value = "年龄")
    private Integer age;
    @ApiModelProperty(value = "手机号")
    private String phone;
    @ApiModelProperty(value = "性别 0男 1女")
    private Integer sex;
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "状态 0在用 1停用")
    private Integer status;
    @ApiModelProperty(value = "头像")
    private String avatar;
    @ApiModelProperty(value = "生日")
    private LocalDateTime birthday;
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
}
