package com.nineya.tool.http;

import com.nineya.tool.restful.Methods;

import java.net.MalformedURLException;
import java.net.URL;

public class HttpRequest extends HttpEntity<HttpRequest> {
    private String method;
    private URL url;
    private String body;

    private HttpRequest(String method, String url) {
        this.method = method;
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static HttpRequest sendPost(String url) {
        return new HttpRequest(Methods.POST, url);
    }

    public static HttpRequest sendGet(String url) {
        return new HttpRequest(Methods.GET, url);
    }

    public String getMethod() {
        return method;
    }

    public URL getUrl() {
        return url;
    }

    public HttpRequest setBody(String body) {
        this.body = body;
        return this;
    }

    public String getBody() {
        return body;
    }
}
