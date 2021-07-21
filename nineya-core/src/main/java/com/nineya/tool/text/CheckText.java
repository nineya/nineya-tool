package com.nineya.tool.text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 有关于文本信息的验证
 */
public class CheckText {
    public static final String PHONE_PATTERN = "^[1]\\d{10}$";
    public static final String NAME_PATTERN = "^([\\u4e00-\\u9fa5]{2,10}|[A-Za-z]*(\\s[A-Za-z]*)*)$";
    public static final String MAIL_PATTERN = "^(\\w-*\\.*)+@(\\w-?)+(\\.\\w{2,})+$";
    public static final String NINEYA_ID_PATTERN = "^[a-zA-Z]\\w{4,15}+$";
    public static final String MD5_PATTERN = "^([a-fA-F0-9]{32})$";
    public static final String NICK_NAME_PATTERN = "([a-zA-Z\\u4e00-\\u9fa5]|[0-9]|"
            + "\\*|[\\u3002\\uff1b\\uff0c\\uff1a\\u201c\\u201d\\uff08\\uff09\\u3001\\uff1f\\u300a\\u300b]){1,12}";
    public static final String CLIENT_ID_PATTERN = "^[a-zA-Z]\\w{1,8}+$";

    /**
     * 判断字符串是否为空
     *
     * @param str 字符串内容
     * @return 是否为空
     */
    public static boolean isEmpty(Object str) {
        return str == null || "".equals(str);
    }

    /**
     * 验证是不是手机号
     *
     * @param phone 手机号
     * @return true：是，false不是
     */
    public static boolean checkPhone(String phone) {
        if (isEmpty(phone)) {
            return false;
        }
        Pattern p = Pattern.compile(PHONE_PATTERN);
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    /**
     * 判断是不是html的时间格式
     *
     * @param time 时间字符串
     * @return true：是，false不是
     */
    public static boolean checkHtmlTime(String time) {
        if (isEmpty(time)) {
            return false;
        }
        String re = "^\\d{4}-\\d{2}-\\d{2}[T]\\d{2}[:]\\d{2}$";
        Pattern p = Pattern.compile(re);
        Matcher m = p.matcher(time);
        return m.matches();

    }

    /**
     * 判断密码是否符合要求
     *
     * @param password 密码
     * @return true：是，false不是
     */
    public static boolean checkPassword(String password) {
        if (isEmpty(password)) {
            return false;
        }
        return password.length() >= 6 && password.length() <= 20;
//        String re = "^([A-Z]|[a-z]|[0-9]|[`\\-=\\[\\];,./~!@#$&%^*()_+}{:?]){6,20}$";
//        Pattern p = Pattern.compile(re);
//        Matcher m = p.matcher(password);
//        return m.matches();
    }

    /**
     * 校验用户真实姓名是否正确
     *
     * @param name 用户真实姓名
     * @return true：是，false：不是
     */
    public static boolean checkName(String name) {
        final int nameMaxLength = 20;
        if (isEmpty(name) && name.length() > nameMaxLength) {
            return false;
        }
        Pattern p = Pattern.compile(NAME_PATTERN);
        Matcher m = p.matcher(name);
        return m.matches();
    }

    /**
     * 判断是不是url
     *
     * @param url 字符串内容
     * @return true：是url，false：不是
     */
    public static boolean checkUrl(String url) {
        if (isEmpty(url)) {
            return false;
        }
        String re = "^(http|ftp|https)://[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-.,@?^=%&:/~+#]*[\\w\\-@?^=%&/~+#])?";
        Pattern p = Pattern.compile(re);
        Matcher m = p.matcher(url);
        return m.matches();

    }

    /**
     * 判断是不是邮箱
     *
     * @param mail 字符串内容
     * @return true：是，false：不是
     */
    public static boolean checkMail(String mail) {
        if (isEmpty(mail)) {
            return false;
        }
        Pattern p = Pattern.compile(MAIL_PATTERN);
        Matcher m = p.matcher(mail);
        return m.matches();
    }

    /**
     * 判断是不是玖涯号，5-16，可包含下划线
     *
     * @param nineyaId 校验的字符串内容
     * @return true：是，false否
     */
    public static boolean checkNineyaId(String nineyaId) {
        if (isEmpty(nineyaId)) {
            return false;
        }
        Pattern p = Pattern.compile(NINEYA_ID_PATTERN);
        Matcher m = p.matcher(nineyaId);
        return m.matches();
    }

    /**
     * 判断客户端id符不符合要求，5-16，可包含下划线
     *
     * @param clientId
     * @return
     */
    public static boolean checkClientId(String clientId) {
        if (isEmpty(clientId)) {
            return false;
        }
        Pattern p = Pattern.compile(CLIENT_ID_PATTERN);
        Matcher m = p.matcher(clientId);
        return m.matches();
    }

    /**
     * 判断是不是日期格式yyyy-MM-dd
     *
     * @param date 日期字符串
     * @return true：是，false：不是
     */
    public static boolean checkDate(String date) {
        if (isEmpty(date)) {
            return false;
        }
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            formatter.setLenient(false);
            formatter.parse(date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断是不是md5字符串
     *
     * @param str 字符串内容
     * @return true：是，false：不是
     */
    public static boolean checkMd5(String str) {
        if (isEmpty(str)) {
            return false;
        }
        Pattern p = Pattern.compile(MD5_PATTERN);
        Matcher m = p.matcher(str);
        return m.matches();

    }

    /**
     * 判断昵称是否符合要求，可包含“ 。 ；  ， ： “ ”（ ） 、 ？ 《 》”
     *
     * @param nickName 昵称校验规则
     * @return 昵称是否通过
     */
    public static boolean checkNickName(String nickName) {
        if (isEmpty(nickName)) {
            return false;
        }
        Pattern p = Pattern.compile(NICK_NAME_PATTERN);
        Matcher m = p.matcher(nickName);
        return m.matches();
    }

    /**
     * 判断字符是否是正整数
     *
     * @param num
     * @return
     */
    public static boolean checkPositiveInteger(String num) {
        if (isEmpty(num)) {
            return false;
        }
        Pattern p = Pattern.compile("^[\\d]*$");
        Matcher m = p.matcher(num);
        return m.matches();
    }
}