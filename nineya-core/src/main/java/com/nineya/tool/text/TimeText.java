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

    /**
     * 以long的格式取得当前系统时间
     *
     * @return 时间戳
     */
    public static long getTimeStamp() {
        Date today = new Date();
        return today.getTime();
    }

    /**
     * 使用当前时间，加随机数生成long格式的id,前面为时间，后三位为随机数
     * 添加线程锁防止多线程访问
     *
     * @return id
     */
    public static synchronized long buildId() {
        return (long) ((getTimeStamp() + Math.random()) * 1000);
    }
}
