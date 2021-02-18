package com.nineya.tool.security;

import java.security.SecureRandom;
import java.util.Random;

/**生成一段指定参数的随机字符串
 * @author 殇雪话诀别
 * 2021/2/19
 */
public class RandomStringGenerator {
    private static final char[] DEFAULT_CODEC = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
            .toCharArray();

    private Random random = new SecureRandom();
    /**
     * 字符串长度
     */
    private int length;

    /**
     * 创建一个能够生成长度为6的随机字符串实例
     */
    public RandomStringGenerator() {
        this(6);
    }

    /**
     * 创建一个能够指定生成长度的随机字符串实例
     * @param length 字符串长度
     */
    public RandomStringGenerator(int length) {
        this.length = length;
    }

    /**
     * 通过随机数生成器生成随机byte数组，再转换为String
     * @return 随机字符串
     */
    public String generate() {
        byte[] verifierBytes = new byte[length];
        random.nextBytes(verifierBytes);
        return getAuthorizationCodeString(verifierBytes);
    }

    /**
     * 将每个byte转换为一个字符，拼接成一个字符串
     *
     * @param verifierBytes byte数组
     * @return 随机字符串
     */
    protected String getAuthorizationCodeString(byte[] verifierBytes) {
        char[] chars = new char[verifierBytes.length];
        for (int i = 0; i < verifierBytes.length; i++) {
            chars[i] = DEFAULT_CODEC[((verifierBytes[i] & 0xFF) % DEFAULT_CODEC.length)];
        }
        return new String(chars);
    }

    /**
     * 随机参数生成器
     *
     * @param random 设置随机参数生成器
     */
    public void setRandom(Random random) {
        this.random = random;
    }

    /**
     * 设置生成的字符串长度
     *
     * @param length 要设置的长度
     */
    public void setLength(int length) {
        this.length = length;
    }
}
