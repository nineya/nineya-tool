package com.nineya.dingtalk.message;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nineya.tool.validate.Assert;

public class MarkdownMessageBuild extends AtBuild<MarkdownMessageBuild> implements MessageBuild {
    private String title;
    private MarkdownConfiguration markdownConfiguration;

    public MarkdownMessageBuild() {
        markdownConfiguration = new MarkdownConfiguration(this);
    }

    public MarkdownMessageBuild title(String title) {
        this.title = title;
        return this;
    }

    /**
     * 传入markdown消息处理接口处理信息
     *
     * @param adapter 处理适配的接口
     * @return
     */
    public MarkdownMessageBuild text(BuilderAdapter<MarkdownConfiguration> adapter) {
        adapter.apply(markdownConfiguration);
        return this;
    }

    /**
     * 获取markdown对其进行配置
     *
     * @return
     */
    public MarkdownConfiguration text() {
        return markdownConfiguration;
    }

    @Override
    public Message build() {
        Message message = new Message("markdown");
        buildMarkdown(message);
        buildAt(message);
        return message;
    }

    private void buildMarkdown(Message message) {
        Assert.notEmptyAllowed(title, "消息标题");
        String text = markdownConfiguration.build();
        Assert.notEmptyAllowed(text, "消息内容");
        Map<String, Object> markdown = new LinkedHashMap<>(2);
        markdown.put("title", title);
        markdown.put("text", text);
        message.add("markdown", markdown);
    }
}
