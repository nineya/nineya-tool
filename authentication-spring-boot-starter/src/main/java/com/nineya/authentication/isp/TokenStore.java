package com.nineya.authentication.isp;

/**
 * token类型的接口，需要单独指定token在redis中存储key或存储时间的则应该单独实现接口
 * 例如，可以为admin和user这样不同类型的用户做区分
 * 可以为同一类型的用户中的不同渠道做类型区分，如
 *
 * @author 殇雪话诀别
 * @date 2022/12/17 1:40
 */
public interface TokenStore {
    /**
     * 当前token对应的发行人名称
     *
     * @return
     */
    String getIssuerName();

    String getTokenType();

    /**
     * 当前token的主键id名称
     *
     * @return
     */
    String getIdName();

    /**
     * token的过期时间，如果为 -1，则表示token永远有效
     * 单位为秒
     */
    int getTimeout();
}
