package com.nineya.tool.spi;

/**
 * 建造者模式，构造器接口
 * @author 殇雪话诀别
 */
@FunctionalInterface
public interface Builder<T> {
    /**
     * 构造实体
     * @return 实体
     */
    T build();
}
