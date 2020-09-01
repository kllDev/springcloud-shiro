package com.kll.springcloud.service;

import com.kll.springcloud.entities.CommonResult;
import com.kll.springcloud.entities.UserLogin;
import com.kll.springcloud.util.Result;

import java.util.Set;

public interface UserLoginService {

    /**
     * 查询用户
     *
     * @param account
     * @return
     */
    UserLogin getUserByAccount(String account);


    /**
     * 通过用户名获取用户角色集合
     *
     * @param username 用户名
     * @return 角色集合
     */
    Set<String> getUserRolesSet(String username);

    /**
     * 通过用户名获取用户权限集合
     *
     * @param username 用户名
     * @return 权限集合
     */
    Set<String> getUserPermissionsSet(String username);

    /**
     * 校验用户是否有效
     *
     * @param userLogin
     * @return
     */
    CommonResult checkUserIsEffective(UserLogin userLogin);
}
