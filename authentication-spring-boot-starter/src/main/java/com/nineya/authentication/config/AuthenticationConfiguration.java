package com.nineya.authentication.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nineya.authentication.filter.AuthenticationForbidFilter;
import com.nineya.authentication.filter.AuthenticationTokenFilter;
import com.nineya.authentication.isp.LoginType;
import com.nineya.authentication.realm.NineyaModularRealmAuthenticator;
import com.nineya.authentication.realm.NineyaModularRealmAuthorizer;
import com.nineya.authentication.service.AuthRedisService;
import com.nineya.authentication.service.AuthTokenService;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import javax.servlet.Filter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 配置类
 * @author 殇雪话诀别
 * @date 2022/12/17 1:04
 */
@Configuration
@EnableConfigurationProperties({AuthenticationProperties.class})
public class AuthenticationConfiguration {

    @Resource
    private AuthenticationProperties authenticationProperties;
    @Resource
    private ObjectMapper objectMapper;

    /**
     * 配置代理，没有配置将会导致注解不生效
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean({DefaultAdvisorAutoProxyCreator.class})
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }

    /**
     * 配置代理，没有配置将会导致注解不生效
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean({AuthorizationAttributeSourceAdvisor.class})
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    /**
     * 防止key前面有其他字符内容，导致模糊查询等无法正常查询
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean(name = "authenticationRedisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // key的序列化采用StringRedisSerializer
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean
    @ConditionalOnMissingBean({AuthRedisService.class})
    public AuthRedisService authRedisService(RedisConnectionFactory redisConnectionFactory) {
        return new AuthRedisService(redisTemplate(redisConnectionFactory));
    }

    @Bean
    @ConditionalOnMissingBean({AuthTokenService.class})
    public AuthTokenService authTokenService(AuthRedisService authRedisService) {
        return new AuthTokenService(authRedisService, authenticationProperties.getJwtSecret());
    }

    /**
     * 针对多realm，用于认证阶段
     */
    @Bean
    @ConditionalOnMissingBean({ModularRealmAuthenticator.class})
    public ModularRealmAuthenticator modularRealmAuthenticator() {
        //自己重写的ModularRealmAuthenticator
        NineyaModularRealmAuthenticator modularRealmAuthenticator = new NineyaModularRealmAuthenticator();
        modularRealmAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        return modularRealmAuthenticator;
    }

    /**
     * 针对多realm，用于授权阶段
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean({NineyaModularRealmAuthorizer.class})
    public ModularRealmAuthorizer modularRealmAuthorizer() {
        return new NineyaModularRealmAuthorizer();
    }

    /**
     * 权限管理，配置主要是Realm的管理认证，同时可以配置缓存管理等
     *
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager(List<Realm> realms) {
        DefaultWebSecurityManager webSecurityManager = new DefaultWebSecurityManager();
        webSecurityManager.setAuthenticator(modularRealmAuthenticator());
        webSecurityManager.setAuthorizer(modularRealmAuthorizer());
        //realm管理，必须在两个modular之后，因为会对这两个对象进行设值
        webSecurityManager.setRealms(realms);
        return webSecurityManager;
    }

    /**
     * Filter工厂，设置对应的过滤条件和跳转条件
     *
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(List<Realm> realms) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager(realms));
        // 过滤url设置与顺序有关
        Map<String, String> filterUrlMap = new LinkedHashMap<>();
        // 设置上下文过滤
        for (AuthenticationProperties.AuthFilterContext context : authenticationProperties.getAuthFilterContexts()) {
            filterUrlMap.put(context.getUri(), context.getType());
        }
        //登录
        shiroFilterFactoryBean.setLoginUrl(authenticationProperties.getLoginUrl());
        // 错误页面，认证不通过跳转
        shiroFilterFactoryBean.setUnauthorizedUrl(authenticationProperties.getUnauthorizedUrl());
        // 登录成功跳转
        shiroFilterFactoryBean.setSuccessUrl(authenticationProperties.getSuccessUrl());
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterUrlMap);
        shiroFilterFactoryBean.setFilters(new LinkedHashMap<String, Filter>() {{
            put("token", authenticationTokenFilter());
            put("forbid", authenticationForbidFilter());
        }});
        return shiroFilterFactoryBean;
    }

    /**
     * 不应该将过滤器的实现注册为bean，否则会导致Filter过滤器顺序混乱，导致抛出异常
     * 如果一定要注册为 Bean，可以使用 Order 指定优先级，还未尝试过
     *
     * @return
     */
    private AuthenticationTokenFilter authenticationTokenFilter() {
        List<LoginType> loginTypes = new ArrayList<>(authenticationProperties.getLoginTypes());
        return new AuthenticationTokenFilter(objectMapper, loginTypes);
    }

    /**
     * 拒绝访问的过滤器
     *
     * @return
     */
    private AuthenticationForbidFilter authenticationForbidFilter() {
        return new AuthenticationForbidFilter(objectMapper);
    }
}
