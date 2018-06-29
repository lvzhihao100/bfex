package com.sskj.bfex.p.activity;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okrx2.adapter.FlowableBody;
import com.sskj.bfex.m.HttpConfig;
import com.sskj.bfex.m.bean.HttpData;
import com.sskj.bfex.m.bean.LoginBean;
import com.sskj.bfex.m.http.JsonCallBack;
import com.sskj.bfex.m.http.JsonConverter;
import com.sskj.bfex.p.base.BasePresenter;
import com.sskj.bfex.utils.UserUtil;
import com.sskj.bfex.v.activity.CustomActivity;

import java.util.List;

import io.reactivex.Flowable;

public class CustomPresenter extends BasePresenter<CustomActivity> {
    public Flowable<List<LoginBean>> loadData(int page) {
        return OkGo.<HttpData<List<LoginBean>>>post(HttpConfig.APP)
                .params("app", "app")
                .params("action", "brokerInfo")
                .params("type", 1)
                .params("page", page)
                .params("pageSize", 15)
                .params("account", UserUtil.getMobile())
                .converter(new JsonConverter<HttpData<List<LoginBean>>>() {})
                .adapt(new FlowableBody<>())
                .map(HttpData::getData);

    }
}
