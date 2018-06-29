package com.sskj.bfex.p.activity;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.sskj.bfex.m.HttpConfig;
import com.sskj.bfex.m.bean.AddressList;
import com.sskj.bfex.m.bean.bean.BaseBean;
import com.sskj.bfex.m.http.JsonCallBack;
import com.sskj.bfex.p.base.BasePresenter;
import com.sskj.bfex.utils.ToastUtil;
import com.sskj.bfex.v.activity.CashAddressAddActivity;
import com.sskj.bfex.v.activity.CashAddressListActivity;

/**
 * Created by Administrator on 2018/4/2 0002.
 */

public class CashPresenter extends BasePresenter {

    public void requestAddressList(String account) {
        OkGo.<AddressList>post(HttpConfig.BASE_URL + HttpConfig.ADDRESS_LIST)
                .params("account", account)
                .execute(new JsonCallBack<AddressList>(this) {

                    @Override
                    public void onSuccess(Response<AddressList> response) {
                        AddressList body = response.body();
                        if (body.status == 200){
                            ((CashAddressListActivity)mView).onResponseCashAddressListSuccess(body.getData().getRes());
                        }else {
                            ToastUtil.showShort(body.msg);
                        }
                    }

                    @Override
                    public void onError(Response<AddressList> response) {
                        super.onError(response);
                    }
                });
    }

    public void requestAddAddress(String address, String notes, String account) {
        OkGo.<BaseBean>post(HttpConfig.BASE_URL + HttpConfig.ADDRESS_ADD)
                .params("qiaobao_url", address)
                .params("notes", notes)
                .params("account", account)
                .execute(new JsonCallBack<BaseBean>(this) {
                    @Override
                    public void onSuccess(Response<BaseBean> response) {
                        BaseBean body = response.body();
                        if (body.status == 200){
                            ToastUtil.showShort(body.msg);
                            ((CashAddressAddActivity)mView).onResponseCashAddressAddSuccess();
                        }else {
                            ToastUtil.showShort(body.msg);
                        }
                    }
                });
    }

    /**
     * 删除提现地址
     * @param position
     * @param id
     */
    public void requestDelite(int position, String id) {
//        OkGo.<BaseBean>post(HttpConfig.BASE_URL + HttpConfig.DELITE_CASH_ADDRESS)
//                .params("id", id)
//                .execute(new JsonCallBack<BaseBean>(this) {
//                    @Override
//                    public void onSuccess(Response<BaseBean> response) {
//                        ToastUtil.showShort(response.body().msg);
//                        if (response.body().status ==1){
//                            ((CashAddressListActivity)mView).onDeliteSuccess(position);
//                        }
//                    }
//                });
    }
}
