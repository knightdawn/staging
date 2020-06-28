package com.webank.staging.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.webank.staging.entity.SysDictDetailEntity;

import java.util.List;

/**
 * 数据字典 服务类
 *
 *
 * 
 * 
 */
public interface SysDictDetailService extends IService<SysDictDetailEntity> {

    IPage<SysDictDetailEntity> listByPage(Page page, String dictId);
}

