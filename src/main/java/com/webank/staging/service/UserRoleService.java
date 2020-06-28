package com.webank.staging.service;

import java.util.List;

import com.webank.staging.vo.req.UserRoleOperationReqVO;

/**
 * 用户角色 服务类
 *
 *
 * 
 * 
 */
public interface UserRoleService {

    int removeByRoleId(String roleId);

    List<String> getRoleIdsByUserId(String userId);


    void addUserRoleInfo(UserRoleOperationReqVO vo);

    int removeByUserId(String userId);


    List<String> getUserIdsByRoleIds(List<String> roleIds);

    List<String> getUserIdsByRoleId(String roleId);
}
