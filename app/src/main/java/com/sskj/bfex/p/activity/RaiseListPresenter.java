package com.sskj.bfex.p.activity;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.sskj.bfex.m.HttpConfig;
import com.sskj.bfex.m.bean.HttpData;
import com.sskj.bfex.m.bean.RaiseListBean;
import com.sskj.bfex.m.http.JsonCallBack;
import com.sskj.bfex.p.base.BasePresenter;
import com.sskj.bfex.utils.ToastUtil;
import com.sskj.bfex.utils.UserUtil;
import com.sskj.bfex.v.activity.RaiseListActivity;

import java.util.List;


public class RaiseListPresenter extends BasePresenter<RaiseListActivity> {

    public void loadData() {
        OkGo.<HttpData<List<RaiseListBean>>>post(HttpConfig.BASE_URL + HttpConfig.RAISE_LIST)
                .params("mobile", UserUtil.getMobile())
                .execute(new JsonCallBack<HttpData<List<RaiseListBean>>>() {
                    @Override
                    public void onSuccess(Response<HttpData<List<RaiseListBean>>> response) {
                        HttpData<List<RaiseListBean>> httpListData = response.body();
                        if (httpListData.getStatus() == 200) {
                            mView.refresh(httpListData.getData());
                        } else {
                            ToastUtil.showShort(httpListData.getMsg());
                        }
                    }
                });
    }
}
