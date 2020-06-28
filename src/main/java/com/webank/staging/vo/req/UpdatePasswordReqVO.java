package com.webank.staging.vo.req;

import io.swagger.annotations.ApiModelProperty;

/**
 * UpdatePasswordReqVO
 *
 *
 * 
 * 
 */
public class UpdatePasswordReqVO {
	@ApiModelProperty(value = "旧密码")
	private String oldPwd;
	@ApiModelProperty(value = "新密码")
	private String newPwd;

	public String getOldPwd() {
		return oldPwd;
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

}
