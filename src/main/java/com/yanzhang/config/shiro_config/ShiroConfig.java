package com.yanzhang.config.shiro_config;

import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.shiro.mgt.SecurityManager;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    /**
     * 验证，哪些路径可以访问，哪些路径可以直接过
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //设置登录路径
        shiroFilterFactoryBean.setLoginUrl("/no_login"); //前后端分离的项目，给用户返回提示信息
//        shiroFilterFactoryBean.setLoginUrl("/to_login");//普通web项目，跳转到login页面

        /**
         * 设置当用户没有权限的时候，所到达的路径, 该路径设置并不起作用
         * AOP: 通知。advice
         */
        shiroFilterFactoryBean.setUnauthorizedUrl("/no_auth");

        /**
         * HashMap 是无序的，所以不能用HashMap, 所以要用有序的实现LinkedHashMap
         */
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        //登录逻辑是必须要开放的
        filterChainDefinitionMap.put("/login","anon");

        //表示访问 /pub/开头的所有路径，可以直接访问
        filterChainDefinitionMap.put("/pub/**","anon");

        /**
         *  /** 表示访问所有的路径，/a/bc/xy
         *  authc  表示访问任何路径都需要认证
         *  anon(anonymous) 匿名，
         */
        filterChainDefinitionMap.put("/**","authc");  //将通配方式放在最后

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    /**
     * shiro中所有的东西都是SecurityManager来进行管理的。
     * @return
     */
    @Bean
    public SecurityManager securityManager(CustomerRealm customerRealm, CustomerSessionManager customerSessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置realm
        securityManager.setRealm(customerRealm);
        //如果说是单纯的web应用，不用定义 SessionManager, 做前后端分离的时候需要
        securityManager.setSessionManager(customerSessionManager);
        return securityManager;
    }

    /**
     * 将realm纳入到容器中
     * @return
     */
    @Bean
    public CustomerRealm realm(AddSaltCredentialsMatcher addSaltCredentialsMatcher) {
        CustomerRealm customerRealm = new CustomerRealm();
        customerRealm.setCredentialsMatcher(addSaltCredentialsMatcher);
        return customerRealm;
    }

    @Bean
    public AddSaltCredentialsMatcher addSaltCredentialsMatcher() {
        AddSaltCredentialsMatcher addSaltCredentialsMatcher = new AddSaltCredentialsMatcher();
        return addSaltCredentialsMatcher;
    }

    /**
     @Bean
     public CredentialsMatcher credentialsMatcher() {
     HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
     hashedCredentialsMatcher.setHashAlgorithmName("md5"); //设置加密方式
     hashedCredentialsMatcher.setHashIterations(2);  //作两次md5加密

     return hashedCredentialsMatcher;
     }
     */

    @Bean
    public CustomerSessionManager customerSessionManager() {
        return new CustomerSessionManager();
    }

    /**
     *  该类的作用是，实现注解的方式来设置权限
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    /**
     * 实现注解的方式来配置权限
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
