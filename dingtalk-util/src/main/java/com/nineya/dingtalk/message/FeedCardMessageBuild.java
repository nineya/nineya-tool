package com.nineya.dingtalk.message;

import java.util.*;

public class FeedCardMessageBuild implements MessageBuild {
    private List<Map<String, String>> links = new ArrayList<>();

    public FeedCardMessageBuild addLink(String title, String messageUrl, String picUrl) {
        Map<String, String> map = new LinkedHashMap<>(3);
        map.put("title", title);
        map.put("messageURL", messageUrl);
        map.put("picURL", picUrl);
        links.add(map);
        return this;
    }

    @Override
    public Message build() {
        Message message = new Message("feedCard");
        buildFeedCard(message);
        return message;
    }

    private void buildFeedCard(Message message) {
        message.add("feedCard", new AbstractMap.SimpleEntry("links", links));
    }
}
