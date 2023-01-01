package com.nineya.authentication.isp;

/**
 * 用户登录类型接口
 * 例如可以为 admin 和 user 用户分别实现，做登录类型区分
 *
 * @author 殇雪话诀别
 * @date 2022/12/17 1:16
 */
public interface LoginType {

    /**
     * 取得登录类型对应的领域名称
     * @return
     */
    String getRealmName();

    /**
     * 取得登录登录类型对应的header头名称
     * @return
     */
    String getHeaderName();

    /**
     * 允许通过 cookie登录
     * @return
     */
    boolean isAllowCookieLogin();
}
