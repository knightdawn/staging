package com.webank.staging.vo.resp;

import java.util.List;

import com.webank.staging.entity.SysRole;

import io.swagger.annotations.ApiModelProperty;

/**
 * UserOwnRoleRespVO
 *
 *
 * 
 * 
 */
public class UserOwnRoleRespVO {
	@ApiModelProperty("所有角色集合")
	private List<SysRole> allRole;
	@ApiModelProperty(value = "用户所拥有角色集合")
	private List<String> ownRoles;

	public List<SysRole> getAllRole() {
		return allRole;
	}

	public void setAllRole(List<SysRole> allRole) {
		this.allRole = allRole;
	}

	public List<String> getOwnRoles() {
		return ownRoles;
	}

	public void setOwnRoles(List<String> ownRoles) {
		this.ownRoles = ownRoles;
	}

}
