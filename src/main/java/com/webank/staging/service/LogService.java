package com.webank.staging.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.webank.staging.entity.SysLog;

import java.util.List;

/**
 * 系统日志
 *
 *
 * 
 * 
 */
public interface LogService {

    IPage<SysLog> pageInfo(SysLog vo);

    void deleted(List<String> logIds);
}
