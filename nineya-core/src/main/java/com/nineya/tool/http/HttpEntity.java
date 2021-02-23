package com.nineya.tool.http;

public interface HttpEntity<T extends HttpEntity> {

    /**
     * 设置实体类型
     * @param type 实体类型
     */
    T setContentType(String type);

    /**
     * 取得实体类型
     * @return 实体类型
     */
    String getContentType();

    /**
     * 设置实体编码
     * @param encode 实体编码
     * @return
     */
    T setContentEncoding(String encode);

    /**
     * 取得实体编码
     * @return 实体编码
     */
    String getContentEncoding();

    /**
     * 取得实体内容
     * @return 实体内容
     */
    String getContent();
}
