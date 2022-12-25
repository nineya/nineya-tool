package com.nineya.tool.util;

import com.nineya.tool.text.CheckText;

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

    private static final String TIME_PATH_FORMAT = "yyyy/MM/";
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
        return convertTime(new Date(), format);
    }

    /**
     * 取得当天0点的long时间
     *
     * @return
     */
    public static long zeroTime() {
        return zeroTime(System.currentTimeMillis());
    }

    /**
     * 取得指定时间的0点时间
     *
     * @param time
     * @return
     */
    public static long zeroTime(long time) {
        return time - (time + TimeZone.getDefault().getRawOffset()) % TIME_DAY;
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
     * 将指定时间转换为 yyyy/MM/ 年月分隔的路径
     * @param time
     * @return
     */
    public static String convertTimePath(long time) {
        return convertTime(new Date(time), TIME_PATH_FORMAT);
    }

    /**
     * 将long时间戳转换为String
     *
     * @param time
     * @param format
     * @returnaoshi
     */
    public static String convertTime(long time, String format) {
        return convertTime(new Date(time), format);
    }

    /**
     * 将date时间转换为String
     *
     * @param time
     * @param format
     * @return
     */
    public static String convertTime(Date time, String format) {
        return new SimpleDateFormat(format).format(time);
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
