package com.example.layer.sys.engine.domain.core;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author Hzhi
 * @Date 2022-04-19 9:25
 * @description 岗位实体
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName(value = "sys_post")
public class SysPost {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @TableId(type = IdType.ASSIGN_ID)
    private Long postId;
    /**
     * 岗位编码
     * */
    private String postCode;
    /**
     * 岗位名称
     * */
    private String postName;
    /**
     * 岗位排序
     * */
    private Integer postSort;
    /**
     * 岗位状态
     * */
    private Integer status;
    /**
     * 创建时间
     */
    private Long createBy;
    private LocalDateTime createTime;
    private String remark;
}
