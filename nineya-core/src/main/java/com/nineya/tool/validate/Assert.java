package com.nineya.tool.validate;

import com.nineya.tool.text.CheckText;

/**
 * @author 殇雪话诀别
 * 2020/12/4
 */
public class Assert {
    private static final String NULL_TIPS = " 不允许为空";
    private static final String FORMAT_TIPS = " 格式不正确";

    /**
     * 对参数进行验证，空值则抛出IllegalArgumentException
     * @param value 参数
     * @param tips 提示信息
     */
    public static void notAllowedEmpty(Object value, String tips) {
        if (value == null) {
            throw new IllegalArgumentException(tips + NULL_TIPS);
        }
        if (value instanceof String
                && ((String) value).trim().length() == 0) {
            throw new IllegalArgumentException(tips + NULL_TIPS);
        }
    }

    /**
     * 验证规则是不是密码
     * @param password 密码
     * @param tips 提示信息
     */
    public static void password(String password, String tips) {
        if (!CheckText.checkPassword(password)) {
            throw new IllegalArgumentException(tips + FORMAT_TIPS);
        }
    }

    /**
     * 验证传入的value值是不是true，允许true通过
     * @param value 值
     * @param message 错误提示信息
     */
    public static void trueAllowed(boolean value, String message) {
       if (!value) {
           throw new IllegalArgumentException(message);
       }
    }

    /**
     * 当两个对象不相等时通过
     * @param o1 对象1
     * @param o2 对象2
     * @param message 未通过时的错误信息
     */
    public static void noEqualsAllowed(Object o1, Object o2, String message) {
        if ((o1 != null && o1.equals(o2)) || (o1 == null && o2 == null)) {
            throw new IllegalArgumentException(message);
        }
    }
}
