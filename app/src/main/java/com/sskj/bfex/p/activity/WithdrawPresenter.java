package com.sskj.bfex.p.activity;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.sskj.bfex.MyAppLication;
import com.sskj.bfex.common.Constants;
import com.sskj.bfex.m.HttpConfig;
import com.sskj.bfex.m.bean.HttpData;
import com.sskj.bfex.m.bean.UserInfo;
import com.sskj.bfex.m.http.JsonCallBack;
import com.sskj.bfex.p.base.BasePresenter;
import com.sskj.bfex.utils.SPUtils;
import com.sskj.bfex.utils.ToastUtil;
import com.sskj.bfex.utils.UserUtil;
import com.sskj.bfex.v.activity.WithdrawActivity;

/**
 * <pre>
 *     author : wk_math
 *     e-mail : wk_math@163.com
 *     time   : 2018/03/30
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class WithdrawPresenter extends BasePresenter<WithdrawActivity> {

    public void sendCode() {
        String phone = (String) SPUtils.get(MyAppLication.getApplication(), Constants.SP_Mobile, "");
        OkGo.<HttpData>post(HttpConfig.BASE_URL + HttpConfig.SMS_SEND)
                .params("mobile", phone)
                .params("type", 5)
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
                });
    }

    public void checkCode(String code) {
        OkGo.<HttpData>post(HttpConfig.BASE_URL + HttpConfig.CHECK_CODE)
                .params("mobile", UserUtil.getMobile())
                .params("code", code)
                .execute(new JsonCallBack<HttpData>() {
                    @Override
                    public void onSuccess(Response<HttpData> response) {
                        HttpData httpListData = response.body();
                        if (httpListData.getStatus() == 200) {
                            mView.setCodeOk();
                        } else {
                            ToastUtil.showShort(httpListData.getMsg());
                        }
                    }
                });
    }

    public void withdraw(String num, String tpwd, String code, String qianbao_url) {
        OkGo.<HttpData>post(HttpConfig.BASE_URL + HttpConfig.WITHDRAW)
                .params("mobile", UserUtil.getMobile())
                .params("num", num)
                .params("tpwd", tpwd)
                .params("code", code)
                .params("qiaobao_url", qianbao_url)
                .execute(new JsonCallBack<HttpData>(this) {
                    @Override
                    public void onSuccess(Response<HttpData> response) {
                        HttpData httpListData = response.body();
                        ToastUtil.showShort(httpListData.getMsg());
                        if (httpListData.getStatus() == 200) {
                            mView.finish();
                        }
                    }
                });
    }

    public void getUserInfo() {
        OkGo.<HttpData<UserInfo>>post(HttpConfig.BASE_URL + HttpConfig.GET_USER_INFO)
                .params("mobile", UserUtil.getMobile())
                .execute(new JsonCallBack<HttpData<UserInfo>>(this) {
                    @Override
                    public void onSuccess(Response<HttpData<UserInfo>> response) {
                        HttpData<UserInfo> httpData = response.body();
                        if (httpData.getStatus() == 200) {
                            mView.updateUserInfo(httpData.getData());
                        }
                    }
                });
    }
}
