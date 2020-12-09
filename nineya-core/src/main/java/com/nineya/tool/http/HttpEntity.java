package com.nineya.tool.http;

import java.util.HashMap;
import java.util.Map;

public class HttpEntity<T extends HttpEntity> {
    private Map<String, String> header;
    private Map<String, String> params;
    private int timeout;

    public T addHeader(String name, String value) {
        if (header == null) {
            header = new HashMap<>();
        }
        header.put(name, value);
        return (T)this;
    }

    public T addParams(String name, String value) {
        if (params == null) {
            params = new HashMap<>();
        }
        params.put(name, value);
        return (T)this;
    }

    public T setHeader(Map<String, String> header) {
        this.header = header;
        return (T)this;
    }

    public T setParams(Map<String, String> params) {
        this.params = params;
        return (T)this;
    }

    public T setContentType(String type) {
        return addHeader("Content-Type", type);
    }

    public T setContentEncoding(String encode) {
        return addHeader("Content-Encoding", encode);
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public T setTimeout(int timeout) {
        this.timeout = timeout;
        return (T)this;
    }

    public int getTimeout() {
        return timeout;
    }
}
