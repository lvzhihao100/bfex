package com.sskj.bfex.p.fragment;

import com.lzy.okgo.OkGo;
import com.lzy.okrx2.adapter.FlowableResponse;
import com.sskj.bfex.common.MarketWebSocket;
import com.sskj.bfex.common.rxbus2.NetSubScriber;
import com.sskj.bfex.m.HttpConfig;
import com.sskj.bfex.m.bean.Crowd;
import com.sskj.bfex.m.bean.bean.HttpResponse;
import com.sskj.bfex.m.bean.bean.NewStock;
import com.sskj.bfex.m.http.JsonConverter;
import com.sskj.bfex.p.base.BasePresenter;
import com.sskj.bfex.utils.RxSchedulersHelper;
import com.sskj.bfex.v.fragment.MarketListFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;

/**
 * Created by Lee on 2018/2/28 0028.
 */
public class MarketFragmentPst extends BasePresenter<MarketListFragment> {

    private MarketWebSocket btcWebSocket;
    private MarketWebSocket timeWebSocket;
    String text = "{\"type\":\"vb_okex\"}";

    /**
     * 获取股票最新数据
     *
     * @param type 类型(1-股票;2-商品;3-指数)
     * @param code 编码
     */
    public Flowable<com.lzy.okgo.model.Response<HttpResponse<List<NewStock>>>> getNewInfo(Integer type, String code, boolean update) {

        return OkGo.<HttpResponse<List<NewStock>>>get(HttpConfig.Url + HttpConfig.New_INFO)
                .params("type", type)
                .params("code", code)
                .converter(new JsonConverter<HttpResponse<List<NewStock>>>() {
                })
                .adapt(new FlowableResponse<>());
    }


    public Flowable<com.lzy.okgo.model.Response<HttpResponse<Crowd>>> getCrowd() {
        return OkGo.<HttpResponse<Crowd>>get(HttpConfig.WLL + HttpConfig.CROWD)
                .converter(new JsonConverter<HttpResponse<Crowd>>() {
                })
                .adapt(new FlowableResponse<>());
    }


    public void getData() {
        mView.loading();
        Flowable.zip(getNewInfo(2, "", false), getCrowd(), (listResponse, crowdResponse) -> {
            Map<Integer, Object> map = new HashMap<>();
            map.put(1, listResponse.body().getData());
            map.put(2, crowdResponse.body().getData());
            return map;
        }).compose(RxSchedulersHelper.transformer())
                .subscribe(new NetSubScriber<Map<Integer, Object>>() {
                               @Override
                               public void onSuccess(Map<Integer, Object> data) {
                                   mView.setAdapter(data);
                               }

                               @Override
                               public void onFailure(Throwable t) {
                                   mView.stopLoading();
                                   mView.stopRefresh();
                               }
                           }

                );

    }


    public void initWebSocket(int type) {

        if (btcWebSocket != null) {
            btcWebSocket.closeWebSocket();
            btcWebSocket = null;
        }
        if (timeWebSocket != null) {
            timeWebSocket.closeWebSocket();
            timeWebSocket = null;
        }

        btcWebSocket = new MarketWebSocket(HttpConfig.BTC_WS, "btc", text);
        btcWebSocket.setListener(message -> mView.update(message));

        timeWebSocket = new MarketWebSocket(HttpConfig.TIME_SOCKET, "time", text);
        timeWebSocket.setListener(message -> mView.updateTime(message));
    }

    public void closeWebSocket() {
        if (btcWebSocket != null) {
            btcWebSocket.closeWebSocket();
            btcWebSocket = null;
        }
        if (timeWebSocket != null) {
            timeWebSocket.closeWebSocket();
            timeWebSocket = null;
        }
    }

    @Override
    public void detachView() {
        super.detachView();
        closeWebSocket();
    }
}
