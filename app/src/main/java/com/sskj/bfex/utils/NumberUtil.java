package com.sskj.bfex.utils;

import android.text.TextUtils;

import java.math.RoundingMode;
import java.text.NumberFormat;

/**
 * Created by lv on 18-6-5.
 */

public class NumberUtil {
    /**
     * 保留4位小数，如果为0,返回--
     *
     * @param number
     * @return
     */
    public static String keep4(String number) {
        if (Double.valueOf(number) == 0) {
            return "--";
        }
        NumberFormat nf = NumberFormat.getNumberInstance();


        // 保留两位小数
        nf.setMinimumFractionDigits(4);
        nf.setMaximumFractionDigits(4);
        nf.setGroupingUsed(false);


        // 如果不需要四舍五入，可以使用RoundingMode.DOWN
        nf.setRoundingMode(RoundingMode.UP);


        return nf.format(Double.valueOf(number));
    }

    public static String keep0(String number) {
        NumberFormat nf = NumberFormat.getNumberInstance();


        // 保留两位小数
        nf.setMinimumFractionDigits(0);
        nf.setMaximumFractionDigits(0);
        nf.setGroupingUsed(false);


        // 如果不需要四舍五入，可以使用RoundingMode.DOWN
        nf.setRoundingMode(RoundingMode.UP);


        return nf.format(Double.valueOf(number));
    }

    /**
     * 保留最多四位小数，number为空，返回0
     *
     * @param number
     * @return
     */
    public static String keepMax4(String number) {
        if (TextUtils.isEmpty(number)) {
            return 0 + "";
        }
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(4);
        nf.setGroupingUsed(false);
        // 如果不需要四舍五入，可以使用RoundingMode.DOWN
        nf.setRoundingMode(RoundingMode.UP);
        return nf.format(Double.valueOf(number));
    }

    public static String keepMax4(Double number) {
        return keepMax4(number + "");
    }

    /**
     * 去除小数点最后的零
     *
     * @param number
     * @return
     */
    public static String keepNoZore(String number) {
        if (TextUtils.isEmpty(number)) {
            return 0 + "";
        }
        if (number.indexOf(".") > 0) {
            number = number.replaceAll("0+?$", "");//去掉多余的0
            number = number.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return number;
    }

    /**
     * 去除小数点最后的零
     *
     * @param number
     * @return
     */
    public static String keepNoZore(double number) {
        return keepNoZore(number + "");
    }

}
