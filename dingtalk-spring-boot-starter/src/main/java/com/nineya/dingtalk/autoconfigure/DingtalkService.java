package com.nineya.dingtalk.autoconfigure;

import com.nineya.dingtalk.DingTalk;
import com.nineya.dingtalk.message.MessageBuild;

/**
 * @author 殇雪话诀别
 * 2020/12/11
 */
public class DingtalkService {
    private final DingTalk dingTalk;
    private String phone;

    public DingtalkService(String accessToken, String secret) {
        this.dingTalk = DingTalk.instance(accessToken, secret);
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 发送一条消息
     *
     * @param build
     */
    public void send(MessageBuild build) {
        dingTalk.send(build.build());
    }

    /**
     * 取得手机号
     *
     * @return
     */
    public String getPhone() {
        return phone;
    }
}
