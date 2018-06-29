package com.sskj.bfex.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by lee on 2018/01/26
 */

public class DateUtil {

    private static String PATTERN_A = "yyyy年MM月dd日 HH时mm分";
    private static String PATTERN_B = "yyyy年MM月dd日";
    private static String PATTERN_C = "yyyy年MM月dd日 HH:mm:ss";
    private static String PATTERN_D = "yyyy年MM月dd日 HH:mm";
    private static String PATTERN_E = "yyyy-MM-dd";
    private static String PATTERN_F = "HH:mm";
    private static String PATTERN_G = "HH时mm分";
    public static String PATTERN_H = "HH:mm MM/dd";
    public static String PATTERN_I = "yyyy/MM/dd";
    public static String PATTERN_J = "yyyy-MM-dd HH:mm:ss";
    public static String PATTERN_K = "yyyy/MM/dd HH:mm";

    /**
     * 获取系统当前时间 格式化
     * @param pattern 时间格式模式  [PATTERN_A：yyyy年MM月dd日 HH时mm分][PATTERN_B：yyyy年MM月dd日]
     *                [PATTERN_C：yyyy年MM月dd日 HH:mm:ss][PATTERN_D：yyyy年MM月dd日 HH:mm][PATTERN_E：yyyy年MM月dd日]
     *                [PATTERN_F：HH:mm][PATTERN_G：HH时mm分]
     * @return
     */
    public static String getCurrentDate(String pattern) {
        Calendar now= Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat(pattern);
        String dateStr=sdf.format(now.getTimeInMillis());
        return dateStr;
    }

    /**
     * 获取指定时间 格式化
     * @param date
     * @param pattern 时间格式模式  [PATTERN_A：yyyy年MM月dd日 HH时mm分][PATTERN_B：yyyy年MM月dd日]
     *                [PATTERN_C：yyyy年MM月dd日 HH:mm:ss][PATTERN_D：yyyy年MM月dd日 HH:mm][PATTERN_E：yyyy年MM月dd日]
     *                [PATTERN_F：HH:mm][PATTERN_G：HH时mm分]
     * @return
     */
    public static String getDateFormat(Date date, String pattern) {
        SimpleDateFormat sdf=new SimpleDateFormat(pattern);
        String dateStr=sdf.format(date);
        return dateStr;
    }
    /**
     * 获取指定时间 格式化
     * @param time
     * @param pattern 时间格式模式  [PATTERN_A：yyyy年MM月dd日 HH时mm分][PATTERN_B：yyyy年MM月dd日]
     *                [PATTERN_C：yyyy年MM月dd日 HH:mm:ss][PATTERN_D：yyyy年MM月dd日 HH:mm][PATTERN_E：yyyy年MM月dd日]
     *                [PATTERN_F：HH:mm][PATTERN_G：HH时mm分]
     * @return
     */
    public static String getDateFormat(long time, String pattern) {
        SimpleDateFormat sdf=new SimpleDateFormat(pattern);
        String dateStr=sdf.format(time);
        return dateStr;
    }

    /**
     * 根据时间字符串  得到 时间戳
     * @param time
     * @param pattern 时间格式模式  [PATTERN_A：yyyy年MM月dd日 HH时mm分][PATTERN_B：yyyy年MM月dd日]
     *                [PATTERN_C：yyyy年MM月dd日 HH:mm:ss][PATTERN_D：yyyy年MM月dd日 HH:mm][PATTERN_E：yyyy年MM月dd日]
     *                [PATTERN_F：HH:mm][PATTERN_G：HH时mm分]
     * @return timeInMillis
     */
    public static long parseDate(String time, String pattern) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            if(TextUtils.isEmpty(time)) {
                return System.currentTimeMillis()/1000;
            }

            return simpleDateFormat.parse(time).getTime()/1000;
        } catch (ParseException e) {
            e.printStackTrace();
            return System.currentTimeMillis()/1000;
        }
    }

    /**
     * 以当前时间为基准点  比较时间
     * @param inputDate
     * @return 参数时间在当前时间之后 return false , 否则  return true
     */
    public static boolean isTrueDate(Date inputDate){
        Calendar instance = Calendar.getInstance();
        Date currTime = instance.getTime();
        if(inputDate.getTime() > currTime.getTime()){
            return false;
        }else {
            return true;
        }
    }

}
