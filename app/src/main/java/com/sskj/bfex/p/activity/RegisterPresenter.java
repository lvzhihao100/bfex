package com.sskj.bfex.p.activity;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.sskj.bfex.m.HttpConfig;
import com.sskj.bfex.m.bean.HttpData;
import com.sskj.bfex.m.http.JsonCallBack;
import com.sskj.bfex.p.base.BasePresenter;
import com.sskj.bfex.utils.ToastUtil;
import com.sskj.bfex.v.activity.RegisterActivity;

/**
 * <pre>
 *     author : wk_math
 *     e-mail : wk_math@163.com
 *     time   : 2018/03/30
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class RegisterPresenter extends BasePresenter<RegisterActivity> {
    public void sendCode(String account) {
        OkGo.<HttpData>post(HttpConfig.BASE_URL + HttpConfig.CHECK_PHONE_EXIST)
                .params("mobile", account)
                .execute(new JsonCallBack<HttpData>(this) {
                    @Override
                    public void onSuccess(Response<HttpData> response) {
                        HttpData httpListData = response.body();
                        if (httpListData.getStatus() == 200) {
                            OkGo.<HttpData>post(HttpConfig.BASE_URL + HttpConfig.SMS_SEND)
                                    .params("mobile", account)
                                    .params("type", 1)
                                    .execute(new JsonCallBack<HttpData>(RegisterPresenter.this) {
                                        @Override
                                        public void onSuccess(Response<HttpData> response) {
                                            HttpData httpListData = response.body();
                                            if (httpListData.getStatus() == 200) {
                                                mView.sendCodeOk(account);
                                            } else {
                                                ToastUtil.showShort(httpListData.getMsg());
                                            }
                                        }

                                        @Override
                                        public void onError(Response<HttpData> response) {
                                            super.onError(response);
                                        }
                                    });
                        } else {
                            ToastUtil.showShort(httpListData.getMsg());
                        }
                    }
                });
    }
}
