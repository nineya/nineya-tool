package com.nineya.tool.text;

import java.util.UUID;

/**
 * id生成器
 */
public class IdGenerator {

    /**
     * 使用当前时间，加随机数生成long格式的id,前面为时间，后三位为随机数
     * 添加线程锁防止多线程访问导致出现的及其细微可能的id重复
     *
     * @return id
     */
    public static synchronized long buildId() {
        return (long) ((System.currentTimeMillis() + Math.random()) * 1000);
    }

    /**
     * 取得 uuid
     * @return
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
