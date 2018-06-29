package com.sskj.bfex.utils;

import android.view.View;

import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

/**
 * @author吕志豪 .
 * @date 17-12-12  上午10:10.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */

public class ClickUtil {
    public static void click(View view, Click click) {
        RxView.clicks(view)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(aVoid -> click.click());
    }
    public static void click(long time,View view, Click click) {
        RxView.clicks(view)
                .throttleFirst(time, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> click.click());
    }

    public interface Click {
        void click();
    }
}
