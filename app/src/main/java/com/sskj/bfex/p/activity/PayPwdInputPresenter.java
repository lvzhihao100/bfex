package com.sskj.bfex.p.activity;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.sskj.bfex.m.HttpConfig;
import com.sskj.bfex.m.bean.HttpData;
import com.sskj.bfex.m.bean.StockBean;
import com.sskj.bfex.m.bean.bean.BaseBean;
import com.sskj.bfex.m.http.JsonCallBack;
import com.sskj.bfex.p.base.BasePresenter;
import com.sskj.bfex.utils.ToastUtil;
import com.sskj.bfex.utils.UserUtil;
import com.sskj.bfex.v.activity.PayPwdInputActivity;

/**
 * <pre>
 *     author : wk_math
 *     e-mail : wk_math@163.com
 *     time   : 2018/03/30
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class PayPwdInputPresenter extends BasePresenter<PayPwdInputActivity> {

    /**
     * 验证支付密码
     *
     * @param pass
     */
    public void checkPayPwd(String pass) {
        OkGo.<HttpData>post(HttpConfig.BASE_URL + HttpConfig.CHECK_PAY_PASS)
                .params("mobile", UserUtil.getMobile())
                .params("tpwd", pass)
                .execute(new JsonCallBack<HttpData>(this) {
                    @Override
                    public void onSuccess(Response<HttpData> response) {
                        HttpData httpListData = response.body();
//                        mView.checkOk();
                        if (httpListData.getStatus() == 200) {
                            mView.checkOk();
                        } else {
                            ToastUtil.showShort(httpListData.getMsg());
                        }
                    }
                });
    }

    public void createOrder(StockBean stockData, String pwd) {
        OkGo.<BaseBean>post(HttpConfig.BASE_URL + HttpConfig.TRADE_CREATE)
                .params("mobile", UserUtil.getMobile())
                .params("newprice", stockData.newprice)
                .params("type", stockData.type)
                .params("buynum", stockData.buynum)
                .params("shopname", stockData.shopname.toLowerCase())
                .params("otype", stockData.otype)
                .params("pwd", pwd)
                .execute(new JsonCallBack<BaseBean>(this) {
                    @Override
                    public void onSuccess(Response<BaseBean> response) {
                        BaseBean baseBean = response.body();
                        if (baseBean.status == 200) {
                            mView.onCreateOrderSuccess();
                        } else {
                            ToastUtil.showShort(baseBean.msg);
                        }
                    }
                });
    }

    public void createRaiseOrder(StockBean stockData, String pwd) {
        OkGo.<BaseBean>post(HttpConfig.BASE_URL + HttpConfig.TRADE_RAISE_CREATE)
                .params("mobile", UserUtil.getMobile())
                .params("notes", stockData.code)
                .params("buynum", stockData.buynum)
                .params("pwd", pwd)
                .execute(new JsonCallBack<BaseBean>(this) {
                    @Override
                    public void onSuccess(Response<BaseBean> response) {
                        BaseBean baseBean = response.body();
                        if (baseBean.status == 200) {
                            mView.onCreateOrderSuccess();
                        } else {
                            ToastUtil.showShort(baseBean.msg);
                        }
                    }
                });
    }

}
