package com.example.layer.sys;

import org.springlayer.core.boot.common.CommonImport;
import org.springlayer.core.cloud.constant.AppConstant;
import org.springlayer.core.launch.ILayerApplication;
import org.springlayer.core.redis.bean.RedisImport;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * 系统启动类
 *
 * @author houzhi
 */
@EnableFeignClients(basePackages = AppConstant.BASE_PACKAGES)
@ComponentScan(basePackages = AppConstant.BASE_PACKAGES)
@Import({CommonImport.class, RedisImport.class})
@SpringBootApplication
public class SysApplication {

    public static void main(String[] args) {
        ILayerApplication.run("sys-service", SysApplication.class, args);
    }
}
