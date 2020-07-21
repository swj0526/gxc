package com.swj.config;

import com.swj.entity.TbEmployee;
import com.swj.service.EmployeeService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {
    @Autowired
    private EmployeeService employeeService;

    //Authorization:授权的意思
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了授权=>doGetAuthorizationInfo");
        Subject subject = SecurityUtils.getSubject();
        TbEmployee currentUser = (TbEmployee) subject.getPrincipal();


        return null;
    }
    //Authentication:认证的意思
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了认证=>doGetAuthenticationInfo");
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        TbEmployee userByName = employeeService.getUserByName(userToken.getUsername());
        if (null==userByName) {//判断是否存在当前用户
            return null;// 此处返回null  会抛在controller声明的异常 UnknownAccountException/IncorrectCredentialsException
        }
        //shiro 中的session,用来判断登录按钮
        Subject subject = SecurityUtils.getSubject();
        Session currentSession = subject.getSession();
        currentSession.setAttribute("user",userByName);

        return new SimpleAuthenticationInfo(
                userByName,  //放数据库的内容,可以是对象,可以使账户,用来传递
                userByName.getPassword(),//数据库密码shiro自动比对
                null,//放盐salt
                this.getName()
        );
    }
}
