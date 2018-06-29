package com.sskj.bfex.utils;

/**
 * <pre>
 *     author : 吕志豪
 *     e-mail : 1030753080@qq.com
 *     time   : 2018/04/04
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class PhoneHideUtil {
    public static String getHide(String mobile){
        return mobile.substring(0,3)+"****"+mobile.substring(7,11);
    }
}
