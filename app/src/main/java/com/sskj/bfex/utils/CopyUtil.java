package com.sskj.bfex.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import com.sskj.bfex.MyAppLication;

/**
 * <pre>
 *     author : wk_math
 *     e-mail : wk_math@163.com
 *     time   : 2018/03/28
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class CopyUtil {
    public static void copy(String content) {
        ClipboardManager cm = (ClipboardManager) MyAppLication.getApplication().getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setPrimaryClip(ClipData.newPlainText("", content));
        ToastUtil.showShort("复制成功");
    }
}
