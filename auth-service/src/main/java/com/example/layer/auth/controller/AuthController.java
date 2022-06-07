package com.example.layer.auth.controller;

import com.example.layer.auth.domain.Oauth2TokenDto;
import com.example.layer.auth.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springlayer.core.tool.api.R;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Map;

/**
 * @Author Hzhi
 * @Date 2022/3/22 9:57
 * @description 自定义Oauth2获取令牌接口
 **/
@Api(tags = "登录/注销")
@Controller
@RequestMapping("/oauth")
public class AuthController {

    @Resource
    private AuthService authService;
    @Resource
    private ConsumerTokenServices consumerTokenServices;

    /**
     * Oauth2登录认证
     */
    @ApiOperation(value = "登录")
    @RequestMapping(value = "/token", method = RequestMethod.POST)
    @ResponseBody
    public R<Oauth2TokenDto> postAccessToken(Principal principal, @RequestParam Map<String, String> parameters, HttpServletResponse response) throws Exception {
        return R.data(authService.authAccessToken(principal, parameters));
    }

    /**
     * Oauth2注销
     */
    @ApiOperation(value = "注销")
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseBody
    public R postAccessToken(@RequestParam("token") String token) {
        if (consumerTokenServices.revokeToken(token)) {
            return R.success("注销成功");
        }
        return R.fail("注销失败");
    }
}