package com.sskj.bfex.p.activity;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.sskj.bfex.MyAppLication;
import com.sskj.bfex.common.Constants;
import com.sskj.bfex.m.HttpConfig;
import com.sskj.bfex.m.bean.HttpData;
import com.sskj.bfex.m.http.JsonCallBack;
import com.sskj.bfex.p.base.BasePresenter;
import com.sskj.bfex.utils.SPUtils;
import com.sskj.bfex.utils.ToastUtil;
import com.sskj.bfex.v.activity.LoginActivity;

/**
 * <pre>
 *     author : wk_math
 *     e-mail : wk_math@163.com
 *     time   : 2018/03/30
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class LoginPresenter extends BasePresenter<LoginActivity> {
    /**
     * 登录
     *
     * @param account 账号
     * @param pws     密码
     */
    public void login(String account, String pws) {
        OkGo.<HttpData>post(HttpConfig.BASE_URL + HttpConfig.LOGIN)
                .params("mobile", account)
                .params("pwd", pws)
                .execute(new JsonCallBack<HttpData>(this) {
                    @Override
                    public void onSuccess(Response<HttpData> response) {
                        HttpData httpListData = response.body();
                        if (httpListData.getStatus() == 200) {
                            SPUtils.put(MyAppLication.getApplication(), Constants.SP_Mobile, account);
                            SPUtils.put(MyAppLication.getApplication(), Constants.SP_LOGIN_STATUS, true);
                            Constants.mIsLogin = true;
                            Constants.mobile = account;
                            mView.loginSuccess();
                        } else {
                            ToastUtil.showShort(httpListData.getMsg());
                        }
                    }
                });
    }
}
