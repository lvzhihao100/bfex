package com.sskj.bfex.v.activity;

import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.sskj.bfex.MyAppLication;
import com.sskj.bfex.R;
import com.sskj.bfex.common.Constants;
import com.sskj.bfex.p.activity.MobileCheckPresenter;
import com.sskj.bfex.utils.ClickUtil;
import com.sskj.bfex.utils.SPUtils;
import com.sskj.bfex.v.base.BaseActivity;

import butterknife.BindView;

/**
 * <pre>
 *     author : 吕志豪
 *     e-mail : 1030753080@qq.com
 *     time   : 2018/04/04
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class MobileCheckActivity extends BaseActivity<MobileCheckPresenter> {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_mobile)
    TextView tvMobile;
    @BindView(R.id.sms_switch)
    Switch smsSwitch;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_mobile_check;
    }

    @Override
    public MobileCheckPresenter getPresenter() {
        return new MobileCheckPresenter();
    }

    @Override
    protected void onViewBinding() {

        String mobile = (String) SPUtils.get(MyAppLication.getApplication(), Constants.SP_Mobile, "");
        if (!TextUtils.isEmpty(mobile)) {
            String start = mobile.substring(0, 3);
            String end = mobile.substring(7, 11);
            tvMobile.setText(start + "****" + end);
        }
        ClickUtil.click(ivBack,() -> onBackPressed());
    }


}
