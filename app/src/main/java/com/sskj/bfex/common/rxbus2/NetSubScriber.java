package com.sskj.bfex.common.rxbus2;

import com.sskj.bfex.m.http.JsonParseException;
import com.sskj.bfex.utils.ToastUtil;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Created by Administrator on 2018/4/20.
 */

public abstract class NetSubScriber<T> extends DisposableSubscriber<T> {



    @Override
    public void onNext(T t) {
        onSuccess(t);

    }

    @Override
    public void onError(Throwable t) {
        onFailure(t);
        t.printStackTrace();
        if (t instanceof JsonParseException) {
            ToastUtil.showShort("数据解析异常");
        } else if (t instanceof UnknownHostException) {
            ToastUtil.showShort("网络异常");
        } else if (t instanceof TimeoutException) {
            ToastUtil.showShort("网络连接超时");
        } else if (t instanceof ConnectException) {
            ToastUtil.showShort("网络异常");
        }

    }

    @Override
    public void onComplete() {
    }

    public abstract void onSuccess(T t);

    public  void onFailure(Throwable t){};
}
