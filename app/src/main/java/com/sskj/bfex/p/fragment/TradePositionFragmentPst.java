package com.sskj.bfex.p.fragment;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.sskj.bfex.m.HttpConfig;
import com.sskj.bfex.m.bean.HttpData;
import com.sskj.bfex.m.bean.TradePositionListBean;
import com.sskj.bfex.m.http.JsonCallBack;
import com.sskj.bfex.m.http.JsonParseException;
import com.sskj.bfex.p.base.BasePresenter;
import com.sskj.bfex.utils.UserUtil;
import com.sskj.bfex.v.fragment.TradePositionFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee on 2018/2/28 0028.
 */
public class TradePositionFragmentPst extends BasePresenter<TradePositionFragment> {
    public void loadData() {
        OkGo.<HttpData<List<TradePositionListBean>>>post(HttpConfig.BASE_URL + HttpConfig.TRADE_POSITION_LIST)
                .params("mobile", UserUtil.getMobile())
                .execute(new JsonCallBack<HttpData<List<TradePositionListBean>>>() {
                    @Override
                    public void onSuccess(Response<HttpData<List<TradePositionListBean>>> response) {
                        HttpData<List<TradePositionListBean>> httpListData = response.body();
                        if (httpListData != null) {
                            mView.updateData(httpListData.getData());
                        } else {
                            mView.updateData(new ArrayList<>());
                        }
                    }

                    @Override
                    public void onError(Response<HttpData<List<TradePositionListBean>>> response) {
//                        super.onError(response);
                        if (response.getException() instanceof JsonParseException) {
                            mView.updateData(new ArrayList<>());
                        } else {
                            mView.updateData(null);
                            super.onError(response);
                        }
                    }
                });
    }

}
