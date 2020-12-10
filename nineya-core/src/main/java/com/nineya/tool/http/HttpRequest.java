package com.nineya.tool.http;

import com.nineya.tool.restful.Methods;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpRequest extends HttpEntity<HttpRequest> {
    private String method;
    private String url;
    private String body;

    private HttpRequest(String method, String url) {
        this.method = method;
        this.url = url;
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
        try {
            Map<String, String> params = getParams();
            if (params== null || params.isEmpty()) {
                return new URL(url);
            }
            String paramsStr = getParams().entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&", url.contains("?") ? "&" : "?", ""));
            System.out.println(url + paramsStr);
            return new URL(url + paramsStr);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public HttpRequest setBody(String body) {
        this.body = body;
        return this;
    }

    public String getBody() {
        return body;
    }
}
