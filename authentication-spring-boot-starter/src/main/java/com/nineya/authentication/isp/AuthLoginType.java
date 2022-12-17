package com.nineya.authentication.isp;

import lombok.Data;

/**
 * 默认的领域登录类型
 *
 * @author 殇雪话诀别
 * @date 2022/12/17 1:31
 */
@Data
public class AuthLoginType implements LoginType {
    private String realmName;
    private String headerName;
}
