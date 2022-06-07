package com.example.layer.sys.feign;

import com.example.layer.sys.engine.domain.service.SysApiLogDomainService;
import org.springlayer.core.log.feign.SysOperLogClient;
import org.springlayer.core.log.model.LogApi;
import org.springlayer.core.tool.api.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author Hzhi
 * @Date 2022-04-23 16:32
 * @description
 **/
@Slf4j
@RestController
public class SysApiLogFeign implements SysOperLogClient {

    @Resource
    private SysApiLogDomainService sysApiLogDomainService;

    /**
     * 保存API操作日志
     *
     * @param logApi api日志
     * @return R<Boolean>
     */
    @Override
    public R<Boolean> saveApiLog(LogApi logApi) {
        log.info("-------------------------执行日志记录-----------------------");
        return R.status(sysApiLogDomainService.save(logApi));
    }
}
