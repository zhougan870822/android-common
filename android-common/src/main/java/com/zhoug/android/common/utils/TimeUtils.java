package com.zhoug.android.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 描述：时间工具
 * zhougan
 * 2019/9/6
 **/
public class TimeUtils {
    public static final String FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取当前时间
     *
     * @param format 默认 "yyyy-MM-dd HH:mm:ss"
     * @return
     */
    public static String getCurrentTime(String format) {
        if (format == null) {
            format = FORMAT;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(new Date());
    }

    /**
     * 格式默认 "yyyy-MM-dd HH:mm:ss"
     * @return
     */
    public static String getCurrentTime() {
        return getCurrentTime(FORMAT);
    }


}
