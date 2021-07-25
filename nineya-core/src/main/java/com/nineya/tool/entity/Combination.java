package com.nineya.tool.entity;

/**
 * @author 殇雪话诀别
 * 2021/7/22
 * <p>
 * 将两个对象组合成一个对象
 */
public class Combination<T, V> {
    public T left;
    public V right;

    public Combination(T left, V right) {
        this.left = left;
        this.right = right;
    }

    public static <T, V> Combination<T, V> create(T left, V right) {
        return new Combination<>(left, right);
    }

    public T left() {
        return left;
    }

    public V right() {
        return right;
    }
}
