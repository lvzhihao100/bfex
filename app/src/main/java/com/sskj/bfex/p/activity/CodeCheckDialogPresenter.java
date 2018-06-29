package com.sskj.bfex.p.activity;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.sskj.bfex.m.HttpConfig;
import com.sskj.bfex.m.bean.HttpData;
import com.sskj.bfex.m.http.JsonCallBack;
import com.sskj.bfex.p.base.BasePresenter;
import com.sskj.bfex.utils.ToastUtil;
import com.sskj.bfex.v.activity.CodeCheckDialogActivity;

/**
 * <pre>
 *     author : wk_math
 *     e-mail : wk_math@163.com
 *     time   : 2018/03/30
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class CodeCheckDialogPresenter extends BasePresenter<CodeCheckDialogActivity> {
    public void checkCode(String account, String code) {
        OkGo.<HttpData>post(HttpConfig.BASE_URL + HttpConfig.CHECK_CODE)
                .params("mobile", account)
                .params("code", code)
                .execute(new JsonCallBack<HttpData>(this) {
                    @Override
                    public void onSuccess(Response<HttpData> response) {
                        HttpData httpListData = response.body();
                        if (httpListData.getStatus() == 200) {
                            mView.setPwd(account);
                        } else {
                            ToastUtil.showShort(httpListData.getMsg());
                        }
                    }
                });
    }
    public void sendCode(String account) {
        OkGo.<HttpData>post(HttpConfig.BASE_URL + HttpConfig.SMS_SEND)
                .params("mobile", account)
                .params("type", 2)
                .execute(new JsonCallBack<HttpData>(this) {
                    @Override
                    public void onSuccess(Response<HttpData> response) {
                        HttpData httpListData = response.body();
                        if (httpListData.getStatus() == 200) {
                            mView.startCount();
                        } else {
                            ToastUtil.showShort(httpListData.getMsg());
                        }
                    }

                    @Override
                    public void onError(Response<HttpData> response) {
                        super.onError(response);
                    }
                });
    }
}
