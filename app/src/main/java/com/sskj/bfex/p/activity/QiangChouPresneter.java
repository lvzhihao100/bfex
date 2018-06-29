package com.sskj.bfex.p.activity;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.sskj.bfex.m.HttpConfig;
import com.sskj.bfex.m.bean.HttpData;
import com.sskj.bfex.m.bean.RaiseDetail;
import com.sskj.bfex.m.bean.RaiseListBean;
import com.sskj.bfex.m.bean.UserInfo;
import com.sskj.bfex.m.http.JsonCallBack;
import com.sskj.bfex.p.base.BasePresenter;
import com.sskj.bfex.utils.ToastUtil;
import com.sskj.bfex.utils.UserUtil;
import com.sskj.bfex.v.activity.QiangChouActivity;

import java.util.List;

/**
 * Created by Administrator on 2018/4/18.
 */

public class QiangChouPresneter extends BasePresenter<QiangChouActivity> {

    /**
     * 获取币种详情
     * @param code
     */
    public void getRaiseDetail(String code){
        OkGo.<HttpData<RaiseDetail>> post(HttpConfig.WLL+HttpConfig.RAISE_INFO)
                .params("mark", code)
                .execute(new JsonCallBack<HttpData<RaiseDetail>>() {
                    @Override
                    public void onSuccess(Response<HttpData<RaiseDetail>> response) {
                        mView.setView(response.body().getData());
                    }
                });
    }


    /**
     * 获取抢筹列表
     */
    public void getRaiseList() {
        OkGo.<HttpData<List<RaiseListBean>>>post(HttpConfig.BASE_URL + HttpConfig.RAISE_LIST)
                .params("mobile", UserUtil.getMobile())
                .execute(new JsonCallBack<HttpData<List<RaiseListBean>>>() {
                    @Override
                    public void onSuccess(Response<HttpData<List<RaiseListBean>>> response) {
                        HttpData<List<RaiseListBean>> httpListData = response.body();
                        if (httpListData.getStatus() == 200) {
                            mView.setData(httpListData.getData());
                        } else {
                            ToastUtil.showShort(httpListData.getMsg());
                        }
                    }
                });
    }



    public void submit(String mobile,String mark,String buyNum){
        OkGo.<HttpData<RaiseListBean>>post(HttpConfig.WLL+HttpConfig.TJ_CROWD)
                .params("mobile",mobile)
                .params("mark",mark)
                .params("buynum",Integer.valueOf(buyNum))
                .execute(new JsonCallBack<HttpData<RaiseListBean>>() {
                    @Override
                    public void onSuccess(Response<HttpData<RaiseListBean>> response) {
                             if (response.body().getStatus()==200){
                                 mView.submitSuccess();
                             }else{
                                 mView.submitFail(response.body().getMsg());
                             }
                    }
                });
    }


    public void getUserInfo(String mobile) {
        OkGo.<HttpData<UserInfo>>post(HttpConfig.BASE_URL+HttpConfig.GET_USER_INFO)
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
}
