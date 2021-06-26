package com.nineya.dingtalk.autoconfigure;

import com.nineya.dingtalk.DingTalk;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 殇雪话诀别
 * 2021/6/26
 */
@Configuration
@EnableConfigurationProperties({DingtalkProperties.class})
public class DingtalkAutoConfiguration {

    @Bean
    public DingtalkService dingTalk(DingtalkProperties properties) {
        DingtalkService service = new DingtalkService(properties.getAccessToken(), properties.getSecret());
        service.setPhone(properties.getPhone());
        return service;
    }
}
