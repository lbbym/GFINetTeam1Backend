package com.citi_team_one.tps.auth;

import com.citi_team_one.tps.model.User;
import com.citi_team_one.tps.service.UserService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MyRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    private static final Logger LOGGER = LogManager.getLogger(MyRealm.class);

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = JWTUtil.getUsername(principals.toString());
        User user = userService.findByName(username);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        simpleAuthorizationInfo.addRole(user.getId().substring(0,1));
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        String username = JWTUtil.getUsername(token);
        if (username == null) {
            throw new AuthenticationException("token invalid");
        }

        User userBean = userService.findByName(username);
        if (userBean == null) {
            throw new AuthenticationException("User didn't existed!");
        }

        if (! JWTUtil.verify(token, username, userBean.getPwd())) {
            System.out.println(token);
            System.out.println(username);
            System.out.println(userBean.getPwd());
            throw new AuthenticationException("Username or password error");
        }

        return new SimpleAuthenticationInfo(token, token, "my_realm");
    }
}
