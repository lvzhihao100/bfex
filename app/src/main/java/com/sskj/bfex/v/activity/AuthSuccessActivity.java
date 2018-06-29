package com.sskj.bfex.v.activity;

import android.content.Intent;
import android.widget.Button;
import android.widget.ImageView;

import com.sskj.bfex.R;
import com.sskj.bfex.p.activity.AuthSuccessPresenter;
import com.sskj.bfex.utils.ClickUtil;
import com.sskj.bfex.v.base.BaseActivity;

import butterknife.BindView;

/**
 * <pre>
 *     author : 吕志豪
 *     e-mail : 1030753080@qq.com
 *     time   : 2018/04/03
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class AuthSuccessActivity extends BaseActivity<AuthSuccessPresenter> {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.bt_next_step)
    Button btNextStep;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_auth_success;
    }

    @Override
    public AuthSuccessPresenter getPresenter() {
        return new AuthSuccessPresenter();
    }

    @Override
    protected void onViewBinding() {
        ClickUtil.click(ivBack, () -> onBackPressed());
        ClickUtil.click(btNextStep, () -> {
            startActivity(new Intent(this, RechargeActivity.class));
        });
    }

}
