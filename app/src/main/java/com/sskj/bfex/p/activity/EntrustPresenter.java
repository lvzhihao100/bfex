package com.sskj.bfex.p.activity;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.sskj.bfex.m.HttpConfig;
import com.sskj.bfex.m.bean.Entrust;
import com.sskj.bfex.m.bean.HttpData;
import com.sskj.bfex.m.http.JsonCallBack;
import com.sskj.bfex.p.base.BasePresenter;
import com.sskj.bfex.v.activity.EntrustActivity;

import java.util.List;

/**
 * Created by Administrator on 2018/5/2.
 */

public class EntrustPresenter extends BasePresenter<EntrustActivity> {


    /**
     * 获取全部交易列表
     *
     * @param mobile 手机号
     * @param status （3 委托记录 4成交记录 ）
     */
    public void getEntrustList(String mobile) {
        OkGo.<HttpData<List<Entrust>>>post(HttpConfig.BASE_URL + HttpConfig.ENTRUSTLIST)
                .params("mobile", mobile)
                .params("status", 3)
                .execute(new JsonCallBack<HttpData<List<Entrust>>>() {
                    @Override
                    public void onSuccess(Response<HttpData<List<Entrust>>> response) {
                        mView.stopRefresh();
                        if (response.body() != null) {
                            if (response.body().getStatus() == 200) {
                                mView.setData(response.body().getData());
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
     * 撤销订单
     *
     * @param mobile   手机号
     * @param order_id 订单号
     */
    public void cancelOrder(String mobile, String order_id) {
        OkGo.<HttpData<String>>post(HttpConfig.BASE_URL + HttpConfig.ENTRUSTCANCEL)
                .params("mobile", mobile)
                .params("order_id", order_id)
                .execute(new JsonCallBack<HttpData<String>>(this) {
                    @Override
                    public void onSuccess(Response<HttpData<String>> response) {
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
