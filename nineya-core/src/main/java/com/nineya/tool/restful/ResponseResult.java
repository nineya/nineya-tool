package com.nineya.tool.restful;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nineya.tool.text.CheckText;

/**
 * @author 殇雪话诀别
 * 2020/11/12
 */
public class ResponseResult<T> {
    /**
     * 是否响应成功
     */
    private boolean error;
    /**
     * 响应状态码
     */
    private int code;
    /**
     * 错误信息
     */
    private String message;
    /**
     * 响应数据内容
     */
    private T data;

    public ResponseResult() {
    }

    public ResponseResult(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public ResponseResult(boolean error, int code, String message) {
        this.error = error;
        this.code = code;
        this.message = message;
    }

    public ResponseResult(boolean error, int code, String message, T data) {
        this.error = error;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 创建错误的响应结果
     *
     * @param message 返回的错误消息内容
     * @return ResponseResult对象
     */
    public static <T> ResponseResult<T> failure(StatusCode code, String message) {
        return new ResponseResult<>(true, code.getCode(), message);
    }

    public static <T> ResponseResult<T> failure(StatusCode code) {
        return new ResponseResult<>(true, code.getCode(), code.getMessage());
    }

    /**
     * 创建成功的响应
     *
     * @param data
     * @return
     */
    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<>(false, NetworkStatus.OK.getCode(), NetworkStatus.OK.getMessage(), data);
    }

    public boolean isError() {
        return error;
    }

    public ResponseResult<?> setError(boolean error) {
        this.error = error;
        return this;
    }

    public int getCode() {
        return code;
    }

    public ResponseResult<?> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ResponseResult<?> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public ResponseResult<?> setData(T data) {
        this.data = data;
        return this;
    }

    /**
     * 将响应信息转map输出
     *
     * @return map信息
     */
    public Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("error", error);
        map.put("code", code);
        if (!CheckText.isEmpty(message)) {
            map.put("message", message);
        }
        if (!CheckText.isEmpty(data)) {
            map.put("data", data);
        }
        return map;
    }

    @Override
    public String toString() {
        return "ResponseResult{" +
                "error=" + error +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
