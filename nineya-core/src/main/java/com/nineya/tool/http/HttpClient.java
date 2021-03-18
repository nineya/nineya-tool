package com.nineya.tool.http;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * 创建http请求客户端
 */
public class HttpClient extends AbstractHttpRequest<HttpClient> {

    /**
     * 发送http请求方法
     *
     * @param request
     * @return
     */
    private static HttpResponse send(HttpRequest request) {
        PrintWriter out = null;
        try {
            HttpURLConnection connect = (HttpURLConnection) request.getUrl().openConnection();
            for (Map.Entry<String, String> entry : request.getHeader().entrySet()) {
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
                // 获取URLConnection对象对应的输出流
                out = new PrintWriter(connect.getOutputStream());
                out.println(request.getBody());
                // flush输出流的缓冲
                out.flush();
            }
            // 定义BufferedReader输入流来读取URL的响应
            InputStream inputStream = connect.getInputStream();
            int buffSize = inputStream.available();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(buffSize);
            byte[] bytes = new byte[buffSize];
            int size = 0;
            while ((size = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, size);
            }
            byte[] results = outputStream.toByteArray();
            outputStream.close();
            inputStream.close();
            return new HttpResponse()
                .setBody(results)
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
     * 将
     *
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
     * 将两个map合并到一个新的map中
     * 在新的 map 中 addMap 中的参数内容将覆盖 sourceMap 中的内容
     *
     * @param sourceMap
     * @param addMap
     * @param <T>
     * @param <V>
     * @return
     */
    private <T, V> Map<T, V> coverCollection(Map<T, V> sourceMap, Map<T, V> addMap) {
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
