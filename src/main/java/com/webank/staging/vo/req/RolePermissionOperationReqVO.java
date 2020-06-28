package com.webank.staging.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * RolePermissionOperationReqVO
 *
 *
 * 
 * 
 */
public class RolePermissionOperationReqVO {
    @ApiModelProperty(value = "角色id")
    @NotBlank(message = "角色id不能为空")
    private String roleId;
    @ApiModelProperty(value = "菜单权限集合")
    @NotEmpty(message = "菜单权限集合不能为空")
    private List<String> permissionIds;
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public List<String> getPermissionIds() {
		return permissionIds;
	}
	public void setPermissionIds(List<String> permissionIds) {
		this.permissionIds = permissionIds;
	}
    
}
