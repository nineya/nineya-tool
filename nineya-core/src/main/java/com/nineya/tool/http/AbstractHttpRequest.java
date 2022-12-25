package com.nineya.tool.http;

import java.util.HashMap;
import java.util.Map;

/**
 * 发送http请求，请求内容实体，包括请求头实体，请求参数实体，超时时间
 * @param <T>
 */
public class AbstractHttpRequest<T extends AbstractHttpRequest> {
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
        return addHeader(HttpHandlerName.CONTENT_TYPE, type);
    }

    public T setContentEncoding(String encode) {
        return addHeader(HttpHandlerName.CONTENT_ENCODING, encode);
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
