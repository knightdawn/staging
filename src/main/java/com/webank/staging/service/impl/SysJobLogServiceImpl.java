package com.webank.staging.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.webank.staging.entity.SysJobLogEntity;
import com.webank.staging.mapper.SysJobLogMapper;
import com.webank.staging.service.SysJobLogService;

/**
 * 定时任务 服务类
 *
 *
 * 
 * 
 */
@Service("sysJobLogService")
public class SysJobLogServiceImpl extends ServiceImpl<SysJobLogMapper, SysJobLogEntity> implements SysJobLogService {


}