package com.webank.staging.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webank.staging.entity.SysDept;
import com.webank.staging.entity.SysUser;
import com.webank.staging.service.DeptService;
import com.webank.staging.service.HomeService;
import com.webank.staging.service.PermissionService;
import com.webank.staging.service.UserService;
import com.webank.staging.vo.resp.HomeRespVO;
import com.webank.staging.vo.resp.PermissionRespNode;
import com.webank.staging.vo.resp.UserInfoRespVO;

import java.util.List;

/**
 * 首页
 *
 *
 * 
 * 
 */
@Service
public class HomeServiceImpl implements HomeService {
    @Autowired
    private UserService userService;
    @Autowired
    private DeptService deptService;
    @Autowired
    private PermissionService permissionService;

    @Override
    public HomeRespVO getHomeInfo(String userId) {


        SysUser sysUser = userService.detailInfo("fcf34b56-a7a2-4719-9236-867495e74c31");
        UserInfoRespVO vo = new UserInfoRespVO();

        if (sysUser != null) {
            BeanUtils.copyProperties(sysUser, vo);
            SysDept sysDept = deptService.detailInfo(sysUser.getDeptId());
            if (sysDept != null) {
                vo.setDeptId(sysDept.getId());
                vo.setDeptName(sysDept.getName());
            }
        }

        List<PermissionRespNode> menus = permissionService.permissionTreeList("fcf34b56-a7a2-4719-9236-867495e74c31");

        HomeRespVO respVO = new HomeRespVO();
        respVO.setMenus(menus);
        respVO.setUserInfo(vo);

        return respVO;
    }
}
