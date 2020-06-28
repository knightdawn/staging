package com.webank.staging.service;

import java.util.List;

import com.webank.staging.entity.SysDept;
import com.webank.staging.vo.resp.DeptRespNodeVO;

/**
 * 部门
 *
 *
 * 
 * 
 */
public interface DeptService {

    SysDept addDept(SysDept vo);

    void updateDept(SysDept vo);

    SysDept detailInfo(String id);

    void deleted(String id);

    List<DeptRespNodeVO> deptTreeList(String deptId);

    List<SysDept> selectAll();
}
