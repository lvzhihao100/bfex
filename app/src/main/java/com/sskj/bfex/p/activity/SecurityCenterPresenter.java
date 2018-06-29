package com.sskj.bfex.p.activity;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.sskj.bfex.common.Constants;
import com.sskj.bfex.m.HttpConfig;
import com.sskj.bfex.m.bean.HttpData;
import com.sskj.bfex.m.bean.UserInfo;
import com.sskj.bfex.m.http.JsonCallBack;
import com.sskj.bfex.p.base.BasePresenter;
import com.sskj.bfex.utils.SPUtils;
import com.sskj.bfex.utils.UserUtil;
import com.sskj.bfex.v.activity.SecurityCenterActivity;

/**
 * Created by Administrator on 2018/4/2 0002.
 */

public class SecurityCenterPresenter extends BasePresenter<SecurityCenterActivity> {
    public void request() {
        OkGo.<HttpData<UserInfo>>post(HttpConfig.BASE_URL + HttpConfig.GET_USER_INFO)
                .params("mobile", UserUtil.getMobile())
                .execute(new JsonCallBack<HttpData<UserInfo>>(this) {
                    @Override
                    public void onSuccess(Response<HttpData<UserInfo>> response) {
                        HttpData<UserInfo> httpData = response.body();
                        if (httpData.getStatus() == 200) {
                            SPUtils.putBean(mView, Constants.SP_USER_INFO,httpData.getData());
                            mView.updataStatus(httpData.getData());
                        }
                    }
                });
    }
}
