package com.nineya.dingtalk.message;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.nineya.tool.spi.BuilderAdapter;
import com.nineya.tool.validate.Assert;

public class ActionCardMessageBuild implements MessageBuild {
    private String title;
    private MarkdownConfiguration<ActionCardMessageBuild> markdownConfiguration;
    private BuilderAdapter<Map<String, Object>> actionCardBuilderAdapter;
    private final Map<String, Object> actionCardMap = new LinkedHashMap<>();
    private int btnOrientation = -1;

    /**
     * 传入markdown消息处理接口处理信息
     *
     * @param adapter 处理适配的接口
     * @return
     */
    public ActionCardMessageBuild text(BuilderAdapter<MarkdownConfiguration> adapter) {
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

    public ActionCardMessageBuild overall(BuilderAdapter<OverallActionCard> adapter) {
        this.actionCardBuilderAdapter = new OverallActionCard();
        adapter.apply((OverallActionCard) actionCardBuilderAdapter);
        return this;
    }

    public ActionCardMessageBuild independent(BuilderAdapter<IndependentActionCard> adapter) {
        this.actionCardBuilderAdapter = new IndependentActionCard();
        adapter.apply((IndependentActionCard) actionCardBuilderAdapter);
        return this;
    }

    public ActionCardMessageBuild() {
        this.markdownConfiguration = new MarkdownConfiguration<>(this);
    }

    public ActionCardMessageBuild title(String title) {
        this.title = title;
        return this;
    }

    /**
     * 按键是否竖直排列，直接存入map，避免在build时默认添加0的值
     *
     * @param vertical
     * @return
     */
    public ActionCardMessageBuild btnOrientation(boolean vertical) {
        this.btnOrientation = vertical ? 0 : 1;
        return this;
    }

    @Override
    public Message build() {
        Message message = new Message("actionCard");
        buildActionCard(message);
        return message;
    }

    private void buildActionCard(Message message) {
        Assert.notEmptyAllowed(title, "消息标题");
        Assert.notNullAllowed(markdownConfiguration, "消息内容");
        Assert.notNullAllowed(actionCardBuilderAdapter, "按钮信息");
        actionCardMap.put("title", title);
        actionCardMap.put("text", markdownConfiguration.build());
        if (btnOrientation != -1) {
            actionCardMap.put("btnOrientation", btnOrientation);
        }
        actionCardBuilderAdapter.apply(actionCardMap);
        message.add("actionCard", actionCardMap);
    }

    /**
     * 整体跳转
     */
    public class OverallActionCard implements BuilderAdapter<Map<String, Object>> {
        private String singleTitle;
        private String singleUrl;

        public OverallActionCard singleTitle(String singleTitle) {
            this.singleTitle = singleTitle;
            return this;
        }

        public OverallActionCard singleUrl(String singleUrl) {
            this.singleUrl = singleUrl;
            return this;
        }

        @Override
        public void apply(Map<String, Object> builder) {
            builder.put("singleTitle", singleTitle);
            builder.put("singleURL", singleUrl);
        }
    }

    /**
     * 独立跳转
     */
    public class IndependentActionCard implements BuilderAdapter<Map<String, Object>> {
        private List<Map<String, String>> btns = new ArrayList<>();

        public IndependentActionCard addBtn(String title, String actionUrl) {
            Map<String, String> btn = new LinkedHashMap<>(2);
            btn.put("title", title);
            btn.put("actionURL", actionUrl);
            btns.add(btn);
            return this;
        }

        @Override
        public void apply(Map<String, Object> builder) {
            builder.put("btns", btns);
        }
    }
}
