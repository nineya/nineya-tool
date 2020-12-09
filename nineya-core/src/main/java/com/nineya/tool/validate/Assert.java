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
}
