package com.nineya.authentication.isp;

import org.apache.shiro.authc.BearerToken;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义的 token 实体类，需要在 token 中添加登录类型对应的 realmName 信息，为了后续的判断
 * request 用于在授权或认证阶段添加 Attribute 参数
 *
 * @author 殇雪话诀别
 * 2021/2/18
 */
public class JwtToken extends BearerToken {
    private final String realmName;
    private final HttpServletRequest request;

    public JwtToken(String realmName, HttpServletRequest request, String token) {
        super(token, request.getRemoteAddr());
        this.realmName = realmName;
        this.request = request;
    }

    public String getRealmName() {
        return realmName;
    }

    public HttpServletRequest getRequest() {
        return request;
    }
}
