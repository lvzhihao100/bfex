package com.sskj.bfex.utils;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by 吕志豪 on 17-10-10  下午3:39.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */

public class PermissionTipUtil {
    public static void tip(Context context, String content) {
        new MaterialDialog.Builder(context)
                .content("您需要在设置中打开权限(" + content + ")")
                .positiveText("确认")
                .negativeText("取消")
                .onPositive((dialog, which) -> {
                    IntentUtil.gotoMiuiPermission(context);
                }).show();
    }
}
