package com.nineya.dingtalk.message;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.nineya.dingtalk.DingTalk.MAPPER;

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
        try {
            return MAPPER.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON 序列化失败", e);
        }
    }
}
