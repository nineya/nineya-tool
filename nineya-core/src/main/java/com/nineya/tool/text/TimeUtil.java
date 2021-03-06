package com.nineya.tool.text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author 殇雪话诀别
 * 2020/12/11
 */
public class TimeUtil {
    private static final String DEFAULT_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String MINIMAL_TIME_FORMAT = "yyyyMMddHHmmss";
    /**
     * 一天的时长
     */
    public static final long TIME_DAY = 86400000;

    /**
     * 取得默认样式的当前时间
     *
     * @return
     */
    public static String currentTime() {
        return currentTime(DEFAULT_TIME_FORMAT);
    }

    /**
     * 以极简的风格取得当前时间
     *
     * @return
     */
    public static String currentMinimalTime() {
        return currentTime(MINIMAL_TIME_FORMAT);
    }

    /**
     * 根据指定格式取得当前时间
     *
     * @param format 日期格式
     * @return
     */
    public static String currentTime(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }

    /**
     * 取得当天0点的long时间
     *
     * @return
     */
    public static long zeroTime() {
        long current = System.currentTimeMillis();
        return current - (current + TimeZone.getDefault().getRawOffset()) % TIME_DAY;
    }

    /**
     * 将指定格式的string 时间转换为 long 时间
     *
     * @param time   时间strig
     * @param format 时间格式模板
     * @return
     */
    public static long convertTime(String time, String format) {
        if (CheckText.isEmpty(time)) {
            return 0;
        }
        SimpleDateFormat f = new SimpleDateFormat(format);
        try {
            return f.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 取得传入时间和当前时间间隔的天数，向上取整
     *
     * @param time
     * @return
     */
    public static int intervalTime(long time) {
        long t = time - System.currentTimeMillis();
        return (int) Math.ceil((double) (t) / TIME_DAY);
    }
}
