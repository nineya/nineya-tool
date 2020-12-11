package com.nineya.dingtalk.message;

import java.util.*;

public class AtBuild<T extends MessageBuild> {
    private Set<String> atMobiles;
    private boolean atAll;

    public T atMobile(String mobile) {
        if (atMobiles == null) {
            atMobiles = new LinkedHashSet<>();
        }
        atMobiles.add(mobile);
        return (T) this;
    }

    public T atMobiles(Collection<String> atMobiles) {
        if (this.atMobiles == null) {
            this.atMobiles = new LinkedHashSet<>(atMobiles);
            return (T) this;
        }
        this.atMobiles.addAll(atMobiles);
        return (T) this;
    }

    public T atAll() {
        this.atAll = true;
        return (T) this;
    }

    protected void buildAt(Message message) {
        Map<String, Object> at = new LinkedHashMap<>(2);
        at.put("atMobiles", atMobiles);
        at.put("isAtAll", atAll);
        message.add("at", at);
    }
}
