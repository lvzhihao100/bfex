package com.sskj.bfex.p.fragment;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.sskj.bfex.m.HttpConfig;
import com.sskj.bfex.m.bean.HttpData;
import com.sskj.bfex.m.bean.TradeDeputeListBean;
import com.sskj.bfex.m.http.JsonCallBack;
import com.sskj.bfex.m.http.JsonParseException;
import com.sskj.bfex.p.base.BasePresenter;
import com.sskj.bfex.utils.ToastUtil;
import com.sskj.bfex.utils.UserUtil;
import com.sskj.bfex.v.fragment.TradeDeputeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee on 2018/2/28 0028.
 */
public class TradeDeputeFragmentPst extends BasePresenter<TradeDeputeFragment> {
    public void loadData() {
        OkGo.<HttpData<List<TradeDeputeListBean>>>post(HttpConfig.BASE_URL + HttpConfig.TRADE_DEPUTE_LIST)
                .params("mobile", UserUtil.getMobile())
                .execute(new JsonCallBack<HttpData<List<TradeDeputeListBean>>>() {
                    @Override
                    public void onSuccess(Response<HttpData<List<TradeDeputeListBean>>> response) {
                        HttpData<List<TradeDeputeListBean>> httpListData = response.body();
                        if (httpListData.getStatus() == 200) {
                            mView.updateData(httpListData.getData());
                        } else {
                            mView.updateData(new ArrayList<>());
                        }
                    }

                    @Override
                    public void onError(Response<HttpData<List<TradeDeputeListBean>>> response) {
                        if (response.getException() instanceof JsonParseException) {
                            mView.updateData(new ArrayList<>());
                        } else {
                            mView.updateData(null);
                            super.onError(response);
                        }
                    }
                });
    }

    public void cancelTrade(String id) {
        OkGo.<HttpData>post(HttpConfig.BASE_URL + HttpConfig.TRADE_CANCEL)
                .params("mobile", UserUtil.getMobile())
                .params("order_id", id)
                .execute(new JsonCallBack<HttpData>() {
                    @Override
                    public void onSuccess(Response<HttpData> response) {
                        HttpData httpListData = response.body();
                        ToastUtil.showShort(httpListData.getMsg());
                        if (httpListData.getStatus() == 200) {
                            loadData();
                        }
                    }
                });
    }

}
