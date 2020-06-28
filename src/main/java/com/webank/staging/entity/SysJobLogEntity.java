package com.webank.staging.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.webank.staging.vo.req.PageReqVO;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.util.Date;

/**
 * 定时任务日志
 *
 */
@TableName("sys_job_log")
public class SysJobLogEntity extends PageReqVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 任务日志id
	 */
	@TableId("id")
	private String id;

	/**
	 * 任务id
	 */
	@TableField("job_id")
	private String jobId;

	/**
	 * spring bean名称
	 */
	@TableField("bean_name")
	private String beanName;

	/**
	 * 参数
	 */
	@TableField("params")
	private String params;

	/**
	 * 任务状态    0：成功    1：失败
	 */
	@TableField("status")
	private Integer status;

	/**
	 * 失败信息
	 */
	@TableField("error")
	private String error;

	/**
	 * 耗时(单位：毫秒)
	 */
	@TableField("times")
	private Integer times;

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

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
