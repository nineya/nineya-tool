package com.nineya.tool.util;

import com.nineya.tool.text.CheckText;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author 殇雪话诀别
 * 2020/11/21
 */
public class Base64Util {
    /**
     * base64编码
     *
     * @param data 字符串内容
     * @return base64编码
     */
    public static String base64Encoder(String data) {
        if (CheckText.isEmpty(data)) {
            return null;
        }
        byte[] resultBytes = data.getBytes(StandardCharsets.UTF_8);
        return Base64.getEncoder().encodeToString(resultBytes);
    }

    /**
     * base64解码
     * @param data base64编码
     * @return 字符串内容
     */
    public static String base64Decoder(String data) {
        if (CheckText.isEmpty(data)) {
            return null;
        }
        byte[] base = Base64.getDecoder().decode(data);
        return new String(base, StandardCharsets.UTF_8);
    }
}
