package com.webank.staging.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.webank.staging.common.aop.annotation.LogAnnotation;
import com.webank.staging.common.utils.DataResult;
import com.webank.staging.service.UserRoleService;
import com.webank.staging.vo.req.UserRoleOperationReqVO;

import javax.validation.Valid;

/**
 * 用户和角色关联
 *
 *
 * 
 * 
 */
@RequestMapping("/sys")
@RestController
@Api(tags = "组织管理-用户和角色关联接口")
public class UserRoleController {
    @Autowired
    private UserRoleService userRoleService;

    @PostMapping("/user/role")
    @ApiOperation(value = "修改或者新增用户角色接口")
    @LogAnnotation(title = "用户和角色关联接口", action = "修改或者新增用户角色")
    public DataResult operationUserRole(@RequestBody @Valid UserRoleOperationReqVO vo) {
        userRoleService.addUserRoleInfo(vo);
        return DataResult.success();
    }
}
