package com.example.layer.sys.feign;

import com.example.layer.sys.engine.service.SysAclModuleMenuService;
import com.example.layer.sys.client.UserAuthClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhihou
 * @date 2022/04/11 14:09
 * @description
 */
@Slf4j
@RestController
public class UserAuthFeign implements UserAuthClient {

    @Resource
    private SysAclModuleMenuService sysAclModuleMenuService;

    /**
     * 网关鉴权降级到接口鉴权
     *
     * @param userId
     * @param uri
     * @return List<String>
     */
    @Override
    public List<String> authenticationTenant(Long userId, String uri) {
        List<Long> aclModuleIdCollection = sysAclModuleMenuService.getAclModuleIdCollection(userId, uri);
       return aclModuleIdCollection.stream().map(String::valueOf).collect(Collectors.toList());
    }
}
