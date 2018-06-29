package com.sskj.bfex.p.activity;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.sskj.bfex.m.HttpConfig;
import com.sskj.bfex.m.bean.HttpData;
import com.sskj.bfex.m.http.JsonCallBack;
import com.sskj.bfex.p.base.BasePresenter;
import com.sskj.bfex.utils.ToastUtil;
import com.sskj.bfex.v.activity.BindEmailActivity;

/**
 * Created by Administrator on 2018/4/2 0002.
 */

public class BindEmailPresenter extends BasePresenter<BindEmailActivity> {

    public void requestBindEmail(String newEmail, String mobile) {
        OkGo.<HttpData>post(HttpConfig.BASE_URL + HttpConfig.BIND_EMAIL)
                .params("mobile", mobile)
                .params("email", newEmail)
                .execute(new JsonCallBack<HttpData>(this) {
                    @Override
                    public void onSuccess(Response<HttpData> response) {
                        HttpData httpListData = response.body();
                        if (httpListData.getStatus() == 200) {
                            ToastUtil.showShort(httpListData.getMsg());
                            mView.onResponseSuccess();
                        } else {
                            ToastUtil.showShort(httpListData.getMsg());
                        }
                    }
                });
    }
}
