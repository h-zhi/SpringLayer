package com.example.layer.sys.engine.domain.enums;

/**
 * 权限角色模块 zhaoyl
 * @date 2022/05/05
 * @description 权限模块组分类
 *
 */
public enum ComAuthAclModuleEnum {
    USER("USER",1),ROLE("ROLE",2),DEPT("DEPT",3);

    private String name;
    private Integer value;

    ComAuthAclModuleEnum(String name, Integer value) {
        this.name = name;
        this.value = value;
    }
    public static Integer getAclModuleValue(String name) {
        for (ComAuthAclModuleEnum temp : ComAuthAclModuleEnum.values()) {
            if (temp.getName().equals(name)) {
                return temp.getValue();
            }
        }
        return null;
    }
    public static String getAclModuleName(Integer value) {
        for (ComAuthAclModuleEnum temp : ComAuthAclModuleEnum.values()) {
            if (temp.getValue().equals(value)) {
                return temp.getName();
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
