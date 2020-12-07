package com.nineya.tool.restful;

/**
 * @author 殇雪话诀别
 * 2020/11/12
 */
public enum StatusCode {
    /**
     * 成功请求
     */
    SUCCESS(200, null),
    /**
     * 服务器错误
     */
    SERVER_ERROR(500, "Server Error."),
    /**
     * 资源未找到
     */
    NOT_FOUND(404, "Not Found."),
    /**
     * 禁止访问，没有权限
     */
    FORBIDDEN(403, "Forbidden."),
    /**
     * 用户未登录
     */
    UNAUTHORIZED(401, "Unauthorized.");


    /**
     * 响应状态码
     */
    private Integer code;
    /**
     * 响应信息
     */
    private String message;

    StatusCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
}
