package com.sskj.bfex.p.activity;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.sskj.bfex.m.HttpConfig;
import com.sskj.bfex.m.bean.HttpData;
import com.sskj.bfex.m.bean.RaiseListBean;
import com.sskj.bfex.m.http.JsonCallBack;
import com.sskj.bfex.p.base.BasePresenter;
import com.sskj.bfex.utils.ToastUtil;
import com.sskj.bfex.utils.UserUtil;
import com.sskj.bfex.v.activity.RaiseInfoActivity;

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

public class RaiseInfoPresenter extends BasePresenter<RaiseInfoActivity> {
    public void loadData(String id) {
        OkGo.<HttpData<List<RaiseListBean>>>post(HttpConfig.BASE_URL + HttpConfig.RAISE_LIST)
                .params("mobile", UserUtil.getMobile())
                .params("cro_id", id)
                .execute(new JsonCallBack<HttpData<List<RaiseListBean>>>() {
                    @Override
                    public void onSuccess(Response<HttpData<List<RaiseListBean>>> response) {
                        HttpData<List<RaiseListBean>> httpListData = response.body();
                        if (httpListData.getStatus() == 200 && httpListData.getData() != null && httpListData.getData().size() == 1) {
                            mView.updateUserTitle(httpListData.getData().get(0));
                        } else {
                            ToastUtil.showShort(httpListData.getMsg());
                        }
                    }
                });
    }
}
