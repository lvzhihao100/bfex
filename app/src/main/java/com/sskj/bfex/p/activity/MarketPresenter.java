package com.sskj.bfex.p.activity;

import com.lzy.okgo.OkGo;
import com.sskj.bfex.m.HttpConfig;
import com.sskj.bfex.m.bean.bean.HttpResponse;
import com.sskj.bfex.m.bean.bean.NewStock;
import com.sskj.bfex.m.http.JsonCallBack;
import com.sskj.bfex.p.base.BasePresenter;
import com.sskj.bfex.v.activity.MarketActivity;

import java.util.List;

/**
*行情
*@author Hey
*created at 2018/4/2 18:16
*/
public class MarketPresenter extends BasePresenter<MarketActivity> {

    /**
     * 获取股票最新数据
     * @param type 类型(1-股票;2-商品;3-指数)
     * @param code 编码
     */
    public void getNewInfo(Integer type,String code){
        OkGo.<HttpResponse<List<NewStock>>> get( HttpConfig.Url+HttpConfig.New_INFO)
                .params("type",type)
                .params("code",code)
                .execute(new JsonCallBack<HttpResponse<List<NewStock>>>(this) {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<HttpResponse<List<NewStock>>> response) {
                        List<NewStock> stocks=  response.body().getData();
                        if ( stocks!=null&&stocks.size()>0){
                            mView.updateView(stocks.get(0));
                        }
                    }
                });
    }


}
