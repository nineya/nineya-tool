package com.nineya.dingtalk.message;

import com.nineya.tool.validate.Assert;

import java.util.*;

public class TextMessageBuild extends AtBuild<TextMessageBuild> implements MessageBuild {
    private String content;

    public TextMessageBuild content(String content) {
        this.content = content;
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
}
