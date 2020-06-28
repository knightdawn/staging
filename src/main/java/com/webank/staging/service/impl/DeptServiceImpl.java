package com.webank.staging.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.webank.staging.common.exception.BusinessException;
import com.webank.staging.common.exception.code.BaseResponseCode;
import com.webank.staging.common.utils.Constant;
import com.webank.staging.entity.SysDept;
import com.webank.staging.entity.SysUser;
import com.webank.staging.mapper.SysDeptMapper;
import com.webank.staging.service.DeptService;
import com.webank.staging.service.UserService;
import com.webank.staging.vo.resp.DeptRespNodeVO;

/**
 * 部门
 *
 *
 * 
 * 
 */
@Service
public class DeptServiceImpl implements DeptService {
	
	private static final Logger log = LoggerFactory.getLogger(DeptServiceImpl.class);
	
    @Autowired
    private SysDeptMapper sysDeptMapper;
    @Autowired
    private UserService userService;

    @Override
    public SysDept addDept(SysDept vo) {
        String relationCode;
        String deptCode = this.getNewDeptCode();
        SysDept parent = sysDeptMapper.selectById(vo.getPid());
        if (vo.getPid().equals("0")) {
            relationCode = deptCode;
        } else if (null == parent) {
            log.error("传入的 pid:{}不合法", vo.getPid());
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        } else {
            relationCode = parent.getRelationCode() + deptCode;
        }
        vo.setDeptNo(deptCode);
        vo.setRelationCode(relationCode);
        vo.setStatus(1);
        sysDeptMapper.insert(vo);
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDept(SysDept vo) {

        SysDept sysDept = sysDeptMapper.selectById(vo.getId());
        if (null == sysDept) {
            log.error("传入 的 id:{}不合法", vo.getId());
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        SysDept update = new SysDept();
        BeanUtils.copyProperties(vo, update);
        sysDeptMapper.updateById(update);
        /** 说明层级发生了变化 */
        if (!StringUtils.isEmpty(vo.getPid()) && !vo.getPid().equals(sysDept.getPid())) {
            SysDept parent = sysDeptMapper.selectById(vo.getPid());
            if (!vo.getPid().equals("0") && null == parent) {
                log.error("传入 的 pid:{}不合法", vo.getId());
                throw new BusinessException(BaseResponseCode.DATA_ERROR);
            }
            SysDept oldParent = sysDeptMapper.selectById(sysDept.getPid());
            String oldRelationCode;
            String newRelationCode;
            /** 根目录降到其他目录 */
            if (sysDept.getPid().equals("0")) {
                oldRelationCode = sysDept.getDeptNo();
                newRelationCode = parent.getRelationCode() + sysDept.getDeptNo();
            } else if (vo.getPid().equals("0")) { // 其他目录升级到跟目录
                oldRelationCode = sysDept.getRelationCode();
                newRelationCode = sysDept.getDeptNo();
            } else {
                oldRelationCode = oldParent.getRelationCode();
                newRelationCode = parent.getRelationCode();
            }
            LambdaQueryWrapper<SysDept> wrapper = new LambdaQueryWrapper<>();
            wrapper.likeLeft(SysDept::getDeptNo, sysDept.getDeptNo());
            List<SysDept> list = sysDeptMapper.selectList(wrapper);
            list.parallelStream().forEach(entity -> {
                String relationCode = entity.getRelationCode().replace(oldRelationCode, newRelationCode);
                entity.setRelationCode(relationCode);
                sysDeptMapper.updateById(entity);
            });
        }
    }

    @Override
    public SysDept detailInfo(String id) {
        return sysDeptMapper.selectById(id);
    }

    @Override
    public void deleted(String id) {
        SysDept sysDept = sysDeptMapper.selectById(id);
        if (null == sysDept) {
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.likeRight("relation_code", sysDept.getRelationCode());
        List<String> deptIds = sysDeptMapper.selectObjs(queryWrapper);
        List<SysUser> list = userService.getUserListByDeptIds(deptIds);
        if (!list.isEmpty()) {
            throw new BusinessException(BaseResponseCode.NOT_PERMISSION_DELETED_DEPT);
        }
        sysDeptMapper.deleteById(id);
    }

    @Override
    public List<DeptRespNodeVO> deptTreeList(String deptId) {
        List<SysDept> list;
        if (StringUtils.isEmpty(deptId)) {
            list = sysDeptMapper.selectList(new QueryWrapper<SysDept>());
        } else {
            SysDept sysDept = sysDeptMapper.selectById(deptId);
            if (sysDept == null) {
                throw new BusinessException(BaseResponseCode.DATA_ERROR);
            }
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.likeRight("relation_code", sysDept.getRelationCode());
            List<String> childIds = sysDeptMapper.selectObjs(queryWrapper);

            queryWrapper = new QueryWrapper();
            queryWrapper.notIn("id", childIds);
            list = sysDeptMapper.selectList(queryWrapper);
        }
        // 默认加一个顶级方便新增顶级部门
        DeptRespNodeVO respNodeVO = new DeptRespNodeVO();
        respNodeVO.setTitle("默认顶级部门");
        respNodeVO.setId("0");
        respNodeVO.setSpread(true);
        respNodeVO.setChildren(getTree(list));
        List<DeptRespNodeVO> result = new ArrayList<>();
        result.add(respNodeVO);
        return result;
    }

    private List<DeptRespNodeVO> getTree(List<SysDept> all) {
        List<DeptRespNodeVO> list = new ArrayList<>();
        for (SysDept sysDept : all) {
            if (sysDept.getPid().equals("0")) {
                DeptRespNodeVO deptTree = new DeptRespNodeVO();
                BeanUtils.copyProperties(sysDept, deptTree);
                deptTree.setTitle(sysDept.getName());
                deptTree.setSpread(true);
                deptTree.setChildren(getChild(sysDept.getId(), all));
                list.add(deptTree);
            }
        }
        return list;
    }

    private List<DeptRespNodeVO> getChild(String id, List<SysDept> all) {
        List<DeptRespNodeVO> list = new ArrayList<>();
        for (SysDept sysDept : all) {
            if (sysDept.getPid().equals(id)) {
                DeptRespNodeVO deptTree = new DeptRespNodeVO();
                BeanUtils.copyProperties(sysDept, deptTree);
                deptTree.setTitle(sysDept.getName());
                deptTree.setChildren(getChild(sysDept.getId(), all));
                list.add(deptTree);
            }
        }
        return list;
    }

    @Override
    public List<SysDept> selectAll() {
        List<SysDept> list = sysDeptMapper.selectList(new QueryWrapper<>());
        for (SysDept sysDept : list) {
            SysDept parent = sysDeptMapper.selectById(sysDept.getPid());
            if (parent != null) {
                sysDept.setPidName(parent.getName());
            }
        }
        return list;
    }

    //获取新的部门编码
    public String getNewDeptCode() {
        LambdaQueryWrapper<SysDept> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(SysDept::getDeptNo);
        //获取所有的deptCode
        List<Object> deptCodes = sysDeptMapper.selectObjs(lambdaQueryWrapper);
        AtomicReference<Integer> maxDeptCode = new AtomicReference<>(0);

        //遍历获取最大的DeptCode
        deptCodes.stream().forEach(o -> {
            String str = String.valueOf(o);
            if (str.length() >= 7) {
                Integer one = Integer.parseInt(str.substring(str.length()-5));
                if (one > maxDeptCode.get()) {
                    maxDeptCode.set(one);
                }
            }
        });

        return padRight(maxDeptCode.get() + 1, 6, "0");
    }


    /**
     * 右补位，左对齐
     *
     * @param len    目标字符串长度
     * @param alexin 补位字符
     * @return 目标字符串
     * 以alexin 做为补位
     * @pparam oriStr  原字符串
     */
    public static String padRight(int oriStr, int len, String alexin) {
        StringBuffer str = new StringBuffer();
        int strlen = String.valueOf(oriStr).length();
        if (strlen < len) {
            for (int i = 0; i < len - strlen; i++) {
                str.append(alexin);
            }
        }
        str.append(oriStr);
        return Constant.DEPT_TYPE + str.toString();
    }
}
