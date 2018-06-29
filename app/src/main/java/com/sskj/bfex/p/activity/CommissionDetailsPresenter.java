package com.sskj.bfex.p.activity;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okrx2.adapter.FlowableBody;
import com.sskj.bfex.m.HttpConfig;
import com.sskj.bfex.m.bean.AgentNumBean;
import com.sskj.bfex.m.bean.CommissionBean;
import com.sskj.bfex.m.bean.HttpData;
import com.sskj.bfex.m.http.JsonCallBack;
import com.sskj.bfex.m.http.JsonConverter;
import com.sskj.bfex.p.base.BasePresenter;
import com.sskj.bfex.utils.UserUtil;
import com.sskj.bfex.v.activity.CommissionDetailActivity;

import java.util.List;
import java.util.TreeMap;

import io.reactivex.Flowable;

public class CommissionDetailsPresenter extends BasePresenter<CommissionDetailActivity> {
    public Flowable<List<CommissionBean>> loadData(int page, String dateFrom, String dateTo) {
        return OkGo.<HttpData<List<CommissionBean>>>post(HttpConfig.APP)
                .params("app", "app")
                .params("action", "brokerInfo")
                .params("type", 2)
                .params("startDate", dateFrom)
                .params("endDate", dateTo)
                .params("page", page)
                .params("pageSize", 15)
                .params("account", UserUtil.getMobile())
                .converter(new JsonConverter<HttpData<List<CommissionBean>>>() {
                    @Override
                    public void test() {
                        super.test();
                    }
                })
                .adapt(new FlowableBody<>())
                .map(listHttpData -> listHttpData.getData());

    }
}
