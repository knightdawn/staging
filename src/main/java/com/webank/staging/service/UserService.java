package com.webank.staging.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.webank.staging.entity.SysUser;
import com.webank.staging.vo.req.*;
import com.webank.staging.vo.resp.LoginRespVO;
import com.webank.staging.vo.resp.UserOwnRoleRespVO;

import java.util.List;

/**
 * 用户 服务类
 *
 *
 * 
 * 
 */
public interface UserService extends IService<SysUser> {

    String register(RegisterReqVO vo);

    LoginRespVO login(LoginReqVO vo);

    void updateUserInfo(SysUser vo, String operationId);


    IPage<SysUser> pageInfo(SysUser vo);

    SysUser detailInfo(String userId);

    void addUser(SysUser vo);

    void logout();

    void updatePwd(UpdatePasswordReqVO vo,String userId);

    List<SysUser> getUserListByDeptIds(List<String> deptIds);

    void deletedUsers(List<String> userIds,String operationId);

    UserOwnRoleRespVO getUserOwnRole(String userId);

    void setUserOwnRole(String userId,List<String> roleIds);

    void updateUserInfoMy(SysUser vo, String userId);
}
