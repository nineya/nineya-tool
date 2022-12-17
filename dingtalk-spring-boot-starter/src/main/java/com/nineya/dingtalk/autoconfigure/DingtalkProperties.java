package com.nineya.dingtalk.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 殇雪话诀别
 * 2021/5/31
 */
@Data
@ConfigurationProperties(prefix = "nineya.dingtalk")
public class DingtalkProperties {
    private String phone;
    private String accessToken;
    private String secret;
}
