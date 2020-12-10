package com.nineya.dingtalk.message;

import com.nineya.tool.validate.Assert;

import java.util.*;

public class TextMessageBuild implements MessageBuild {
    private String content;
    private List<String> atMobiles;
    private boolean atAll;

    public TextMessageBuild content(String content) {
        this.content = content;
        return this;
    }

    public TextMessageBuild atMobile(String mobile) {
        if (atMobiles == null) {
            atMobiles = new ArrayList<>();
        }
        atMobiles.add(mobile);
        return this;
    }

    public TextMessageBuild atMobiles(List<String> atMobiles) {
        this.atMobiles = atMobiles;
        return this;
    }

    public TextMessageBuild atAll(boolean atAll) {
        this.atAll = atAll;
        return this;
    }

    @Override
    public Message build() {
        Message message = new Message("text");
        buildText(message);
        buildAt(message);
        return message;
    }

    private void buildText(Message message) {
        Assert.notAllowedEmpty(content, "消息内容");
        message.add("text", new AbstractMap.SimpleEntry<>("content", content));
    }

    private void buildAt(Message message) {
        Map<String, Object> at = new LinkedHashMap<>();
        at.put("atMobiles", atMobiles);
        at.put("isAtAll", atAll);
        message.add("at", at);
    }
}
