package com.nineya.tool.text;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 殇雪话诀别
 * 2020/12/11
 */
public class TimeText {
    private static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 取得默认样式的当前时间
     * @return
     */
    public static String getCurrentTime() {
        return getCurrentTime(TIME_FORMAT);
    }

    /**
     * 根据指定格式取得当前时间
     * @param format 日期格式
     * @return
     */
    public static String getCurrentTime(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }
}
