package com.example.layer.auth;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Import;
import org.springlayer.core.boot.common.CommonImport;
import org.springlayer.core.cloud.constant.AppConstant;
import org.springlayer.core.launch.ILayerApplication;
import org.springlayer.core.redis.bean.RedisImport;

/**
 * @author houzhi
 */
@EnableDiscoveryClient
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableFeignClients(basePackages = AppConstant.BASE_PACKAGES)
@ComponentScans({
        @ComponentScan(basePackages = "org.springlayer.**"),
        @ComponentScan(basePackages = "com.example.**")}
)
@Import({CommonImport.class, RedisImport.class})
public class AuthApplication {


    public static void main(String[] args) {
        ILayerApplication.run("auth-service", AuthApplication.class, args);
    }
}