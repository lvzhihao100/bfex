package com.sskj.bfex.utils;

import android.graphics.Bitmap;

import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by aaron on 16/7/27.
 * 二维码扫描工具类
 */
public class QRCodeUtil {
    public interface OnAnalyzeCallback {
        void onAnalyzeSuccess(String content);

        void onAnalyzeFailed();
    }

    public interface OnEncodeQRCodeCallback {
        void onAnalyzeSuccess(Bitmap bitmap);

        void onAnalyzeFailed();
    }

    /**
     * 解析二维码图片工具类
     *
     * @param analyzeCallback
     */
    public static void analyzeBitmap(String path, OnAnalyzeCallback analyzeCallback) {

        Flowable.just(path)
                .map(QRCodeDecoder::syncDecodeQRCode
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    if (result == null) {
                        analyzeCallback.onAnalyzeFailed();
                    } else {
                        analyzeCallback.onAnalyzeSuccess(result);
                    }
                });
    }
    /**
     * 解析二维码图片工具类
     *
     * @param analyzeCallback
     */
    public static void analyzeBitmap(Bitmap path, OnAnalyzeCallback analyzeCallback) {

        Observable.just(path)
                .map(QRCodeDecoder::syncDecodeQRCode
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    if (result == null) {
                        analyzeCallback.onAnalyzeFailed();
                    } else {
                        analyzeCallback.onAnalyzeSuccess(result);
                    }
                });
    }

    /**
     * 生成二维码图片
     *
     * @return
     */
    public static void createImage(String text, int size, OnEncodeQRCodeCallback analyzeCallback) {

        Observable.just(text)
                .map(s -> QRCodeEncoder.syncEncodeQRCode(s, size)
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    if (result == null) {
                        analyzeCallback.onAnalyzeFailed();
                    } else {
                        analyzeCallback.onAnalyzeSuccess(result);
                    }
                });
    }
}
