package com.sskj.bfex.p.fragment;

import com.lzy.okgo.OkGo;
import com.sskj.bfex.m.HttpConfig;
import com.sskj.bfex.m.bean.bean.HttpResponse;
import com.sskj.bfex.m.bean.bean.Stock;
import com.sskj.bfex.m.http.JsonCallBack;
import com.sskj.bfex.p.base.BasePresenter;
import com.sskj.bfex.v.fragment.MinuteFragment;

import java.util.List;

/**
 * Created by Administrator on 2018/4/3.
 */

public class StockPresenter extends BasePresenter<MinuteFragment> {
    /**
     * 获取股票K线
     *
     * @param action
     * @param goodsType K线周期(minute/minute5/minute15/minute30/minute60/day)
     * @param code      (BCH_USDT/ETC_USDT/ETH_USDT/BTC_USDT/LTC_USDT )
     * @param page
     * @param pageSize
     */
    public void getStockInfo(String action, String goodsType, String code, Integer page, Integer pageSize, boolean update) {
        OkGo.<HttpResponse<List<Stock>>>get(HttpConfig.Url + HttpConfig.GOODS_INFO)
                .params("action", action)
                .params("goodsType", goodsType)
                .params("code", code)
                .execute(new JsonCallBack<HttpResponse<List<Stock>>>(this, !update) {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<HttpResponse<List<Stock>>> response) {
                        if ("OK".equals(response.body().getMsg())) {
                            List<Stock> stockList = response.body().getData();
                            if (stockList != null) {
                                mView.setUpdate(update);
                                mView.setChart(stockList);
                            }
                        }
                    }
                });
    }

    /**
     * 获取交易币数据
     */
    public void getTime(String goodsType, String code, boolean update) {
        OkGo.<HttpResponse<List<Stock>>>get(HttpConfig.WLL+"index.php/Home/test/index")
                .params("goodsType", goodsType)
                .params("code", code)
                .execute(new JsonCallBack<HttpResponse<List<Stock>>>(this, !update) {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<HttpResponse<List<Stock>>> response) {
                        List<Stock> stocks = response.body().getData();
                        if (stocks != null && stocks.size() > 0) {
                            mView.setUpdate(update);
                            mView.setChart(stocks);
                        }
                    }
                });


    }

}
