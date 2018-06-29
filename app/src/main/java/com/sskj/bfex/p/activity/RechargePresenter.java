package com.sskj.bfex.p.activity;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.sskj.bfex.m.HttpConfig;
import com.sskj.bfex.m.bean.HttpData;
import com.sskj.bfex.m.bean.RechargeInfoBean;
import com.sskj.bfex.m.http.JsonCallBack;
import com.sskj.bfex.p.base.BasePresenter;
import com.sskj.bfex.utils.ToastUtil;
import com.sskj.bfex.utils.UserUtil;
import com.sskj.bfex.v.activity.RechargeActivity;

/**
 * <pre>
 *     author : wk_math
 *     e-mail : wk_math@163.com
 *     time   : 2018/03/30
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class RechargePresenter extends BasePresenter<RechargeActivity> {

    public void recharge() {
        OkGo.<HttpData<RechargeInfoBean>>get(HttpConfig.BASE_URL + HttpConfig.RECHARGE_URL)
                .params("mobile", UserUtil.getMobile())
                .execute(new JsonCallBack<HttpData<RechargeInfoBean>>(this) {
                    @Override
                    public void onSuccess(Response<HttpData<RechargeInfoBean>> response) {
                        HttpData<RechargeInfoBean> httpListData = response.body();
                        if (httpListData.getStatus() == 200) {
                            mView.updateView(httpListData.getData());
                        } else {
                            ToastUtil.showShort(httpListData.getMsg());
                        }
                    }
                });
    }
}
