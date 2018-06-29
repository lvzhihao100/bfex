package com.sskj.bfex.p.activity;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.sskj.bfex.m.HttpConfig;
import com.sskj.bfex.m.bean.Notice;
import com.sskj.bfex.m.bean.bean.HttpResponse;
import com.sskj.bfex.m.http.JsonCallBack;
import com.sskj.bfex.p.base.BasePresenter;
import com.sskj.bfex.v.activity.WebViewActivity;

/**
 * Created by Administrator on 2018/4/16.
 */

public class NoticeDetailPresenter  extends BasePresenter<WebViewActivity>{
    public void getData(int id){
        OkGo.<HttpResponse<Notice>>get(HttpConfig.WLL+HttpConfig.NOTICEDETAIL)
                .params("id",id)
                .execute(new JsonCallBack<HttpResponse<Notice>>() {
                    @Override
                    public void onSuccess(Response<HttpResponse<Notice>> response) {
                        mView.loadData(response.body().getData().getContent());
                    }
                });

    }


}
