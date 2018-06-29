package com.sskj.bfex.p.fragment;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.sskj.bfex.m.HttpConfig;
import com.sskj.bfex.m.bean.HttpData;
import com.sskj.bfex.m.bean.TradePositionListBean;
import com.sskj.bfex.m.http.JsonCallBack;
import com.sskj.bfex.p.base.BasePresenter;
import com.sskj.bfex.utils.ToastUtil;
import com.sskj.bfex.utils.UserUtil;
import com.sskj.bfex.v.fragment.NewTradePositionFragment;

import java.util.List;

/**
 * Created by Lee on 2018/2/28 0028.
 */
public class NewTradePositionFragmentPst extends BasePresenter<NewTradePositionFragment> {
    public void loadData() {
        OkGo.<HttpData<List<TradePositionListBean>>>post(HttpConfig.BASE_URL + HttpConfig.TRADE_POSITION_LIST)
                .params("mobile", UserUtil.getMobile())
                .execute(new JsonCallBack<HttpData<List<TradePositionListBean>>>() {
                    @Override
                    public void onSuccess(Response<HttpData<List<TradePositionListBean>>> response) {
                        mView.stopRefresh();
                        HttpData<List<TradePositionListBean>> httpListData = response.body();
                        if (httpListData != null) {
                            mView.updateData(httpListData.getData());
                        }
                    }

                    @Override
                    public void onError(Response<HttpData<List<TradePositionListBean>>> response) {
                        mView.stopRefresh();
                        super.onError(response);
                    }

                });
    }

    public void pingcang(String id, String newPrice) {
        OkGo.<HttpData>post(HttpConfig.BASE_URL + HttpConfig.TRADE_PINGCANG)
                .params("mobile", UserUtil.getMobile())
                .params("order_id", id)
                .params("newprice", newPrice)
                .execute(new JsonCallBack<HttpData>(this) {
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
