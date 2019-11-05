package com.yanzhang.config.shiro_config;

import com.yanzhang.pojo.Permission;
import com.yanzhang.pojo.User;
import com.yanzhang.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CustomerRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;

    /**
     *  获取其权限列表, 当用户每一次去做权限校验，都需要查询，极大的降低了数据库的性能，需要作缓存。
     *  采用的缓存中间件是 Redis
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String name = (String)principals.getPrimaryPrincipal();

        User user = userService.userPermissionInfo(name);

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        Set<Permission> permissionSet = user.getPermissionSet();

        List<String> permissionList = new ArrayList<>();

        permissionSet.forEach(p -> permissionList.add(p.getPermission()));

        simpleAuthorizationInfo.addStringPermissions(permissionList);

        return simpleAuthorizationInfo;
    }

    /**
     * 当用户在登录逻辑中调用  subject.login(token), 该方法会被触发，将token传入过来
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String name = (String)token.getPrincipal(); //用户名
//        String password = (String)token.getCredentials(); //密码

        User user = userService.userSimpleInfo(name);

        if (null == user || null == user.getPassword() || "".equals(user.getPassword())) {
            return null;   //验证失败
        }

        /**
         * 方法接收四个参数：
         * 1. 用户名
         * 2. 密码
         * 3. 盐值，必须是ByteSource类型
         * 4. Realm名字，不用管
         */
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(name, user.getPassword(), ByteSource.Util.bytes(user.getSalt().getBytes()), this.getClass().getName());

        return authenticationInfo;
    }
}
