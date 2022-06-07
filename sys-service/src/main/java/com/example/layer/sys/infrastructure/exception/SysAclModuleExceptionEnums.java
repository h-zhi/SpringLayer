package com.example.layer.sys.infrastructure.exception;

import org.springlayer.core.tool.api.IResultCode;
import org.springlayer.core.tool.constant.ApiConstant;

/**
 * @Author zhaoyl
 * @Date 2022-04-26
 * @description
 **/
public enum SysAclModuleExceptionEnums implements IResultCode {
    NOT_EXIST_SOURCE_ID("来源ID不存在"),
    NOT_EXIST_SOURCE_MODULE("来源组不存在"),
    NOT_EXIST_STATUS("状态不存在"),
    NOT_EXIST_ACL_MODULE_ID("aclModuleId不存在"),
    EXIST_SOURCE_MODULE("来源组已存在"),
    NOT_EXIST_ID("ID不存在"),
    NOT_EXIST_ACL_MODULE("权限模块不存在");

    final String message;

    SysAclModuleExceptionEnums(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getCode() {
        return ApiConstant.FAILURE;
    }
}
