package com.sskj.bfex.p.activity;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.sskj.bfex.m.HttpConfig;
import com.sskj.bfex.m.bean.HttpData;
import com.sskj.bfex.m.bean.LoginBean;
import com.sskj.bfex.m.http.JsonCallBack;
import com.sskj.bfex.p.base.BasePresenter;
import com.sskj.bfex.utils.ToastUtil;
import com.sskj.bfex.utils.UserUtil;
import com.sskj.bfex.v.activity.SpreadActivity;

public class SpreadPresenter extends BasePresenter<SpreadActivity> {

    public void loadData() {
        OkGo.<HttpData<String>>post(HttpConfig.APP)
                .params("app", "app")
                .params("action", "brokerInfo")
                .params("type", 3)
                .params("account", UserUtil.getMobile())
                .execute(new JsonCallBack<HttpData<String>>(this) {
                    @Override
                    public void onSuccess(Response<HttpData<String>> response) {
                        HttpData<String> httpData = response.body();
                        if (httpData.getStatus() == 200) {
                            mView.updateQR(httpData.getData());
                        } else {
                            ToastUtil.showShort(httpData.getMsg());
                        }
                    }
                });
    }

    public void getAccount() {
        OkGo.<HttpData<LoginBean>>post(HttpConfig.APP)
                .params("app", "app")
                .params("action", "accountInfo")
                .params("account", UserUtil.getMobile())
                .execute(new JsonCallBack<HttpData<LoginBean>>(this) {
                    @Override
                    public void onSuccess(Response<HttpData<LoginBean>> response) {
                        HttpData<LoginBean> httpData = response.body();
                        if (httpData.getStatus() == 200) {
                            mView.updateUI(httpData.getData());
                        } else {
                            ToastUtil.showShort(httpData.getMsg());
                        }
                    }
                });
    }
}
