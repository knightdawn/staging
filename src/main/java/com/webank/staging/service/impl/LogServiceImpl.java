package com.webank.staging.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.webank.staging.entity.SysLog;
import com.webank.staging.mapper.SysLogMapper;
import com.webank.staging.service.LogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 系统日志
 *
 *
 * 
 * 
 */
@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public IPage<SysLog> pageInfo(SysLog vo) {

        Page page = new Page(vo.getPage(), vo.getLimit());
        QueryWrapper queryWrapper = new QueryWrapper();
        if (!StringUtils.isEmpty(vo.getUsername()) ) {
            queryWrapper.like("username", vo.getUsername());
        }
        if (!StringUtils.isEmpty(vo.getOperation()) ) {
            queryWrapper.like("operation", vo.getOperation());
        }
        if (!StringUtils.isEmpty(vo.getStartTime()) ) {
            queryWrapper.gt("create_time", vo.getStartTime());
        }
        if (!StringUtils.isEmpty(vo.getEndTime()) ) {
            queryWrapper.lt("create_time", vo.getEndTime());
        }
        queryWrapper.orderByDesc("create_time");
        return sysLogMapper.selectPage(page, queryWrapper);
    }

    @Override
    public void deleted(List<String> logIds) {
        sysLogMapper.deleteBatchIds(logIds);
    }
}
