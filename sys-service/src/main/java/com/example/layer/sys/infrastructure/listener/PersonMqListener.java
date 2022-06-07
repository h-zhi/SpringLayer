package com.example.layer.sys.infrastructure.listener;

import com.example.layer.sys.engine.domain.core.SysUser;
import com.example.layer.sys.engine.domain.service.SysUserDomainService;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author Hzhi
 * @Date 2022-05-05 17:18
 * @description
 **/
@Component
@RocketMQMessageListener(consumerGroup = "${rocketmq.producer.groupName}", topic = "SYS_ADD")
public class PersonMqListener implements RocketMQListener<SysUser> {

    @Resource
    private SysUserDomainService sysUserDomainService;

    @Override
    public void onMessage(SysUser sysUser) {
        System.out.println("接收到消息，开始消费..username:" + sysUser.getUsername() + ",age:" + sysUser.getAge());
        sysUserDomainService.save(sysUser);
    }
}