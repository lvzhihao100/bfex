package com.sskj.bfex.mvchelper;

import com.shizhefei.mvc.IAsyncDataSource;
import com.shizhefei.mvc.RequestHandle;
import com.shizhefei.mvc.ResponseSender;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Created by LuckyJayce on 2016/7/21.
 */
public abstract class Rx2DataSource<DATA> implements IAsyncDataSource<DATA> {

    @Override
    public final RequestHandle refresh(final ResponseSender<DATA> sender) throws Exception {
        return load(sender, refreshRX());
    }

    @Override
    public final RequestHandle loadMore(ResponseSender<DATA> sender) throws Exception {

        return load(sender, loadMoreRX());
    }

    protected abstract Flowable<DATA> refreshRX() throws Exception;

    protected abstract Flowable<DATA> loadMoreRX() throws Exception;

    private RequestHandle load(final ResponseSender<DATA> sender, final Flowable<DATA> flowable) {
        DisposableSubscriber<DATA> subscriber = new DisposableSubscriber<DATA>() {
            @Override
            public void onNext(DATA data) {
                sender.sendData(data);
            }

            @Override
            public void onError(Throwable e) {
                sender.sendError(new Exception(e));
            }

            @Override
            public void onComplete() {

            }
        };
        flowable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        return new RequestHandle() {
            @Override
            public void cancle() {
                if (!subscriber.isDisposed()) {
                    subscriber.dispose();
                }
            }

            @Override
            public boolean isRunning() {
                return false;
            }
        };
    }


}
