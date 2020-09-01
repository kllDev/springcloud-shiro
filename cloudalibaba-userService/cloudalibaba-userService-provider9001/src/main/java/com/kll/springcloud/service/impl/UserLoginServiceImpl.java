package com.kll.springcloud.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.kll.springcloud.entities.CommonResult;
import com.kll.springcloud.entities.Permission;
import com.kll.springcloud.entities.UserLogin;
import com.kll.springcloud.mapper.UserLoginMapper;
import com.kll.springcloud.mapper.UserPermissionMapper;
import com.kll.springcloud.mapper.UserRoleMapper;
import com.kll.springcloud.service.UserLoginService;
import com.kll.springcloud.util.CommonUtils;
import com.kll.springcloud.util.constant.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@Slf4j
public class UserLoginServiceImpl implements UserLoginService {
    @Autowired
    private UserLoginMapper userLoginMapper;

    @Autowired
    private UserPermissionMapper userPermissionMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;


    @Override
    public UserLogin getUserByAccount(String account) {
        System.out.println(account);
        return  userLoginMapper.getUserByAccount(account);
    }

    /**
     * 通过用户名获取用户角色集合
     *
     * @param username 用户名
     * @return 角色集合
     */
    @Override
    @Cacheable(value = CommonConstant.LOGIN_USER_RULES_CACHE, key = "'Roles_'+#username")
    public Set<String> getUserRolesSet(String username) {
        // 查询用户拥有的角色集合
        List<String> roles = userRoleMapper.getRoleByUserName(username);
        log.info("-------通过数据库读取用户拥有的角色Rules------username： " + username + ",Roles size: " + (roles == null ? 0 : roles.size()));
        return new HashSet<>(roles);
    }

    /**
     * 通过用户名获取用户权限集合
     *
     * @param username 用户名
     * @return 权限集合
     */
    @Override
    @Cacheable(value = CommonConstant.LOGIN_USER_RULES_CACHE, key = "'Permissions_'+#username")
    public Set<String> getUserPermissionsSet(String username) {
        Set<String> permissionSet = new HashSet<>();
        List<Permission> permissionList = userPermissionMapper.queryByUser(username);
        for (Permission po : permissionList) {
//			if (oConvertUtils.isNotEmpty(po.getUrl())) {
//				permissionSet.add(po.getUrl());
//			}
            if (CommonUtils.isNotEmpty(po.getPerms())) {
                permissionSet.add(po.getPerms());
            }
        }
        log.info("-------通过数据库读取用户拥有的权限Perms------username： " + username + ",Perms size: " + (permissionSet == null ? 0 : permissionSet.size()));
        return permissionSet;
    }

    @Override
    public CommonResult<JSONObject> checkUserIsEffective(UserLogin userLogin) {
        CommonResult<JSONObject> result = new CommonResult<>();
        result.setCode(200);
        //情况1：根据用户信息查询，该用户不存在
        if (userLogin == null) {
            result.setCode(500);
            result.setMessage("该用户不存在，请注册");
            return result;
        }
        //情况2：根据用户信息查询，该用户已注销
        if (CommonConstant.DEL_FLAG_1.toString().equals(userLogin.getDelFlag())) {
            result.setCode(500);
            result.setMessage("该用户已注销");
            return result;
        }
        //情况3：根据用户信息查询，该用户已被删除
        if (CommonConstant.USER_FREEZE.equals(userLogin.getDelFlag())) {
            result.setCode(500);
            result.setMessage("该用户已被删除");
            return result;
        }
        return result;
    }
}
