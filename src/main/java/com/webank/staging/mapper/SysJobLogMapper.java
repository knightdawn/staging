package com.webank.staging.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.webank.staging.entity.SysJobLogEntity;

import org.apache.ibatis.annotations.Mapper;

/**
 * 定时任务日志、 Mapper
 *
 *
 * 
 * 
 */
@Mapper
public interface SysJobLogMapper extends BaseMapper<SysJobLogEntity> {
	
}
