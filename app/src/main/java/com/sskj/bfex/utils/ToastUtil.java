package com.sskj.bfex.utils;

import android.content.Context;
import android.widget.Toast;

import com.sskj.bfex.MyAppLication;

import io.reactivex.android.schedulers.AndroidSchedulers;


/**
 * Created by vzhihao on 2016/8/24.
 */
public class ToastUtil {
    protected static Toast toast = null;
    private static volatile ToastUtil mToastUtils;

    /**
     * 长时间显示Toast
     *
     * @param message
     */
    public static void showLong(CharSequence message) {
        AndroidSchedulers.mainThread().createWorker().schedule(() ->
                Toast.makeText(MyAppLication.getApplication(), message, Toast.LENGTH_LONG).show()
        );
    }

    public static void showShort(CharSequence message) {
        AndroidSchedulers.mainThread().createWorker().schedule(() ->
                Toast.makeText(MyAppLication.getApplication(), message, Toast.LENGTH_SHORT).show()
        );
    }


    public ToastUtil() {
    }


    private ToastUtil(Context context) {
        toast = Toast.makeText(context.getApplicationContext(), null, Toast.LENGTH_SHORT);
    }

    public static ToastUtil getInstance(Context context) {
        if (null == mToastUtils) {
            synchronized (ToastUtil.class) {
                if (null == mToastUtils) {
                    mToastUtils = new ToastUtil(context);
                }
            }
        }
        return mToastUtils;
    }

    public void showMessage(int toastMsg) {
        toast.setText(toastMsg);
        toast.show();
    }

    public void showMessage(String toastMsg) {
        toast.setText(toastMsg);
        toast.show();
    }

    public void toastCancel() {
        if (null != toast) {
            toast.cancel();
            toast = null;
        }
        mToastUtils = null;
    }
}
