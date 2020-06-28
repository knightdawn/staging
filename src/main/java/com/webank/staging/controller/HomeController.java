package com.webank.staging.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webank.staging.common.utils.DataResult;
import com.webank.staging.service.HomeService;
import com.webank.staging.service.HttpSessionService;
import com.webank.staging.vo.resp.HomeRespVO;

import javax.servlet.http.HttpServletRequest;

/**
 * 首页
 *
 */
@RestController
@RequestMapping("/sys")
@Api(tags = "首页数据")
public class HomeController {
    @Autowired
    private HomeService homeService;
    @Autowired
    private HttpSessionService httpSessionService;

    @GetMapping("/home")
    @ApiOperation(value = "获取首页数据接口")
    public DataResult<HomeRespVO> getHomeInfo(HttpServletRequest request) {
        /**
         * 通过access_token拿userId
         */
        String userId = httpSessionService.getCurrentUserId();
        DataResult<HomeRespVO> result = DataResult.success();
        result.setData(homeService.getHomeInfo(userId));
        return result;
    }
}
