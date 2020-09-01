package com.kll.springcloud.shiro;

import com.kll.springcloud.entities.UserLogin;
import com.kll.springcloud.service.UserLoginService;
import com.kll.springcloud.service.impl.RedisService;
import com.kll.springcloud.util.JwtToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Set;


/**
 * 用户登录鉴权和获取用户授权
 */
@Component
@Slf4j
public class ShiroRealm extends AuthorizingRealm {
    @Autowired
    @Lazy
    private UserLoginService userLoginService;
    @Autowired
    @Lazy
    private RedisService redisService;

    /**
     * 必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;

    }


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("————权限认证 [ roles、permissions]————");
        UserLogin sysUser = null;
        String username = null;
        if (principals != null) {
            sysUser = (UserLogin) principals.getPrimaryPrincipal();
            username = sysUser.getUserName();
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        // 设置用户拥有的角色集合，比如“admin,test”
        Set<String> roleSet = userLoginService.getUserRolesSet(username);
        info.setRoles(roleSet);

        // 设置用户拥有的权限集合，比如“sys:role:add,sys:user:add”
        Set<String> permissionSet = userLoginService.getUserPermissionsSet(username);
        info.addStringPermissions(permissionSet);
        return info;
    }


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        return null;
    }
}
