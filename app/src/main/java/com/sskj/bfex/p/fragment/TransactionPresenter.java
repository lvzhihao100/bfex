package com.sskj.bfex.p.fragment;


import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.sskj.bfex.common.MarketWebSocket;
import com.sskj.bfex.m.HttpConfig;
import com.sskj.bfex.m.bean.CurrencyInfo;
import com.sskj.bfex.m.bean.Entrust;
import com.sskj.bfex.m.bean.HttpData;
import com.sskj.bfex.m.bean.TransactionModel;
import com.sskj.bfex.m.http.JsonCallBack;
import com.sskj.bfex.p.base.BasePresenter;
import com.sskj.bfex.utils.GsonUtil;
import com.sskj.bfex.v.fragment.TransactionFragment;

import java.util.List;

/**
 * Created by Administrator on 2018/4/26.
 */

public class TransactionPresenter extends BasePresenter<TransactionFragment> {
    MarketWebSocket webSocket;

    /**
     * 获取最新交易列表
     *
     * @param mobile 手机号
     * @param code   编号
     */
    public void getEntrustList(String mobile, String code) {
        OkGo.<HttpData<List<Entrust>>>post(HttpConfig.BASE_URL + HttpConfig.ENTRUSTLIST)
                .params("mobile", mobile)
                .params("code", code)
                .params("status", 3)
                .execute(new JsonCallBack<HttpData<List<Entrust>>>() {
                    @Override
                    public void onSuccess(Response<HttpData<List<Entrust>>> response) {
                        mView.stopRefresh();
                        if (response.body() != null) {
                            if (response.body().getStatus() == 200) {
                                mView.setHistory(response.body().getData());
                            }
                        }
                    }

                    @Override
                    public void onError(Response<HttpData<List<Entrust>>> response) {
                        mView.stopRefresh();
                    }
                });

    }


    /**
     * 提交订单
     * @param mobile    手机号
     * @param code      币种编号
     * @param price     委托价格 （限价必填）
     * @param num       委托数量 （限价必填）
     * @param toalprice 委托总金额 （市价买入必填）
     * @param toalnum   委托总数量 （市价卖出必填）
     * @param type      1买入 2卖出 （必填）
     * @param otype     1限价 2市价 （必填）
     */
    public void submit(String mobile, String code, String price, String num, String toalprice, String toalnum, int type, int otype) {
        OkGo.<HttpData<String>>post(HttpConfig.BASE_URL + HttpConfig.ORDERSUBMIT)
                .params("mobile", mobile)
                .params("code", code)
                .params("buyprice", price)
                .params("buynum", num)
                .params("toalprice", toalprice)
                .params("toalnum", toalnum)
                .params("type", type)
                .params("otype", otype)
                .execute(new JsonCallBack<HttpData<String>>(this) {
                    @Override
                    public void onSuccess(Response<HttpData<String>> response) {
                        if (response.body() != null) {
                            if (response.body().getStatus() == 200) {
                                mView.submitOrderSuccess(response.body().getMsg());
                            } else {
                                mView.submitOrderFail(response.body().getMsg());
                            }
                        }

                    }
                });
    }



    /**
     * 获取币种信息
     *
     * @param mobile 手机号
     * @param code   编号
     */
    public void getInfo(String mobile, String code) {
        OkGo.<HttpData<CurrencyInfo>>post(HttpConfig.BASE_URL + HttpConfig.INFO)
                .params("mobile", mobile)
                .params("code", code)
                .execute(new JsonCallBack<HttpData<CurrencyInfo>>() {
                    @Override
                    public void onSuccess(Response<HttpData<CurrencyInfo>> response) {
                        if (response.body() != null) {
                            if (response.body().getStatus() == 200) {
                                mView.serCurrencyInfo(response.body().getData());
                            }
                        }
                    }

                    @Override
                    public void onError(Response<HttpData<CurrencyInfo>> response) {
                        super.onError(response);
                    }
                });

    }


    /**
     * 关闭webSocket
     */
    public void closeWebSocket() {
        if (webSocket != null) {
            webSocket.closeWebSocket();
            webSocket = null;
        }
    }

    /**
     * 打开webSocket
     *
     * @param code 币种编码
     */
    public void openWebSocket(String code) {
        if (webSocket != null) {
            webSocket.closeWebSocket();
            webSocket = null;
        }
        webSocket = new MarketWebSocket("ws://47.92.0.187:7273", "币种：" + code, code);
        webSocket.setListener(message -> {
            TransactionModel model = GsonUtil.GsonToBean(message, TransactionModel.class);
            mView.setBuySell(model);
        });
    }

    @Override
    public void detachView() {
        super.detachView();
        closeWebSocket();
    }


    /**
     * 撤销订单
     *
     * @param mobile   手机号
     * @param order_id 订单号
     */
    public void cancelOrder(String mobile, String order_id) {
        OkGo.<HttpData<String>>post(HttpConfig.BASE_URL + HttpConfig.ENTRUSTCANCEL)
                .params("mobile", mobile)
                .params("order_id", order_id)
                .execute(new JsonCallBack<HttpData<String>>() {
                    @Override
                    public void onSuccess(Response<HttpData<String>> response) {
                        mView.stopRefresh();
                        if (response.body() != null) {
                            if (response.body().getStatus() == 200) {
                                mView.cancelOrder(response.body().getMsg(), true);
                            } else {
                                mView.cancelOrder(response.body().getMsg(), false);
                            }
                        }
                    }

                });

    }
}