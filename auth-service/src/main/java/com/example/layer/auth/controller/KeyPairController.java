package com.example.layer.auth.controller;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

/**
 * @Author Hzhi
 * @Date 2022/3/22 13:31
 * @description 获取RSA公钥
 **/
@RestController
public class KeyPairController {

    @Resource
    private KeyPair keyPair;

    /**
     * 获取获取RSA公钥 接口
     *
     * @return Map<String, Object>
     */
    @GetMapping("/rsa/publicKey")
    public Map<String, Object> getKey() {
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAKey key = new RSAKey.Builder(publicKey).build();
        return new JWKSet(key).toJSONObject();
    }
}