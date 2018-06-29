package com.sskj.bfex.p.activity;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.sskj.bfex.m.HttpConfig;
import com.sskj.bfex.m.bean.HttpData;
import com.sskj.bfex.m.http.JsonCallBack;
import com.sskj.bfex.p.base.BasePresenter;
import com.sskj.bfex.utils.UserUtil;
import com.sskj.bfex.v.activity.InviteFriendActivity;


public class InviteFriendPresenter extends BasePresenter<InviteFriendActivity> {
    public void upDateUserInfo() {
        OkGo.<HttpData<QRBean>>post(HttpConfig.BASE_URL + HttpConfig.GET_INVITE_URL)
                .params("mobile", UserUtil.getMobile())
                .execute(new JsonCallBack<HttpData<QRBean>>() {
                    @Override
                    public void onSuccess(Response<HttpData<QRBean>> response) {
                        HttpData<QRBean> httpData = response.body();
                        if (httpData.getStatus() == 200) {
                            mView.updateUserInfo(httpData.getData().url);
                        }
                    }
                });
    }
    class QRBean{

        /**
         * url : http://192.168.1.232:8088/wap/register.html?tjuser=TG6980867683251
         * qrc : Poster/3841162.png
         */

        public String url;
        public String qrc;
    }
}
