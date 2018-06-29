package com.sskj.bfex.p.activity;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.sskj.bfex.m.HttpConfig;
import com.sskj.bfex.m.bean.HttpData;
import com.sskj.bfex.m.http.JsonCallBack;
import com.sskj.bfex.p.base.BasePresenter;
import com.sskj.bfex.utils.ToastUtil;
import com.sskj.bfex.v.activity.PwdResetActivity;

/**
 * <pre>
 *     author : wk_math
 *     e-mail : wk_math@163.com
 *     time   : 2018/03/30
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class PwdResetPresenter extends BasePresenter<PwdResetActivity> {
    public void reset(String account, String password, String code) {
        OkGo.<HttpData>post(HttpConfig.BASE_URL + HttpConfig.RESET_PWD)
                .params("pw1", password)
                .params("pw2", password)
                .params("code", code)
                .params("mobile", account)
                .execute(new JsonCallBack<HttpData>(this) {
                    @Override
                    public void onSuccess(Response<HttpData> response) {
                        HttpData httpData = response.body();
                        ToastUtil.showShort(httpData.getMsg());
                        if (httpData.getStatus() == 200) {
                            mView.resetSuccess();
                        }
                    }
                });
    }

    public void resetWithOrigin(String account, String passwordOrigin, String password) {
        OkGo.<HttpData>post(HttpConfig.BASE_URL + HttpConfig.CHANGE_PWD)
                .params("opwd", passwordOrigin)
                .params("pw1", password)
                .params("pw2", password)
                .params("mobile", account)
                .execute(new JsonCallBack<HttpData>(this) {
                    @Override
                    public void onSuccess(Response<HttpData> response) {
                        HttpData httpData = response.body();
                        ToastUtil.showShort(httpData.getMsg());
                        if (httpData.getStatus() == 200) {
                            mView.resetSuccess();
                        }
                    }
                });
    }
}
