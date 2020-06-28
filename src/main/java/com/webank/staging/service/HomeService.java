package com.webank.staging.service;

import com.webank.staging.vo.resp.HomeRespVO;

/**
 * 首页
 *
 *
 * 
 * 
 */
public interface HomeService {

    HomeRespVO getHomeInfo(String userId);
}
