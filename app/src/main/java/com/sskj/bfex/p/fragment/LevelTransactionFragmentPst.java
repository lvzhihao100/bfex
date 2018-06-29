package com.sskj.bfex.p.fragment;

import android.text.TextUtils;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.sskj.bfex.m.HttpConfig;
import com.sskj.bfex.m.bean.HttpData;
import com.sskj.bfex.m.bean.StockBean;
import com.sskj.bfex.m.bean.TimeData;
import com.sskj.bfex.m.bean.TradeDealBean;
import com.sskj.bfex.m.bean.UserInfo;
import com.sskj.bfex.m.bean.bean.BaseBean;
import com.sskj.bfex.m.http.JsonCallBack;
import com.sskj.bfex.m.http.JsonParseException;
import com.sskj.bfex.p.base.BasePresenter;
import com.sskj.bfex.utils.ToastUtil;
import com.sskj.bfex.utils.UserUtil;
import com.sskj.bfex.v.fragment.LevelTransactionFragment;

import java.util.List;

/**
 * Created by Administrator on 2018/4/9 0009.
 */

public class LevelTransactionFragmentPst extends BasePresenter<LevelTransactionFragment> {

    public void requestOrderList(String code) {
        if (!TextUtils.isEmpty(UserUtil.getMobile())) {
            OkGo.<HttpData<List<TradeDealBean>>>post(HttpConfig.BASE_URL + HttpConfig.TRADE_CHENGJIAO_ALL)
                    .params("mobile", UserUtil.getMobile())
                    .params("code", code)
                    .execute(new JsonCallBack<HttpData<List<TradeDealBean>>>() {
                        @Override
                        public void onSuccess(Response<HttpData<List<TradeDealBean>>> response) {
                            HttpData<List<TradeDealBean>> body = response.body();
                            if (body.getStatus() == 200) {
                                mView.onOrderListResponseSuccess(body.getData());
                            } else {
                                mView.onOrderListResponseSuccess(null);
                            }
                        }

                        @Override
                        public void onError(Response<HttpData<List<TradeDealBean>>> response) {
                            if (response.getException() instanceof JsonParseException) {
                                mView.onOrderListResponseSuccess(null);
                            } else {
                                super.onError(response);
                            }

                        }
                    });
        }
    }

    public void getUserInfo() {
        OkGo.<HttpData<UserInfo>>post(HttpConfig.BASE_URL + HttpConfig.GET_USER_INFO)
                .params("mobile", UserUtil.getMobile())
                .execute(new JsonCallBack<HttpData<UserInfo>>() {
                    @Override
                    public void onSuccess(Response<HttpData<UserInfo>> response) {
                        HttpData<UserInfo> httpData = response.body();
                        if (httpData.getStatus() == 200) {
                            mView.updateWallet(httpData.getData().wallone);
                        }
                    }
                });
    }

    public void createOrder(StockBean stockData, boolean isTime) {
        OkGo.<BaseBean>post(HttpConfig.BASE_URL + HttpConfig.TRADE_CREATE)
                .params("mobile", UserUtil.getMobile())
                .params("newprice", stockData.newprice)
                .params("type", stockData.type)
                .params("buynum", Integer.valueOf(stockData.buynum))
                .params("shopname", isTime ? stockData.shopname : stockData.shopname.toLowerCase())
                .params("otype", stockData.otype)
//                .params("pwd", pwd)
                .execute(new JsonCallBack<BaseBean>(this) {
                    @Override
                    public void onSuccess(Response<BaseBean> response) {
                        BaseBean baseBean = response.body();
                        if (baseBean.status == 200) {
                            mView.onCreateOrderSuccess();
                        } else {
                            ToastUtil.showShort(baseBean.msg);
                        }
                    }
                });
    }

    public void getLeveRange() {
        OkGo.<HttpData<TimeData>>post(HttpConfig.BASE_URL + HttpConfig.TIME_DATA)
                .execute(new JsonCallBack<HttpData<TimeData>>() {
                    @Override
                    public void onSuccess(Response<HttpData<TimeData>> response) {
                        if (response.body() != null) {
                            HttpData<TimeData> httpListData = response.body();
                            if (httpListData.getStatus() == 200) {
                                mView.updateLevel(httpListData.getData().getTimeData());
                            }
                        }
                    }
                });
//        OkGo.<HttpData>post(HttpConfig.BASE_URL+HttpConfig.LEVEL)
//                .params("id", id)
//                .execute(new JsonCallBack<HttpData>() {
//                    @Override
//                    public void onSuccess(Response<HttpData> response) {
//                        HttpData httpListData = response.body();
//                        if (httpListData.getStatus() == 1) {
//
//                        }
//                    }
//                });
    }
}
