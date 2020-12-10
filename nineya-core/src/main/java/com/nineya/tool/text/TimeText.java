package com.nineya.tool.text;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 殇雪话诀别
 * 2020/12/11
 */
public class TimeText {
    private static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String getCurrentTime() {
        return getCurrentTime(TIME_FORMAT);
    }

    public static String getCurrentTime(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }
}
