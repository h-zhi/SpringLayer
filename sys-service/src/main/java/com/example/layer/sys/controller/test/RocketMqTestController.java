package com.example.layer.sys.controller.test;

import com.example.layer.sys.engine.domain.core.SysUser;
import org.springlayer.core.rocketmq.helper.RocketMqHelper;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author Hzhi
 * @Date 2022-05-05 17:26
 * @description
 **/
@RestController
@RequestMapping(value = "/test/mq")
public class RocketMqTestController {

    @Resource
    private RocketMqHelper rocketMqHelper;

    @GetMapping(value = "/send")
    public void sendmsg() {
        SysUser sysUser = new SysUser();
        sysUser.setUsername("houzhi");
        sysUser.setAge(18);
        rocketMqHelper.asyncSend("SYS_ADD", MessageBuilder.withPayload(sysUser).build());
    }
}