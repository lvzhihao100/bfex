package com.sskj.bfex.p.activity;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.sskj.bfex.m.HttpConfig;
import com.sskj.bfex.m.bean.HttpData;
import com.sskj.bfex.m.bean.WithdrawListBean;
import com.sskj.bfex.m.http.JsonCallBack;
import com.sskj.bfex.p.base.BasePresenter;
import com.sskj.bfex.utils.UserUtil;
import com.sskj.bfex.v.activity.WithdrawRecordActivity;

import java.util.List;

/**
 * <pre>
 *     author : wk_math
 *     e-mail : wk_math@163.com
 *     time   : 2018/03/30
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class WithdrawRecordPresenter extends BasePresenter<WithdrawRecordActivity> {

    public void getWithdrawList() {
        OkGo.<HttpData<List<WithdrawListBean>>>post(HttpConfig.BASE_URL + HttpConfig.WITHDRAW_LIST)
                .params("mobile", UserUtil.getMobile())
                .execute(new JsonCallBack<HttpData<List<WithdrawListBean>>>() {
                    @Override
                    public void onSuccess(Response<HttpData<List<WithdrawListBean>>> response) {
                        HttpData<List<WithdrawListBean>> httpListData = response.body();
                        if (httpListData.getStatus() == 200) {
                            mView.refresh(httpListData.getData());
                        } else {
                            mView.refresh(null);
                        }
                    }

                    @Override
                    public void onError(Response<HttpData<List<WithdrawListBean>>> response) {
                        mView.refresh(null);
                        super.onError(response);

                    }
                });
    }
}
