package com.webank.staging.vo.req;

import com.baomidou.mybatisplus.annotation.TableField;

import io.swagger.annotations.ApiModelProperty;

/**
 * PageReqVO
 *
 */
public class PageReqVO {
    @ApiModelProperty(value = "第几页")
    @TableField(exist = false)
    private int page=1;

    @ApiModelProperty(value = "分页数量")
    @TableField(exist = false)
    private int limit=10;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
    
    
}
