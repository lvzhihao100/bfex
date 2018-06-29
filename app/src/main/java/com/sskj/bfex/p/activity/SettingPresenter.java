package com.sskj.bfex.p.activity;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.sskj.bfex.MyAppLication;
import com.sskj.bfex.common.Constants;
import com.sskj.bfex.m.HttpConfig;
import com.sskj.bfex.m.bean.HttpData;
import com.sskj.bfex.m.bean.bean.BaseBean;
import com.sskj.bfex.m.http.JsonCallBack;
import com.sskj.bfex.p.base.BasePresenter;
import com.sskj.bfex.utils.SPUtils;
import com.sskj.bfex.utils.ToastUtil;
import com.sskj.bfex.utils.UserUtil;
import com.sskj.bfex.v.activity.PayPwdSettingActivity;

/**
 * Created by Administrator on 2018/4/3 0003.
 */

public class SettingPresenter extends BasePresenter {

    /**
     * 请求交易密码验证码
     */
    public void requestSms() {
        String mobile = (String) SPUtils.get(MyAppLication.getApplication(), Constants.SP_Mobile, "");
        OkGo.<BaseBean>post(HttpConfig.BASE_URL + HttpConfig.SMS_SEND)
                .params("mobile", mobile)
                .params("type", 4)
                .execute(new JsonCallBack<BaseBean>(this) {
                    @Override
                    public void onSuccess(Response<BaseBean> response) {
                        BaseBean body = response.body();
                        ToastUtil.showShort(body.msg);
                        if (body.status == 200) {
                            ((PayPwdSettingActivity) mView).onSmsResponseSuccess();
                        }
                    }
                });
    }

    /**
     * 设置交易密码
     *
     * @param pwd1
     * @param
     */
    public void setPayPwd(String pwd1, String code) {
        OkGo.<HttpData>post(HttpConfig.BASE_URL + HttpConfig.SET_PAY_PWD)
                .params("mobile", UserUtil.getMobile())
                .params("pw1", pwd1)
                .params("pw2", pwd1)
                .params("code", code)
                .execute(new JsonCallBack<HttpData>(this) {
                    @Override
                    public void onSuccess(Response<HttpData> response) {
                        HttpData httpListData = response.body();
                        if (httpListData.getStatus() == 200) {
                            ((PayPwdSettingActivity) mView).onPayPwdSettingSuccess();
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
                .execute(new JsonCallBack<HttpData>(this) {
                    @Override
                    public void onSuccess(Response<HttpData> response) {
                        HttpData httpListData = response.body();
                        ToastUtil.showShort(httpListData.getMsg());
                        if (httpListData.getStatus() == 200) {
                            ((PayPwdSettingActivity) mView).changeBt(code);
                        }
                    }
                });
    }
}
