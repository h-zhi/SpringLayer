package com.example.layer.sys.engine.domain.enums;

/**
 * @author zhihou
 * @date 2020/12/16 17:32
 * @description 公共状态
 * 0是 1否
 * 0启用 1禁用
 * 0展示 1隐藏
 */
public enum ComStatusEnum {

    YES("是", 0) {
        @Override
        public boolean isYes() {
            return true;
        }
    },
    NOT("否", 1) {
        @Override
        public boolean isNo() {
            return true;
        }
    };

    public Integer value() {
        return value;
    }

    public static ComStatusEnum value(Integer value) {
        switch (value) {
            case 0:
                return YES;
            case 1:
                return NOT;
        }

        return null;
    }

    private String name;

    private Integer value;

    ComStatusEnum(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public boolean isYes() {
        return false;
    }

    public boolean isNo() {
        return false;
    }

}