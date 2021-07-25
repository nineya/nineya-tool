package com.nineya.tool.restful;

/**
 * @author 殇雪话诀别
 * 2021/7/20
 * 网络相关的状态码
 */
public enum NetworkStatus implements StatusCode {

    /**
     * 成功请求
     */
    OK(200, null),
    /**
     * 客户端请求报文内容有误
     */
    BAD_REQUEST(400, "Bad Request."),
    /**
     * 用户未登录（不知道有没有授权）
     */
    UNAUTHORIZED(401, "Unauthorized."),
    /**
     * 禁止访问，没有权限（不在授权范围）
     */
    FORBIDDEN(403, "Forbidden."),
    /**
     * 资源未找到
     */
    NOT_FOUND(404, "Not Found."),
    /**
     * 服务器错误
     */
    SERVER_ERROR(500, "Server Error."),
    /**
     * 服务器过负载或者在维护
     */
    SERVER_UNAVAILABLE(503, "Service Unavailable.");


    /**
     * 响应状态码
     */
    private int code;
    /**
     * 响应信息
     */
    private String message;

    NetworkStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
