package com.nineya.tool.http;

import java.io.UnsupportedEncodingException;

import com.nineya.tool.charset.Charsets;

/**
 * http响应内容封装
 */
public class HttpResponse {
    private int status;
    private byte[] body;
    private String message;

    public HttpResponse setBody(byte[] body) {
        this.body = body;
        return this;
    }

    public String getBody() {
        return getBody(Charsets.UTF_8);
    }

    public String getBody(String charset) {
        try {
            return new String(body, charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public HttpResponse setStatus(int status) {
        this.status = status;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public HttpResponse setMessage(String message) {
        this.message = message;
        return this;
    }
}
