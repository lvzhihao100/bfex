package com.sskj.bfex.p.fragment;

import com.lzy.okgo.OkGo;
import com.sskj.bfex.m.HttpConfig;
import com.sskj.bfex.m.bean.TouTiao;
import com.sskj.bfex.m.bean.bean.HttpResponse;
import com.sskj.bfex.m.http.JsonCallBack;
import com.sskj.bfex.p.base.BasePresenter;
import com.sskj.bfex.v.fragment.TouTiaoFragment;

import java.util.List;

/**
 * Created by Administrator on 2018/4/16.
 */

public class TouTiaoPresenter extends BasePresenter<TouTiaoFragment> {

    public  void getData(int pageNum){
        OkGo.<HttpResponse<List<TouTiao>>>get(HttpConfig.WLL+HttpConfig.TOUTIAO)
                .params("id",pageNum)
                .execute(new JsonCallBack<HttpResponse<List<TouTiao>>>(this) {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<HttpResponse<List<TouTiao>>> response) {
                            mView.setData(response.body().getData());
                    }
                });

    }

}
