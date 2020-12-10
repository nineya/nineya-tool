package com.nineya.dingtalk.message;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AtBuild<T extends MessageBuild>{
    private List<String> atMobiles;
    private boolean atAll;

    public T atMobile(String mobile) {
        if (atMobiles == null) {
            atMobiles = new ArrayList<>();
        }
        atMobiles.add(mobile);
        return (T) this;
    }

    public T atMobiles(List<String> atMobiles) {
        this.atMobiles = atMobiles;
        return (T) this;
    }

    public T atAll(boolean atAll) {
        this.atAll = atAll;
        return (T) this;
    }

    protected void buildAt(Message message) {
        Map<String, Object> at = new LinkedHashMap<>(2);
        at.put("atMobiles", atMobiles);
        at.put("isAtAll", atAll);
        message.add("at", at);
    }
}
