package com.sskj.bfex.p.activity;

import android.text.TextUtils;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.sskj.bfex.m.HttpConfig;
import com.sskj.bfex.m.bean.HttpData;
import com.sskj.bfex.m.bean.PublicData;
import com.sskj.bfex.m.bean.UserInfo;
import com.sskj.bfex.m.http.JsonCallBack;
import com.sskj.bfex.p.base.BasePresenter;
import com.sskj.bfex.v.activity.MainActivity;


/**
 * Created by Lee on 2018/1/25 0025.
 */

public class MainPresenter extends BasePresenter<MainActivity> {

    public void upDateUserInfo(String mobile) {
        OkGo.<HttpData<UserInfo>>post(HttpConfig.BASE_URL + HttpConfig.GET_USER_INFO)
                .params("mobile", mobile)
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


    public void getNotice() {
        OkGo.<HttpData<PublicData>>post(HttpConfig.WLL + HttpConfig.PUBLIC_DATA)
                .execute(new JsonCallBack<HttpData<PublicData>>() {
                    @Override
                    public void onSuccess(Response<HttpData<PublicData>> response) {
                        HttpData<PublicData> httpData = response.body();
                        if (httpData.getStatus() == 200) {
                            if (!TextUtils.isEmpty(httpData.getData().getTpgg())) {
                                mView.showNotice(httpData.getData().getTpgg());
                            }
                        }
                    }
                });

    }

}
