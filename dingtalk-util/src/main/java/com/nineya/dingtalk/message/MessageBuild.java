package com.nineya.dingtalk.message;

public interface MessageBuild {
    Message build();

    static TextMessageBuild text() {
        return new TextMessageBuild();
    }
}
