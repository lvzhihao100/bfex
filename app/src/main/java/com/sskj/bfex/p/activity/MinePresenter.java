package com.sskj.bfex.p.activity;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.sskj.bfex.m.HttpConfig;
import com.sskj.bfex.m.bean.HttpData;
import com.sskj.bfex.m.bean.UserInfo;
import com.sskj.bfex.m.http.JsonCallBack;
import com.sskj.bfex.p.base.BasePresenter;
import com.sskj.bfex.utils.ToastUtil;
import com.sskj.bfex.utils.UserUtil;
import com.sskj.bfex.v.fragment.MineFragment;


/**
 * Created by Lee on 2018/1/25 0025.
 */

public class MinePresenter extends BasePresenter<MineFragment> {

    public void requestUserInfo(String mobile) {
        OkGo.<HttpData<UserInfo>>post(HttpConfig.BASE_URL + HttpConfig.GET_USER_INFO)
                .params("mobile", mobile)
                .execute(new JsonCallBack<HttpData<UserInfo>>() {
                    @Override
                    public void onSuccess(Response<HttpData<UserInfo>> response) {
                        HttpData<UserInfo> httpData = response.body();
                        if (httpData.getStatus() == 200) {
                            mView.updateUserTitle(httpData.getData());
                        }
                    }
                });
    }

    public void sign() {
        OkGo.<HttpData<CoinGetBean>>post(HttpConfig.BASE_URL + HttpConfig.SIGN)
                .params("mobile", UserUtil.getMobile())
                .execute(new JsonCallBack<HttpData<CoinGetBean>>(this) {
                    @Override
                    public void onSuccess(Response<HttpData<CoinGetBean>> response) {
                        HttpData<CoinGetBean> httpListData = response.body();
                        if (httpListData.getStatus() == 200) {
                            ToastUtil.showLong("获得" + httpListData.getData().bz + "币" + httpListData.getData().num + "枚");
                        } else {
                            ToastUtil.showShort(httpListData.getMsg());
                        }
                    }

                });
    }

    class CoinGetBean {
        String bz;
        String num;
    }

}
