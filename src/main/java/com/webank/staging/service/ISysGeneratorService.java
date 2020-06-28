package com.webank.staging.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.webank.staging.entity.SysGenerator;

/**
 * 代码生成
 *
 *
 * 
 * 
 */
public interface ISysGeneratorService {


    IPage<SysGenerator> selectAllTables(Page page, SysGenerator vo);

    byte[] generatorCode(String[] split);
}
