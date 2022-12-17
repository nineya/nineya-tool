package com.nineya.authentication.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.nineya.authentication.isp.TokenStore;
import org.apache.commons.collections.CollectionUtils;

import java.util.Date;
import java.util.Set;

/**
 * jwt处理类
 *
 * @author 殇雪话诀别
 * 2020/11/29
 */
public class AuthTokenService {
    /**
     * jwt 加密算法
     */
    private final Algorithm algorithm;
    private final JWTVerifier verifier;
    private final AuthRedisService authRedisService;

    public static final String USER_TOKEN_PREFIX = "USER_TOKEN_";
    /**
     * 误差，redis存储的是long，精确到毫秒，jwt精确到秒，可能和jwt的值有1000ms的误差
     */
    private static final int DEVIATION = 1000;

    public AuthTokenService(AuthRedisService authRedisService, String jwtSecret) {
        this.authRedisService = authRedisService;
        algorithm = Algorithm.HMAC256(jwtSecret);
        verifier = JWT.require(algorithm).build();
    }

    /**
     * 创建token的存储key
     *
     * @param issuerName 当前token对应的发行人名称
     * @param tokenType  token类型
     * @param id         用户id
     * @return
     */
    private static String buildTokenKey(String issuerName, String tokenType, long id) {
        return issuerName + "_" + tokenType + "_" + id;
    }

    /**
     * 创建用户token，并将token创建时间存入
     * 该接口仅该管理员等非Role角色的登录方式创建token
     *
     * @param issuerName 当前token对应的发行人名称
     * @param tokenType  token类型
     * @param idName     存储用户id标识的名称，用于做jwt负载的key
     * @param timeout    token的过期时间，如果为 -1，则表示token永远有效，单位为秒
     * @param id         用户id
     * @return token字符串
     */
    public String createToken(String issuerName, String tokenType, String idName, int timeout, long id) {
        Date createTime = new Date();
        // 有毫秒误差，数值比jwt取出来的大0-1000
        authRedisService.set(buildTokenKey(issuerName, tokenType, id), createTime.getTime(), timeout);
        return JWT.create()
                .withIssuer(issuerName)
                .withClaim(idName, id)
                .withIssuedAt(createTime)
                .sign(algorithm);
    }

    /**
     * 根据token类型和用户id创建指定token，jwt中不设置到期时间，token是否到期以redis中是否有存储为准
     *
     * @param tokenStore token类型
     * @param id         用户id
     * @return
     */
    public String createToken(TokenStore tokenStore, long id) {
        return createToken(tokenStore.getIssuerName(), tokenStore.getTokenType(), tokenStore.getIdName(), tokenStore.getTimeout(), id);
    }

    /**
     * 校验token合法性，解析token信息
     *
     * @param token
     * @return
     */
    public DecodedJWT verifyToken(String token) {
        try {
            return verifier.verify(token);
        } catch (Exception e) {
            throw new TokenExpiredException("token 解析失败");
        }
    }

    /**
     * 校验token是否有效，只允许一个token有效，如果token创建时间大于redis的时间，那么这个token是无效的
     *
     * @param timeout    token的失效时间，当小于等于0时表示token没有时间限制，不需要刷新
     * @param id         token所属的用户id
     * @param createTime token中存储的创建时间
     * @return true表示通过校验，false表示登录状态失效了
     */
    public boolean valid(String issuerName, String tokenType, int timeout, long id, long createTime) {
        String key = buildTokenKey(issuerName, tokenType, id);
        Object value = authRedisService.get(key);
        if (value == null) {
            return false;
        }
        // 如果超过这个误差，则表示该token被更新过，原先的就不能生效了
        if ((long) value - createTime > DEVIATION) {
            return false;
        }
        if (timeout > 0) {
            authRedisService.expire(key, timeout);
        }
        return true;
    }

    /**
     * 校验token是否有效，只允许一个token有效，如果token创建时间大于redis的时间，那么这个token是无效的
     *
     * @param tokenStore token类型
     * @param id         token所属的用户id
     * @param createTime token中存储的创建时间
     * @return
     */
    public boolean valid(TokenStore tokenStore, long id, long createTime) {
        return valid(tokenStore.getIssuerName(), tokenStore.getTokenType(), tokenStore.getTimeout(), id, createTime);
    }

    /**
     * 清除指定用户的所有token
     *
     * @param id
     */
    public void clearToken(String tokenType, long id) {
        Set<String> keys = authRedisService.keys(buildTokenKey("*", tokenType, id));
        if (CollectionUtils.isNotEmpty(keys)) {
            authRedisService.del(keys.toArray(new String[0]));
        }
    }

    /**
     * 已知key名称，直接通过key名称删除token
     *
     * @param key
     */
    public void clearToken(String key) {
        authRedisService.del(key);
    }

    /**
     * 根据token类型和用户id删除token
     *
     * @param tokenStore token类型
     * @param id         用户id
     */
    public void clearToken(TokenStore tokenStore, long id) {
        authRedisService.del(buildTokenKey(tokenStore.getIssuerName(), tokenStore.getTokenType(), id));
    }
}
