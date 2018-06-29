package com.sskj.bfex.utils;

import android.text.TextUtils;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数字相关操作
 * create by Hey at 2018/5/11 11:13
 */

public final class NumberUtils {
    private NumberUtils() {
    }

    /**
     * String转换成Float
     *
     * @param s s
     * @return 若为null或空返回0
     */
    public static float pareFloat(String s) {
        if (isRealNumber(s)) {
            return Float.valueOf(s);
        }
        return 0;
    }

    /**
     * Format字符串
     *
     * @param s       s
     * @param pattern 表达式
     * @return 若为数字则格式化若不为数字则返回原字符
     */
    public static String format(String s, String pattern) {
        if (isRealNumber(s)) {
            DecimalFormat format = new DecimalFormat(pattern);
            return format.format(pareFloat(s));
        }
        return s;
    }


    /**
     * Format字符串
     *
     * @param s       s
     * @param pattern 表达式
     * @return 若为数字则格式化若不为数字则返回原字符
     */
    public static String format(Float s, String pattern) {
        DecimalFormat format = new DecimalFormat(pattern);
        return format.format(s);
    }

    private static boolean isMatch(String regex, String orginal) {
        if (orginal == null || orginal.trim().equals("")) {
            return false;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher isNum = pattern.matcher(orginal);
        return isNum.matches();
    }

    public static boolean isPositiveInteger(String orginal) {
        return isMatch("^\\+{0,1}[1-9]\\d*", orginal);
    }

    public static boolean isNegativeInteger(String orginal) {
        return isMatch("^-[1-9]\\d*", orginal);
    }

    public static boolean isWholeNumber(String orginal) {
        return isMatch("[+-]{0,1}0", orginal) || isPositiveInteger(orginal) || isNegativeInteger(orginal);
    }

    public static boolean isPositiveDecimal(String orginal) {
        return isMatch("\\+{0,1}[0]\\.[1-9]*|\\+{0,1}[1-9]\\d*\\.\\d*", orginal);
    }

    public static boolean isNegativeDecimal(String orginal) {
        return isMatch("^-[0]\\.[1-9]*|^-[1-9]\\d*\\.\\d*", orginal);
    }

    public static boolean isDecimal(String orginal) {
        return isMatch("[-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+", orginal);
    }

    /**
     * 判断是否是整数或小数
     *
     * @param s s
     * @return 为空返回false
     */
    public static boolean isRealNumber(String s) {
        if (TextUtils.isEmpty(s)) {
            return false;
        }
        return isWholeNumber(s) || isDecimal(s);
    }
}
