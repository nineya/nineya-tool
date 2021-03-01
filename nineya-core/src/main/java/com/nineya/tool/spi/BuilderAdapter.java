package com.nineya.tool.spi;

/**
 * 处理创建适配配置信息的接口
 * @param <T> 需要适配的类的泛型
 */
@FunctionalInterface
public interface BuilderAdapter<T> {

    /**
     * 适配
     * @param builder 等待被适配的对象
     */
    void apply(T builder);
}
