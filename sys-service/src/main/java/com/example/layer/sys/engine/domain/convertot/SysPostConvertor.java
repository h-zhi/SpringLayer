package com.example.layer.sys.engine.domain.convertot;

import com.example.layer.sys.engine.domain.core.SysPost;
import com.example.layer.sys.dto.SysPostDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Hzhi
 * @Date 2022-05-17 14:38
 * @description
 **/
@Service
public class SysPostConvertor {

    /**
     * 转换岗位信息
     *
     * @param sysPost
     * @return SysPostDTO
     */
    public static SysPostDTO convertToSysPostDTO(SysPost sysPost) {
        SysPostDTO postDTO = new SysPostDTO();
        BeanUtils.copyProperties(sysPost, postDTO);
        postDTO.setPostId(sysPost.getPostId().toString());
        return postDTO;
    }

    /**
     * 转换系统岗位DTO集合
     *
     * @param sysPosts
     * @return List<SysPostDTO>
     */
    public static List<SysPostDTO> convertToSysPostDTOList(List<SysPost> sysPosts) {
        return sysPosts.stream().map(tenantRole -> convertToSysPostDTO(tenantRole)).collect(Collectors.toList());
    }
}
