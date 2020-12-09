package com.nineya.tool.http;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

public class HttpClient extends HttpEntity<HttpClient> {

    private static HttpResponse send(HttpRequest request) {
        PrintWriter out = null;
        try {
            HttpURLConnection connect = (HttpURLConnection) request.getUrl().openConnection();
            for (Map.Entry<String, String> entry : request.getHeader().entrySet()) {
                System.out.println(entry.getKey());
                connect.setRequestProperty(entry.getKey(), entry.getValue());
            }
            if (request.getTimeout() != 0) {
                connect.setReadTimeout(request.getTimeout());
            }
            // 设置请求格式
            connect.setRequestMethod(request.getMethod());
            // 发送请求body参数
            if (request.getBody() != null) {
                connect.setDoInput(true);
                connect.setDoOutput(true);
                System.out.println(request.getBody());
                // 获取URLConnection对象对应的输出流
                out = new PrintWriter(connect.getOutputStream());
                out.println(request.getBody());
                // flush输出流的缓冲
                out.flush();
            }
            // 定义BufferedReader输入流来读取URL的响应
            InputStream inputStream = connect.getInputStream();

            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            return new HttpResponse()
                .setBody(bytes)
                .setStatus(connect.getResponseCode());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return null;
    }

    /**
     * 执行参数处理
     * @param request
     * @return
     */
    public HttpResponse execute(HttpRequest request) {
        request.setHeader(coverCollection(getHeader(), request.getHeader()));
        request.setParams(coverCollection(getParams(), request.getParams()));
        if (request.getTimeout() == 0) {
            request.setTimeout(getTimeout());
        }
        return send(request);
    }

    /**
     *
     * @param sourceMap
     * @param addMap
     * @param <T>
     * @param <V>
     * @return
     */
    private <T,V> Map<T, V> coverCollection(Map<T, V> sourceMap, Map<T, V> addMap) {
        if (sourceMap == null) {
            if (addMap == null) {
                return new HashMap<>();
            }
            return new HashMap<>(addMap);
        }
        if (addMap == null) {
            return new HashMap<>(sourceMap);
        }
        Map<T, V> map = new HashMap<>(sourceMap);
        map.putAll(addMap);
        return map;
    }
}