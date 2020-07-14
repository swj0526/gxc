package com.swj.config;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class shiroConfig {

    //1.创建realm对象,交给spring管理,
    @Bean
    public UserRealm userRealm() {
        UserRealm userRealm = new UserRealm();
        return userRealm;
    }
    //2.配置DefaultWebSecurityManager用来管理realm对象
    @Bean("securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);//管理Realm
        return securityManager;
    }
    //3.配置shiroFilterFactoryBean过滤器
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager webSecurityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(webSecurityManager);
        //设置shiro 内置的过滤器
        /*
         认证过滤器：
        anon(不认证也可以访问)，authcBasic
        authc(必须认证后才可访问)
        user:必须拥有记住我功能
         授权过滤器：
         perms（指定资源需要哪些权限才可以访问）
         Roles:拥有某个角色权限才能访问
        * */
        //添加过滤器链
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/toUpdate", "authc");
        filterMap.put("/toAdd", "authc");
        filterMap.put("/toUserList", "authc");
        //授权 正常情况下 未授权,会跳转到未授权的页面
        filterMap.put("/toUpdate", "perms[user:update]");
        filterMap.put("/toAdd", "perms[user:add]");
        filterMap.put("/toUserList", "perms[user:select]");

        //支持通配符
        /* filterMap.put("/user/*", "authc");*/

        //登录拦截
        bean.setFilterChainDefinitionMap(filterMap);
        //拦截后 没有登录跳转的页面
        bean.setLoginUrl("/toLogin");
        //未授权后,没有权限跳转的页面
        bean.setUnauthorizedUrl("/toUnauth");
        return bean;
    }
}
