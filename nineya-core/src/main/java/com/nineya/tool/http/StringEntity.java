package com.nineya.tool.http;

public class StringEntity implements HttpEntity {
    private String contentType;
    private String contentEncoding;
    private final String content;

    public StringEntity(String content) {
        this.content = content;
    }

    public StringEntity(String content, String encode) {
        this.content = content;
        this.contentEncoding = encode;
    }

    @Override
    public HttpEntity setContentType(String type) {
        this.contentType = type;
        return this;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public HttpEntity setContentEncoding(String encode) {
        this.contentEncoding = encode;
        return this;
    }

    @Override
    public String getContentEncoding() {
        return contentEncoding;
    }

    @Override
    public String getContent() {
        return content;
    }
}
