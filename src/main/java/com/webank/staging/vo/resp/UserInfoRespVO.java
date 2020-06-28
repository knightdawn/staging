package com.webank.staging.vo.resp;

import io.swagger.annotations.ApiModelProperty;

/**
 * UserInfoRespVO
 *
 *
 * 
 * 
 */
public class UserInfoRespVO {
	@ApiModelProperty(value = "用户id")
	private String id;
	@ApiModelProperty(value = "账号")
	private String username;
	@ApiModelProperty(value = "手机号")
	private String phone;
	@ApiModelProperty(value = "昵称")
	private String nickName;
	@ApiModelProperty(value = "真实姓名")
	private String realName;
	@ApiModelProperty(value = "所属机构id")
	private String deptId;
	@ApiModelProperty(value = "所属机构名称")
	private String deptName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

}
