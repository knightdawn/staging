package com.webank.staging.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.webank.staging.vo.req.PageReqVO;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 字典管理
 *
 *
 * 
 * 
 */
@TableName("sys_dict")
public class SysDictEntity extends PageReqVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId("id")
	private String id;

	/**
	 * 字典名称
	 */
	@TableField("name")
	private String name;

	/**
	 * 备注
	 */
	@TableField("remark")
	private String remark;

	/**
	 * 创建时间
	 */
	@TableField(value = "create_time", fill = FieldFill.INSERT)
	private Date createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
