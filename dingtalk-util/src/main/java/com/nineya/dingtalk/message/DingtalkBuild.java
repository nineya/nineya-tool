package com.nineya.dingtalk.message;

/**
 * 构造器
 * @param <T>
 */
public interface DingtalkBuild<T> {
    /**
     * 构造实体
     * @return 实体
     */
    T build();
}
