package com.webank.staging.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.webank.staging.common.exception.BusinessException;
import com.webank.staging.common.exception.code.BaseResponseCode;
import com.webank.staging.entity.SysRole;
import com.webank.staging.mapper.SysRoleMapper;
import com.webank.staging.service.HttpSessionService;
import com.webank.staging.service.PermissionService;
import com.webank.staging.service.RolePermissionService;
import com.webank.staging.service.RoleService;
import com.webank.staging.service.UserRoleService;
import com.webank.staging.vo.req.RolePermissionOperationReqVO;
import com.webank.staging.vo.resp.PermissionRespNode;

/**
 * 角色
 *
 *
 * 
 * 
 */
@Service
public class RoleServiceImpl implements RoleService {

	private static final Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);
	
	@Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private HttpSessionService httpSessionService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SysRole addRole(SysRole vo) {

        vo.setStatus(1);
        sysRoleMapper.insert(vo);
        if (null != vo.getPermissions() && !vo.getPermissions().isEmpty()) {
            RolePermissionOperationReqVO reqVO = new RolePermissionOperationReqVO();
            reqVO.setRoleId(vo.getId());
            reqVO.setPermissionIds(vo.getPermissions());
            rolePermissionService.addRolePermission(reqVO);
        }

        return vo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateRole(SysRole vo) {
        SysRole sysRole = sysRoleMapper.selectById(vo.getId());
        if (null == sysRole) {
            log.error("传入 的 id:{}不合法", vo.getId());
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        sysRoleMapper.updateById(vo);
        rolePermissionService.removeByRoleId(sysRole.getId());
        if (!CollectionUtils.isEmpty(vo.getPermissions())) {
            RolePermissionOperationReqVO reqVO = new RolePermissionOperationReqVO();
            reqVO.setRoleId(sysRole.getId());
            reqVO.setPermissionIds(vo.getPermissions());
            rolePermissionService.addRolePermission(reqVO);
            // 刷新权限
            httpSessionService.refreshRolePermission(sysRole.getId());
        }
    }

    @Override
    public SysRole detailInfo(String id) {
        SysRole sysRole = sysRoleMapper.selectById(id);
        if (sysRole == null) {
            log.error("传入 的 id:{}不合法", id);
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        List<PermissionRespNode> permissionRespNodes = permissionService.selectAllByTree();
        Set<String> checkList =
                new HashSet<>(rolePermissionService.getPermissionIdsByRoleId(sysRole.getId()));
        setChecked(permissionRespNodes, checkList);
        sysRole.setPermissionRespNodes(permissionRespNodes);
        return sysRole;
    }

    private void setChecked(List<PermissionRespNode> list, Set<String> checkList) {
        for (PermissionRespNode node : list) {
            if (checkList.contains(node.getId())
                    && (node.getChildren() == null || node.getChildren().isEmpty())) {
                node.setChecked(true);
            }
            setChecked((List<PermissionRespNode>) node.getChildren(), checkList);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deletedRole(String id) {
        sysRoleMapper.deleteById(id);

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select("user_id").eq("role_id", id);
        rolePermissionService.removeByRoleId(id);
        userRoleService.removeByRoleId(id);
        // 刷新权限
        httpSessionService.refreshRolePermission(id);
    }

    @Override
    public IPage<SysRole> pageInfo(SysRole vo) {
        Page page = new Page(vo.getPage(), vo.getLimit());
        QueryWrapper queryWrapper = new QueryWrapper();
        if (!StringUtils.isEmpty(vo.getName())) {
            queryWrapper.like("name", vo.getName());
        }
        if (!StringUtils.isEmpty(vo.getStartTime())) {
            queryWrapper.gt("create_time", vo.getStartTime());
        }
        if (!StringUtils.isEmpty(vo.getEndTime())) {
            queryWrapper.lt("create_time", vo.getEndTime());
        }
        if (!StringUtils.isEmpty(vo.getStatus())) {
            queryWrapper.eq("status", vo.getStatus());
        }
        queryWrapper.orderByDesc("create_time");
        return sysRoleMapper.selectPage(page, queryWrapper);
    }

    @Override
    public List<SysRole> getRoleInfoByUserId(String userId) {

        List<String> roleIds = userRoleService.getRoleIdsByUserId(userId);
        if (roleIds.isEmpty()) {
            return null;
        }
        return sysRoleMapper.selectBatchIds(roleIds);
    }

    @Override
    public List<String> getRoleNames(String userId) {

        List<SysRole> sysRoles = getRoleInfoByUserId(userId);
        if (null == sysRoles || sysRoles.isEmpty()) {
            return null;
        }
        List<String> list = sysRoles.stream().map(SysRole::getName).collect(Collectors.toList());
        return list;
    }

    @Override
    public List<SysRole> selectAllRoles() {
        return sysRoleMapper.selectList(Wrappers.emptyWrapper());
    }
}