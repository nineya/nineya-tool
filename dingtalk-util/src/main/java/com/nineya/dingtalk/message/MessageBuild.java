package com.nineya.dingtalk.message;

import com.nineya.tool.spi.Builder;

public interface MessageBuild extends Builder<Message> {

    /**
     * 创建text类型消息的构建器
     * @return 消息构建器
     */
    static TextMessageBuild text() {
        return new TextMessageBuild();
    }

    /**
     * 创建link类型消息的构建器
     * @return 消息构建器
     */
    static LinkMessageBuild link() {
        return new LinkMessageBuild();
    }

    /**
     * 创建markdown类型消息的构建器
     * @return 消息构建器
     */
    static MarkdownMessageBuild markdown() {
        return new MarkdownMessageBuild();
    }

    /**
     * 创建actionCard类型消息的构建器
     * @return 消息构建器
     */
    static ActionCardMessageBuild actionCard() {
        return new ActionCardMessageBuild();
    }

    /**
     * 创建feedCard类型消息的构建器
     * @return 消息构建器
     */
    static FeedCardMessageBuild feedCard() {
        return new FeedCardMessageBuild();
    }
}
