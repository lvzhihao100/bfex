package com.sskj.bfex.utils;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.sskj.bfex.R;

/**
 * <pre>
 *     author : 吕志豪
 *     e-mail : 1030753080@qq.com
 *     time   : 2018/04/10
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class TipUtil {
    public static MaterialDialog getTip(Context context, String content, OnTipSure onTipSure) {
        return new MaterialDialog.Builder(context)
                .title("提示")
                .content(content)
                .positiveText("确定")
                .negativeText("取消")
                .onAny((dialog, which) -> {
                    if (which == DialogAction.POSITIVE) {
                        dialog.dismiss();
                        onTipSure.onSure();
                    } else if (which == DialogAction.NEGATIVE) {
                        dialog.dismiss();
                    }
                }).build();
    }

    public static MaterialDialog customTip(Context context, String title, String content, String deal) {
        MaterialDialog dialog = new MaterialDialog.Builder(context)
                .customView(R.layout.custom_tip_view, false)
                .autoDismiss(true)
                .show();
        View customeView = dialog.getCustomView();
        ((TextView) customeView.findViewById(R.id.tv_title)).setText(title);
        ((TextView) customeView.findViewById(R.id.tv_content)).setText(content);
        ((TextView) customeView.findViewById(R.id.tv_deal)).setText(deal);
        ClickUtil.click((TextView) customeView.findViewById(R.id.tv_deal), () -> {
            dialog.dismiss();
        });
        return dialog;
    }

    public interface OnTipSure {
        void onSure();
    }
}
