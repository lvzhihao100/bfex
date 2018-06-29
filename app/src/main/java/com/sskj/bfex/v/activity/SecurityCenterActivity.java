package com.sskj.bfex.v.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.sskj.bfex.R;
import com.sskj.bfex.common.Constants;
import com.sskj.bfex.common.rxbus2.RxBus;
import com.sskj.bfex.common.widget.ToolBarLayout;
import com.sskj.bfex.m.bean.UserInfo;
import com.sskj.bfex.p.activity.SecurityCenterPresenter;
import com.sskj.bfex.utils.ToastUtil;
import com.sskj.bfex.v.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

import static com.sskj.bfex.common.Constants.PAY_PWD_SETTING_CODE;
import static com.sskj.bfex.common.Constants.VERIFY_IDENT_CODE;

/**
 * 安全中心
 * create by Hey at 2018/4/20 15:04
 */
public class SecurityCenterActivity extends BaseActivity<SecurityCenterPresenter> {


    @BindView(R.id.security_mobile_checked)
    TextView mSecurityMobileChecked;
    @BindView(R.id.security_email_checked)
    TextView mSecurityEmailChecked;
    @BindView(R.id.security_login_paw_checked)
    TextView mSecurityLoginPawChecked;
    @BindView(R.id.security_pay_psw_checked)
    TextView mSecurityPayPswChecked;
    @BindView(R.id.security_verify_checked)
    TextView mSecurityVerifyChecked;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.toolBar)
    ToolBarLayout mToolBar;
    private UserInfo mUserInfo;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_security_center;
    }

    @Override
    public SecurityCenterPresenter getPresenter() {
        return new SecurityCenterPresenter();
    }

    @Override
    protected void onViewBinding() {
        mTitle.setText("安全中心");
        mToolBar.setLeftButtonOnClickLinster(v -> onBackPressed());
        mPresenter.request();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && mUserInfo != null) {
            switch (requestCode) {
                case Constants.BIND_EMAIL_CODE:
                    String email = data.getStringExtra("resultData");
                    mUserInfo.mail = email;
                    mSecurityEmailChecked.setText(email);
                    RxBus.getDefault().post(mUserInfo);
                    break;
                case Constants.PAY_PWD_SETTING_CODE:
                    String pwd = data.getStringExtra("resultData");
                    mUserInfo.tpwd = pwd;
                    mSecurityPayPswChecked.setText("已设置");
                    RxBus.getDefault().post(mUserInfo);
                    break;
                case VERIFY_IDENT_CODE:
                    int status = data.getIntExtra("resultData", 1);
                    if (status == 2) {
                        mUserInfo.status = 2;
                        mSecurityVerifyChecked.setText("审核中");
                    }
                    RxBus.getDefault().post(mUserInfo);
                    break;
            }
        }
    }

    @OnClick({R.id.security_mobile_layout, R.id.security_email_layout, R.id.security_login_paw_layout, R.id.security_pay_psw_layout, R.id.security_verify_layout})
    public void onViewClicked(View view) {
        if (mUserInfo != null) {
            switch (view.getId()) {
                case R.id.security_email_layout:
                    identOpenActivity(BindEmailActivity.class, Constants.BIND_EMAIL_CODE);
                    break;
                case R.id.security_login_paw_layout:
                    Intent intent = new Intent(mActivity, PwdResetActivity.class);
                    intent.putExtra(Constants.IS_RESET, false);
                    startActivity(intent);
                    break;
                case R.id.security_pay_psw_layout:
                    identOpenActivity(PayPwdSettingActivity.class, PAY_PWD_SETTING_CODE);
                    break;
                case R.id.security_verify_layout:
                    if (mUserInfo.status == 1 || mUserInfo.status == 4) {//未认证及已拒绝，重新认证
                        openActivity(VerifyIdentActivity.class, VERIFY_IDENT_CODE);
                    } else if (mUserInfo.status == 3) {//已认证
                        startActivity(new Intent(this, CommonActivity.class));
                    } else if (mUserInfo.status == 2) {//审核中
                        ToastUtil.showShort(getString(R.string.wait_ident));
                    }
                    break;
            }
        }

    }

    private void identOpenActivity(Class<? extends Activity> clz, int requestCode) {
        if (mUserInfo != null) {
            if (mUserInfo.status == 3) {//已实名认证
                openActivity(clz, requestCode);
            } else if (mUserInfo.status == 2) {//审核中
                ToastUtil.showShort("请等待实名认证通过");
            } else {
                ToastUtil.showShort("请先实名认证");
                openActivity(VerifyIdentActivity.class, VERIFY_IDENT_CODE);
            }
        }
    }

    private void openActivity(Class<? extends Activity> clz, int requestCode) {
        if (!TextUtils.isEmpty(mMobile)) {
            startActivityForResult(new Intent(mActivity, clz), requestCode);
        } else {
            startActivity(new Intent(mActivity, LoginActivity.class));
        }
    }


    public void updataStatus(UserInfo user) {
        mUserInfo = user;
        if (!TextUtils.isEmpty(user.mail)/* && CheckUtil.isEmail(user.mail)*/) {
            mSecurityEmailChecked.setText(user.mail);
        } else {
            mSecurityEmailChecked.setText("未绑定");
        }
        mSecurityLoginPawChecked.setText("已设置");

        if (user.tpwd != null && user.tpwd.equals("1")) {
            mSecurityPayPswChecked.setText("已设置");
        } else {
            mSecurityPayPswChecked.setText("未设置");
        }

        switch (user.status) {
            case 1:
                mSecurityVerifyChecked.setText("未认证");
                break;
            case 2:
                mSecurityVerifyChecked.setText("审核中");
                break;
            case 3:
                mSecurityVerifyChecked.setText("已认证");
                break;
            case 4:
                mSecurityVerifyChecked.setText("已拒绝");
//                mSecurityVerifyChecked.setTextColor();
                break;
        }
    }
}
