package com.sskj.bfex.p.activity;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.sskj.bfex.m.HttpConfig;
import com.sskj.bfex.m.bean.AseetListBean;
import com.sskj.bfex.m.bean.HttpData;
import com.sskj.bfex.m.bean.UserInfo;
import com.sskj.bfex.m.http.JsonCallBack;
import com.sskj.bfex.p.base.BasePresenter;
import com.sskj.bfex.utils.UserUtil;
import com.sskj.bfex.v.activity.AssetActivity;


public class AssetPresenter extends BasePresenter<AssetActivity> {

    public void getUserInfo(){
        OkGo.<HttpData<UserInfo>>post(HttpConfig.BASE_URL+HttpConfig.GET_USER_INFO)
                .params("mobile", UserUtil.getMobile())
                .execute(new JsonCallBack<HttpData<UserInfo>>() {
                    @Override
                    public void onSuccess(Response<HttpData<UserInfo>> response) {
                        HttpData<UserInfo> httpData = response.body();
                        if (httpData.getStatus() == 200) {
                            mView.updateUserInfo(httpData.getData());
                        }
                    }
                });
    }

    public void loadData() {
        OkGo.<HttpData<AseetListBean>>post(HttpConfig.BASE_URL+HttpConfig.ASEET_LIST)
                .params("mobile", UserUtil.getMobile())
                .execute(new JsonCallBack<HttpData<AseetListBean>>() {
                    @Override
                    public void onSuccess(Response<HttpData<AseetListBean>> response) {
                        HttpData<AseetListBean> httpListData = response.body();
                        if (httpListData.getStatus() == 200) {

                            mView.refresh(httpListData.getData());
                        }
                    }
                });
    }
}
