package com.example.layer.auth.exception;

/**
 * 业务代码接口
 *
 * @author houzhi
 */
public class OauthExceptionConstant {

    public static final String LOGIN_SUCCESS = "登录成功!";
    public static final String USERNAME_INVALID_ERROR = "无效的用户名";
    public static final String CREDENTIALS_EXPIRED = "该账户的登录凭证已过期，请重新登录!";
    public static final String ACCOUNT_DISABLED = "该账户已被禁用，请联系管理员!";
    public static final String ACCOUNT_LOCKED = "该账号已被锁定，请联系管理员!";
    public static final String ACCOUNT_EXPIRED = "该账号已过期，请联系管理员!";
    public static final String PERMISSION_DENIED = "没有访问权限，请联系管理员!";
    public static final String NOT_USER = "用户不存在!";
    public static final String NOT_TENANT = "用户所在租户不存在!";
    public static final String ACCOUNT_LOCK = "账户已锁定";
    public static final String ACCOUNT_ERROR = "用户名或密码错误，1小时内剩余%s次尝试";
    public static final String NOT_CAPTCHA = "验证码不能为空";
    public static final String NOT_KEY_CAPTCHA = "验证码身份不能为空";
    public static final String EXPIRED_CAPTCHA = "验证码过期";
    public static final String ERROR_CAPTCHA = "验证码错误";
    public static final String INVALID_SIGN = "签字认证失败或已失效";
}