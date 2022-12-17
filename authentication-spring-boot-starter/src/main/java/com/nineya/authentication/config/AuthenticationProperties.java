package com.nineya.authentication.config;

import com.nineya.authentication.isp.AuthLoginType;
import com.nineya.authentication.isp.LoginType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author 殇雪话诀别
 * @date 2022/12/17 1:08
 */
@Data
@ConfigurationProperties(prefix = "nineya.authentication")
public class AuthenticationProperties {
    /**
     * jwt secret秘钥
     */
    private String jwtSecret;
    /**
     * 登录url
     */
    private String loginUrl;
    /**
     * 登录成功url
     */
    private String successUrl;
    /**
     * 认证未通过跳转url
     */
    private String unauthorizedUrl = "/error";
    /**
     * 领域登录类型
     */
    private List<AuthLoginType> loginTypes;
    /**
     * 授权uri过滤规则
     */
    private List<AuthFilterContext> authFilterContexts;

    /**
     * 授权uri过滤规则
     */
    @Data
    public static class AuthFilterContext {
        /**
         * uri上下文
         */
        private String uri;
        /**
         * 过滤器类型，anon: 允许访问，token：需要token校验，forbid：拒绝访问
         */
        private String type;
    }
}
