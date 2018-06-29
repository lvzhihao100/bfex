package com.sskj.bfex.p.activity;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.sskj.bfex.m.HttpConfig;
import com.sskj.bfex.m.bean.AgentNumBean;
import com.sskj.bfex.m.bean.HttpData;
import com.sskj.bfex.m.http.JsonCallBack;
import com.sskj.bfex.p.base.BasePresenter;
import com.sskj.bfex.utils.UserUtil;
import com.sskj.bfex.v.activity.AgentPersonActivity;

public class AgentPersonPresenter extends BasePresenter<AgentPersonActivity> {
    public void loadData() {
        OkGo.<HttpData<AgentNumBean>>post(HttpConfig.APP)
                .params("app", "app")
                .params("action", "brokerInfo")
                .params("type", 0)
                .params("account", UserUtil.getMobile())
                .execute(new JsonCallBack<HttpData<AgentNumBean>>(this) {
                    @Override
                    public void onSuccess(Response<HttpData<AgentNumBean>> response) {
                        if (response.body().getStatus()==200) {
                            mView.updateUI(response.body().getData());
                        }
                    }
                });

    }
}
