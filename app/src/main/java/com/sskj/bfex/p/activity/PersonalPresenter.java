package com.sskj.bfex.p.activity;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.sskj.bfex.m.HttpConfig;
import com.sskj.bfex.m.bean.HttpData;
import com.sskj.bfex.m.bean.UserInfo;
import com.sskj.bfex.m.http.JsonCallBack;
import com.sskj.bfex.p.base.BasePresenter;
import com.sskj.bfex.v.activity.PersonalActivity;


/**
 * Created by Lee on 2018/1/25 0025.
 */

public class PersonalPresenter extends BasePresenter<PersonalActivity> {

    public void request(String mobile) {
        OkGo.<HttpData<UserInfo>>post(HttpConfig.BASE_URL + HttpConfig.GET_USER_INFO)
                .params("mobile", mobile)
                .execute(new JsonCallBack<HttpData<UserInfo>>(this) {
                    @Override
                    public void onSuccess(Response<HttpData<UserInfo>> response) {
                        HttpData<UserInfo> httpData = response.body();
                        if (httpData.getStatus() == 200) {
                            mView.updateUserTitle(httpData.getData());
                        }
                    }
                });
    }

}
