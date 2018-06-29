package com.sskj.bfex.p.activity;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.sskj.bfex.m.HttpConfig;
import com.sskj.bfex.m.bean.HttpData;
import com.sskj.bfex.m.http.JsonCallBack;
import com.sskj.bfex.p.base.BasePresenter;
import com.sskj.bfex.utils.ToastUtil;
import com.sskj.bfex.v.activity.PwdSetActivity;

/**
 * <pre>
 *     author : wk_math
 *     e-mail : wk_math@163.com
 *     time   : 2018/03/30
 *     desc   :
 *     version: 1.0
 * </pre>
 */
//13717645021
public class PasswordSetPresenter extends BasePresenter<PwdSetActivity> {
    public void register(String account, String password,String tjuser,String code) {
        OkGo.<HttpData>post(HttpConfig.BASE_URL + HttpConfig.REGISTER)
                .params("mobile", account)
                .params("opwd", password)
                .params("tjuser", tjuser)
                .params("code", code)
                .execute(new JsonCallBack<HttpData>(this) {
                    @Override
                    public void onSuccess(Response<HttpData> response) {
                        HttpData httpListData = response.body();
                        if (httpListData.getStatus() == 200) {
                            mView.registerSuccess();
                        } else {
                            ToastUtil.showShort(httpListData.getMsg());
                        }
                    }
                });
    }
}
