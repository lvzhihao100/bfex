package com.sskj.bfex.p.fragment;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.sskj.bfex.m.HttpConfig;
import com.sskj.bfex.m.bean.HttpData;
import com.sskj.bfex.m.bean.TradeDealBean;
import com.sskj.bfex.m.http.JsonCallBack;
import com.sskj.bfex.p.base.BasePresenter;
import com.sskj.bfex.v.fragment.TradeFragment;

import java.util.List;

/**
 * @author Hey
 * created at 2018/4/4 13:34
 */
public class TradePresenter extends BasePresenter<TradeFragment> {

    /**
     * 获取成交数据
     *
     * @author Hey
     * created at 2018/4/4 13:23
     */
    public void getTradeList(String code, boolean update) {
        OkGo.<HttpData<List<TradeDealBean>>>post(HttpConfig.BASE_URL + HttpConfig.TOTAL_CHENGJIAO)
                .params("code", code)
                .execute(new JsonCallBack<HttpData<List<TradeDealBean>>>() {
                    @Override
                    public void onSuccess(Response<HttpData<List<TradeDealBean>>> response) {
                        HttpData<List<TradeDealBean>> bean = response.body();
                        if (update) {
                            mView.update(bean.getData());
                        } else {
                            mView.setData(bean.getData());
                        }
                    }
                });
    }

}
