package com.sskj.bfex.utils;

import com.sskj.bfex.MyAppLication;
import com.sskj.bfex.common.Constants;
import com.sskj.bfex.m.bean.UserInfo;

/**
 * <pre>
 *     author : 吕志豪
 *     e-mail : 1030753080@qq.com
 *     time   : 2018/04/04
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class UserUtil {
    public static String getMobile() {
        return (String) SPUtils.get(MyAppLication.getApplication(), Constants.SP_Mobile, "");
    }

    public static String getAccount() {
        return ((UserInfo)SPUtils.getBean(MyAppLication.getApplication(), Constants.SP_USER_INFO)).getAccount();
    }
}
