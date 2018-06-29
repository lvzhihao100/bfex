package com.sskj.bfex;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.bulong.rudeness.RudenessScreenHelper;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.DBCookieStore;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.sskj.bfex.common.Cockroach;

import java.util.logging.Level;

import okhttp3.OkHttpClient;


/**
 * Created by Lee on 2018/1/25 0025.
 */

public class MyAppLication extends Application {

    public static MyAppLication getApplication() {
        return mApplication;
    }

    private static MyAppLication mApplication;


    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        new RudenessScreenHelper(this, 750).activate(); //初始化百分比布局
        initOkgoHttp(this);

//        Cockroach.install((thread, throwable) -> new Handler(Looper.getMainLooper()).post(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    //建议使用下面方式在控制台打印异常，这样就可以在Error级别看到红色log
//                    Log.e("AndroidRuntime", "--->CockroachException:" + thread + "<---", throwable);
//                } catch (Throwable e) {
//                }
//            }
//        }));

    }

    private void initOkgoHttp(Context context) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        loggingInterceptor.setColorLevel(Level.INFO);
        builder.cookieJar(new CookieJarImpl(new DBCookieStore(context)));
        builder.addInterceptor(loggingInterceptor);

        OkGo.getInstance().init(this)//必须调用初始化
                .setOkHttpClient(builder.build())                   //必须设置OkHttpClient
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(0);
        //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
//                .addCommonHeaders(headers)                      //全局公共头
//                .addCommonParams(params);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Cockroach.uninstall();
    }
}
