package com.sskj.bfex.p.fragment;

import com.lzy.okgo.OkGo;
import com.sskj.bfex.m.HttpConfig;
import com.sskj.bfex.m.bean.NoticeList;
import com.sskj.bfex.m.bean.bean.HttpResponse;
import com.sskj.bfex.m.http.JsonCallBack;
import com.sskj.bfex.p.base.BasePresenter;
import com.sskj.bfex.v.fragment.NoticeFragment;

import java.util.List;

/**
 * Created by Administrator on 2018/4/16.
 */

public class NoticePresenter extends BasePresenter<NoticeFragment> {

    public void getData(int pageNum) {
        OkGo.<HttpResponse<List<NoticeList>>>get(HttpConfig.WLL + HttpConfig.NOTICE)
                .params("id", pageNum)
                .execute(new JsonCallBack<HttpResponse<List<NoticeList>>>(this) {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<HttpResponse<List<NoticeList>>> response) {
                        mView.stopRefresh();
                        mView.setData(response.body().getData());
                    }
                });
    }
}
