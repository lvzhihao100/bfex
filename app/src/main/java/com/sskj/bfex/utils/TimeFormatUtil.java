package com.sskj.bfex.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by lv on 18-6-4.
 */

public class TimeFormatUtil {
    public static String FORMAT_A = "yyyy.MM.dd HH:mm";
    public static String FORMAT_B = "yyyy-MM-dd";
    public static SimpleDateFormat SF_FORMAT_A = new SimpleDateFormat(FORMAT_A);
    public static SimpleDateFormat SF_FORMAT_B = new SimpleDateFormat(FORMAT_B);

    public static String formatA(Long time) {
        return SF_FORMAT_A.format(new Date(time));
    }

    public static String formatSix2Five(String time) {
        return time.substring(0, time.length() - 3);
    }

    public static String limitMaxDate(Date date) {
        int day = (int) (date.getTime() / (1000 * 60 * 60 * 24));
        int nowDay = (int) (System.currentTimeMillis() / (1000 * 60 * 60 * 24));
        if (day >= nowDay) {
            return SF_FORMAT_B.format(Calendar.getInstance().getTime());
        }else {
            return SF_FORMAT_B.format(date);

        }
    }
}
