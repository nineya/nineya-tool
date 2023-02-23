package com.nineya.authentication.isp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 默认的领域登录类型
 *
 * @author 殇雪话诀别
 * @date 2022/12/17 1:31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthLoginType implements LoginType {
    private String realmName;
    private String headerName;
    private boolean allowCookieLogin;
}
