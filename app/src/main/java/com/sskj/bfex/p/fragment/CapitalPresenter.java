package com.sskj.bfex.p.fragment;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.sskj.bfex.m.HttpConfig;
import com.sskj.bfex.m.bean.TradeInfoBean;
import com.sskj.bfex.m.http.JsonCallBack;
import com.sskj.bfex.p.base.BasePresenter;
import com.sskj.bfex.utils.UserUtil;
import com.sskj.bfex.v.fragment.CapitalFragment;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * <pre>
 *     author : 吕志豪
 *     e-mail : 1030753080@qq.com
 *     time   : 2018/04/04
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class CapitalPresenter extends BasePresenter<CapitalFragment> {

    private DisposableSubscriber disposableSubscriber;

    public void cancel(){
        if (disposableSubscriber != null && !disposableSubscriber.isDisposed()) {
            disposableSubscriber.dispose();
            disposableSubscriber = null;
            OkGo.getInstance().cancelTag(CapitalPresenter.this);
        }
    }
    public void getTradeInfo() {
        disposableSubscriber = new DisposableSubscriber() {
            @Override
            public void onNext(Object o) {
                OkGo.<TradeInfoBean>post(HttpConfig.BASE_URL + HttpConfig.TRADE_INFO)
                        .tag(this)
                        .params("mobile", UserUtil.getMobile())
                        .execute(new JsonCallBack<TradeInfoBean>() {
                            @Override
                            public void onSuccess(Response<TradeInfoBean> response) {
                                TradeInfoBean httpListData = response.body();
                                if (httpListData != null) {
                                    if (mView != null) {
                                        mView.updateTrade(httpListData);
                                    } else {
                                        if (disposableSubscriber != null && !disposableSubscriber.isDisposed()) {
                                            disposableSubscriber.dispose();
                                            disposableSubscriber = null;
                                            OkGo.getInstance().cancelTag(CapitalPresenter.this);
                                        }
                                    }
                                }
                            }
                        });
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        };
        Flowable.interval(0, 1, TimeUnit.SECONDS)
//        Flowable.timer(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .subscribe(disposableSubscriber);

    }
}
