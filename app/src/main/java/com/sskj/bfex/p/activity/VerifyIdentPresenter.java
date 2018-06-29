package com.sskj.bfex.p.activity;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.sskj.bfex.MyAppLication;
import com.sskj.bfex.common.Constants;
import com.sskj.bfex.m.HttpConfig;
import com.sskj.bfex.m.bean.bean.BaseBean;
import com.sskj.bfex.m.http.JsonCallBack;
import com.sskj.bfex.p.base.BasePresenter;
import com.sskj.bfex.utils.SPUtils;
import com.sskj.bfex.utils.ToastUtil;
import com.sskj.bfex.v.activity.VerifyIdentActivity;

import java.io.File;
import java.util.HashMap;

/**
 * Created by Administrator on 2018/4/3 0003.
 */

public class VerifyIdentPresenter extends BasePresenter<VerifyIdentActivity> {
    public void submitVerify(String realName, String identNum, HashMap<String, Object> params) {
        String mobile = (String) SPUtils.get(MyAppLication.getApplication(), Constants.SP_Mobile, "");

        OkGo.<BaseBean>post(HttpConfig.BASE_URL + HttpConfig.IDENT_AUTH)
                .isMultipart(false)
                .params("realname", realName)
                .params("mobile", mobile)
                .params("idcard", identNum)
                .params("image3", (File) params.get("photo_a"), (String) params.get("photoAName"))
                .params("image1", (File) params.get("photo_b"), (String) params.get("photoBName"))
                .params("image2", (File) params.get("photo_c"), (String) params.get("photoCName"))
                .execute(new JsonCallBack<BaseBean>(this) {
                    @Override
                    public void onSuccess(Response<BaseBean> response) {
                        BaseBean body = response.body();
                        if (body.status == 200) {
                            mView.onSubmitVerifyResponseSuccess();
                        } else {
//                            mView.onSubmitVerifyResponseSuccess();
                            ToastUtil.showShort(body.msg);
                        }
                    }
                });
    }
}
