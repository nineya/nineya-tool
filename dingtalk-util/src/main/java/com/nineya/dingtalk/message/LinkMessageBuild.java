package com.nineya.dingtalk.message;

import com.nineya.tool.validate.Assert;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * link类型消息构架器
 * link消息包含一个跳转链接，点击消息可跳转链接
 */
public class LinkMessageBuild implements MessageBuild {
    /**
     * 消息标题
     */
    private String title;
    /**
     * 消息内容
     */
    private String text;
    /**
     * 点击消息跳转的URL
     */
    private String messageUrl;
    /**
     * 消息图片的URL，非必填
     */
    private String picUrl;

    public LinkMessageBuild title(String title) {
        this.title = title;
        return this;
    }

    public LinkMessageBuild text(String text) {
        this.text = text;
        return this;
    }

    public LinkMessageBuild messageUrl(String messageUrl) {
        this.messageUrl = messageUrl;
        return this;
    }

    public LinkMessageBuild picUrl(String picUrl) {
        this.picUrl = picUrl;
        return this;
    }

    /**
     * 构造消息link部分内容
     * @param message 消息主体
     */
    private void buildLink(Message message) {
        Assert.notAllowedEmpty(title, "消息标题");
        Assert.notAllowedEmpty(text, "消息内容");
        Assert.notAllowedEmpty(messageUrl, "消息跳转URL");
        Map<String, Object> link = new LinkedHashMap<>();
        link.put("title", title);
        link.put("text", text);
        link.put("messageUrl", messageUrl);
        link.put("picUrl", picUrl);
        message.add("link", link);
    }

    @Override
    public Message build() {
        Message message = new Message("link");
        buildLink(message);
        return message;
    }
}
