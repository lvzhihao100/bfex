package com.sskj.bfex.v.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sskj.bfex.MyAppLication;
import com.sskj.bfex.R;
import com.sskj.bfex.common.Constants;
import com.sskj.bfex.common.rxbus2.RxBus;
import com.sskj.bfex.p.activity.PwdResetPresenter;
import com.sskj.bfex.utils.CheckUtil;
import com.sskj.bfex.utils.ClickUtil;
import com.sskj.bfex.utils.SPUtils;
import com.sskj.bfex.utils.ToastUtil;
import com.sskj.bfex.v.base.BaseActivity;

import butterknife.BindView;

/**
 * <pre>
 *     author : 吕志豪
 *     e-mail : 1030753080@qq.com
 *     time   : 2018/04/02
 *     desc   :密码修改或重置页面，默认为重置页面，修改页面传递Constants.IS_RESET false
 *     version: 1.0
 * </pre>
 */

public class PwdResetActivity extends BaseActivity<PwdResetPresenter> {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_pwd_origin)
    EditText etPwdOrigin;
    @BindView(R.id.iv_toggle_pwd_origin)
    ImageView ivTogglePwdOrigin;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.iv_toggle_pwd)
    ImageView ivTogglePwd;
    @BindView(R.id.et_pwd_again)
    EditText etPwdAgain;
    @BindView(R.id.iv_toggle_pwd_again)
    ImageView ivTogglePwdAgain;
    @BindView(R.id.bt_next_step)
    Button btNextStep;
    @BindView(R.id.rl_origin)
    RelativeLayout rlOrigin;
    private boolean isSee = false;
    private boolean isSeeAgain = false;
    private boolean isSeeOrigin = false;
    private String phone;
    private String code;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_pws_reset;
    }

    @Override
    public PwdResetPresenter getPresenter() {
        return new PwdResetPresenter();
    }

    @Override
    protected void onViewBinding() {
        //true 忘记密码进入 false 安全中心进入
        boolean isReset = getIntent().getBooleanExtra(Constants.IS_RESET, true);
        rlOrigin.setVisibility(isReset ? View.GONE : View.VISIBLE);
        if (isReset) {
            phone = getIntent().getStringExtra(Constants.PHONE);
            code = getIntent().getStringExtra(Constants.CODE);
        } else {
            phone = (String) SPUtils.get(MyAppLication.getApplication(), Constants.SP_Mobile, "");
        }
        ClickUtil.click(ivBack, this::onBackPressed);
        ClickUtil.click(btNextStep, () -> {

            if (isReset) {
                mPresenter.reset(phone, etPwd.getText().toString(), code);
            } else {
                if (etPwdOrigin.getText().toString().equals(etPwd.getText().toString())) {
                    ToastUtil.showShort("新密码与旧密码不能一致");
                    return;
                }
                if (!CheckUtil.isPayPwd(etPwdOrigin.getText().toString())) {
                    ToastUtil.showShort("原密码格式不正确");
                    return;
                } else {
                    mPresenter.resetWithOrigin(phone, etPwdOrigin.getText().toString(), etPwd.getText().toString());
                }
            }
        });
        etPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (CheckUtil.isPayPwd(s.toString())) {
                    if (s.toString().equals(etPwdAgain.getText().toString())) {
                        btNextStep.setEnabled(true);
                    } else {
                        btNextStep.setEnabled(false);
                    }
                } else {
                    btNextStep.setEnabled(false);
                }
            }
        });
        etPwdAgain.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (CheckUtil.isPayPwd(s.toString())) {
                    if (s.toString().equals(etPwd.getText().toString())) {
                        btNextStep.setEnabled(true);
                    } else {
                        btNextStep.setEnabled(false);
                    }
                } else {
                    btNextStep.setEnabled(false);
                }
            }
        });
        ClickUtil.click(200, ivTogglePwdAgain, () -> {
            if (isSeeAgain) {
                isSeeAgain = false;
                etPwdAgain.setTransformationMethod(PasswordTransformationMethod.getInstance());
                ivTogglePwdAgain.setImageResource(R.mipmap.unsee);
            } else {
                isSeeAgain = true;
                etPwdAgain.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                ivTogglePwdAgain.setImageResource(R.mipmap.see);
            }
        });
        ClickUtil.click(200, ivTogglePwd, () -> {
            if (isSee) {
                isSee = false;
                etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                ivTogglePwd.setImageResource(R.mipmap.unsee);
            } else {
                isSee = true;
                etPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                ivTogglePwd.setImageResource(R.mipmap.see);
            }
        });
        ClickUtil.click(200, ivTogglePwdOrigin, () -> {
            if (isSeeOrigin) {
                isSeeOrigin = false;
                etPwdOrigin.setTransformationMethod(PasswordTransformationMethod.getInstance());
                ivTogglePwdOrigin.setImageResource(R.mipmap.unsee);
            } else {
                isSeeOrigin = true;
                etPwdOrigin.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                ivTogglePwdOrigin.setImageResource(R.mipmap.see);
            }
        });
    }

    public void toLogin() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void resetSuccess() {
        RxBus.getDefault().send(Constants.USER_LOGOUT);
        toLogin();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getDefault().unregister(this);
    }
}
