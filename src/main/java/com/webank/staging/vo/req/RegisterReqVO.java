package com.webank.staging.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * RegisterReqVO
 *
 *
 * 
 * 
 */
public class RegisterReqVO {
	@ApiModelProperty(value = "账号")
	@NotBlank(message = "账号不能为空")
	private String username;
	@ApiModelProperty(value = "密码")
	@NotBlank(message = "密码不能为空")
	private String password;
	@ApiModelProperty(value = "电话")
	private String phone;
	@ApiModelProperty(value = "真实名称")
	private String realName;
	@ApiModelProperty(value = "昵称")
	private String nickName;
	@ApiModelProperty(value = "邮箱")
	private String email;
	@ApiModelProperty(value = "性别(1.男 2.女) ")
	private Integer sex;
	@ApiModelProperty(value = "创建来源(1.web 2.android 3.ios )")
	private Integer createWhere;
	@ApiModelProperty(value = "所属机构")
	@NotBlank(message = "所属机构不能为空")
	private String deptId;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getCreateWhere() {
		return createWhere;
	}

	public void setCreateWhere(Integer createWhere) {
		this.createWhere = createWhere;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

}
