package com.webank.staging.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.webank.staging.entity.SysJobEntity;

import java.util.List;

/**
 * 定时任务 服务类
 *
 *
 * 
 * 
 */
public interface SysJobService extends IService<SysJobEntity> {

    void saveJob(SysJobEntity sysJob);

    void updateJobById(SysJobEntity sysJob);

    void delete(List<String> ids);

    void run(List<String> ids);

    void pause(List<String> ids);

    void resume(List<String> ids);

    void updateBatch(List<String> ids, int status);
}

