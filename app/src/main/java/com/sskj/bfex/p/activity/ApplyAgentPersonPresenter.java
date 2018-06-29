package com.sskj.bfex.p.activity;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.sskj.bfex.common.rxbus2.RxBus;
import com.sskj.bfex.m.HttpConfig;
import com.sskj.bfex.m.bean.AgentNumBean;
import com.sskj.bfex.m.bean.HttpData;
import com.sskj.bfex.m.http.JsonCallBack;
import com.sskj.bfex.p.base.BasePresenter;
import com.sskj.bfex.utils.ToastUtil;
import com.sskj.bfex.utils.UserUtil;
import com.sskj.bfex.v.activity.ApplyAgentPersonActivity;

import java.util.TreeMap;

public class ApplyAgentPersonPresenter extends BasePresenter<ApplyAgentPersonActivity> {
    public void sendCode() {
        OkGo.<HttpData>post(HttpConfig.APP)
                .params("app", "app")
                .params("action", "getTelCode")
                .params("type", 0)
                .params("account", UserUtil.getMobile())
                .execute(new JsonCallBack<HttpData>() {
                    @Override
                    public void onSuccess(Response<HttpData> response) {
                        if (response.body().getStatus() == 200) {
                            ToastUtil.showShort(response.body().getMsg());
                        }
                    }
                });
    }

    public void applyAgent(String jigouCode, String name, String code) {

        OkGo.<HttpData<AgentNumBean>>post(HttpConfig.APP)
                .params("app", "app")
                .params("action", "brokerApply")
                .params("code", code)
                .params("account", UserUtil.getMobile())
                .execute(new JsonCallBack<HttpData<AgentNumBean>>(this) {
                    @Override
                    public void onSuccess(Response<HttpData<AgentNumBean>> response) {
                        if (response.body().getStatus() == 200) {

                            ToastUtil.showShort(response.body().getMsg());
//                            RxBus.getDefault().send(Constant.USER_REFRESH);
                            mView.finish();
                        }
                    }
                });
    }
}
