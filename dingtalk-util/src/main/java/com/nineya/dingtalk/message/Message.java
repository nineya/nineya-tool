package com.nineya.dingtalk.message;

import com.alibaba.fastjson.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

public class Message {
    private Map<String, Object> message;

    Message(String msgtype) {
        this.message = new LinkedHashMap<>();
        this.message.put("msgtype", msgtype);
    }

    public void add(String name, Object value) {
        message.put(name, value);
    }

    public void remove(String name) {
        message.remove(name);
    }

    public Object get(String name) {
        return message.get(name);
    }

    public String toJsonString() {
        return JSONObject.toJSONString(message);
    }
}
