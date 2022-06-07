package com.example.layer.auth.enums;

/**
 * @Author Hzhi
 * @date 2022/03/22 17:31
 * @description oauth 客户端数据
 */
public enum ClientEnum {

    LAYER("layer", "sys") {
        @Override
        public boolean isValuation() {
            return true;
        }
    };

    public static ClientEnum value(String client) {
        switch (client) {
            case "layer":
                return LAYER;
        }
        return null;
    }

    private String client;

    private String secret;

    public String getClient() {
        return client;
    }

    public String getSecret() {
        return secret;
    }

    ClientEnum(String client, String secret) {
        this.client = client;
        this.secret = secret;
    }

    public boolean isValuation() {
        return false;
    }

    public boolean isMicro() {
        return false;
    }

    public boolean isMicroApp() {
        return false;
    }
}