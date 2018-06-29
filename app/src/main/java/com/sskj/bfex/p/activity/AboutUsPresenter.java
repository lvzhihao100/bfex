package com.sskj.bfex.p.activity;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.sskj.bfex.m.HttpConfig;
import com.sskj.bfex.m.bean.HttpData;
import com.sskj.bfex.m.bean.XiTongBean;
import com.sskj.bfex.m.http.JsonCallBack;
import com.sskj.bfex.p.base.BasePresenter;
import com.sskj.bfex.utils.ToastUtil;
import com.sskj.bfex.v.activity.AboutUsActivity;

/**
 * <pre>
 *     author : wk_math
 *     e-mail : wk_math@163.com
 *     time   : 2018/03/30
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class AboutUsPresenter extends BasePresenter<AboutUsActivity> {

    public void getEmail() {
        OkGo.<HttpData<XiTongBean>>post(HttpConfig.BASE_URL + HttpConfig.GET_EMAIL)
                .execute(new JsonCallBack<HttpData<XiTongBean>>() {
                    @Override
                    public void onSuccess(Response<HttpData<XiTongBean>> response) {
                        HttpData<XiTongBean> httpData = response.body();
                        if (httpData.getStatus() == 200) {
                            mView.dialogEmail(httpData.getData().email);
                        } else {
                            ToastUtil.showShort(httpData.getMsg());
                        }
                    }
                });
//        OkGo.<HttpData>post(HttpConfig.BASE_URL + HttpConfig.GET_USER_INFO)
//                .params("mobile", UserUtil.getMobile())
//                .execute(new JsonCallBack<HttpData>() {
//                    @Override
//                    public void onSuccess(Response<HttpData> response) {
//                        HttpData httpListData = response.body();
//                        mView.dialogEmail(httpListData.getMsg());
//                    }
//                });
    }
}
