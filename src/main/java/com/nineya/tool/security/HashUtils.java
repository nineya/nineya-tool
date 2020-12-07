package com.nineya.tool.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * @author 殇雪话诀别
 * 2020/11/21
 */
public class HashUtils {
    public static final String TYPE_MD5 = "MD5";
    public static final String TYPE_SHA = "SHA-1";

    /**
     * 计算hash摘要
     * @param string 为要计算的内容
     * @param sf 为加密算法，可以有“MD5”、“SHA-1”，不区分大小写
     * @return 摘要内容
     */
    public static String Hash(String string, String sf) {
        try {
            MessageDigest md5 = MessageDigest.getInstance(sf);
            byte[] arr = md5.digest(string.getBytes(StandardCharsets.UTF_8));
            StringBuilder result = new StringBuilder();
            for (byte b : arr) {
                result.append(String.format("%02x", b));
            }
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
