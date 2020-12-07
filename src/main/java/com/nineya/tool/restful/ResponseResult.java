package com.nineya.tool.restful;

import com.nineya.tool.text.CheckText;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 殇雪话诀别
 * 2020/11/12
 */
public class ResponseResult<T> {
    /**
     * 是否响应成功
     */
    private Boolean error;
    /**
     * 响应状态码
     */
    private Integer status;
    /**
     * 错误信息
     */
    private String message;
    /**
     * 响应数据内容
     */
    private T data;

    public ResponseResult() {
        this(StatusCode.SUCCESS);
    }

    public ResponseResult(StatusCode resultCode) {
        this.status = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.error = resultCode != StatusCode.SUCCESS;
    }

    public ResponseResult(StatusCode resultCode, T data) {
        this(resultCode);
        this.data = data;
    }

    /**
     * 创建错误的响应结果
     *
     * @param message 返回的错误消息内容
     * @return ResponseResult对象
     */
    public static ResponseResult failure(StatusCode code, String message) {
        return new ResponseResult(code).setMessage(message);
    }

    /**
     * 创建成功的响应
     *
     * @param data
     * @return
     */
    public static <T> ResponseResult<T> access(T data) {
        return new ResponseResult<>(StatusCode.SUCCESS, data);
    }

    /**
     * 创建服务器错误的api对象
     *
     * @return
     */
    public static ResponseResult serverError() {
        return new ResponseResult(StatusCode.SERVER_ERROR);
    }

    public Boolean getError() {
        return error;
    }

    public ResponseResult setError(Boolean error) {
        this.error = error;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public ResponseResult setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ResponseResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public ResponseResult setData(T data) {
        this.data = data;
        return this;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("error", error);
        map.put("status", status);
        if (!CheckText.isEmpty(message)) {
            map.put("message", message);
        }
        if (!CheckText.isEmpty(data)) {
            map.put("data", data);
        }
        return map;
    }

    /**
     * 将响应信息转map输出
     * @return map信息
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ResponseResult{");
        sb.append("error=").append(error);
        sb.append(", status=").append(status);
        sb.append(", message='").append(message).append('\'');
        sb.append(", data=").append(data);
        sb.append('}');
        return sb.toString();
    }
}
