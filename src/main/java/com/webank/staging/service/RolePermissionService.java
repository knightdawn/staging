package com.webank.staging.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.webank.staging.entity.SysRolePermission;
import com.webank.staging.vo.req.RolePermissionOperationReqVO;

import java.util.List;

/**
 * 角色权限关联
 *
 *
 * 
 * 
 */
public interface RolePermissionService extends IService<SysRolePermission> {

    int removeByRoleId(String roleId);

    List<String> getPermissionIdsByRoles(List<String> roleIds);

    void addRolePermission(RolePermissionOperationReqVO vo);

    int removeByPermissionId(String permissionId);

    List<String> getRoleIds(String permissionId);
    List<String> getPermissionIdsByRoleId(String roleId);

}
