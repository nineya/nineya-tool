package com.nineya.tool.text;

/**
 * @author 殇雪话诀别
 * 2020/12/13
 */
public class TextHander {

    /**
     * 当字符串长度大于要求长度时删除超出长度的字符串，补加三个点
     *
     * @param str  字符串内容
     * @param size 最大长度
     * @return 截取前面部分字符
     */
    public static String limitSize(String str, int size) {
        if (str.length() < size) {
            return str;
        }
        return str.substring(0, size) + "...";
    }

    /**
     * 将字符串内容改为小写
     *
     * @param text 字符串
     * @return 字符串转小写
     */
    public static String toLowerCase(String text) {
        if (text == null) {
            return null;
        }
        return text.toLowerCase();
    }

    /**
     * 手机号脱敏
     * @param phone 手机号
     * @return 脱敏后的数据
     */
    public static String desensitizedPhone(String phone) {
        if (!CheckText.isEmpty(phone)) {
            return phone.replaceAll("(\\w{3})\\w*(\\w{4})", "$1****$2");
        }
        return null;
    }
}
