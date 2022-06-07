package com.example.layer.sys.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Hzhi
 * @Date 2022-04-19 11:26
 * @description
 **/
@Data
@NoArgsConstructor
public class SysUserVO {
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
    private String birthday;
}
