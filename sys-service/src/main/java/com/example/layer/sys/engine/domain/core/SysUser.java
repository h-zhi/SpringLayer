package com.example.layer.sys.engine.domain.core;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author Hzhi
 * @Date 2022-04-19 9:25
 * @description 用户实体
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName(value = "sys_user")
public class SysUser implements Serializable {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @TableId(type = IdType.ASSIGN_ID)

    @ExcelIgnore
    private Long userId;
    @Excel(name = "用户名称")
    private String username;
    @Excel(name = "登录名称")
    private String loginName;
    @Excel(name = "年龄")
    private Integer age;
    @Excel(name = "密码")
    private String password;
    @Excel(name = "电话")
    private String phone;
    @Excel(name = "性别",replace = {"男_0", "女_1"})
    private Integer sex;
    @Excel(name = "邮箱")
    private String email;
    @Excel(name = "状态(0启用,1停用)")
    private Integer status;
    @Excel(name = "头像")
    private String avatar;
    //@Excel(name = "生日",isImportField = "true",exportFormat = "yyyy-MM-dd HH:mm:ss", importFormat =  "yyyy-MM-dd HH:mm:ss" ,databaseFormat = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "生日")
    private String birthday;
    @ExcelIgnore
    private LocalDateTime createTime;
    @Excel(name = "是否删除")
    private Boolean isDeleted;
}
