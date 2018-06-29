package com.sskj.bfex.v.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sskj.bfex.R;
import com.sskj.bfex.common.Constants;
import com.sskj.bfex.p.activity.PasswordSetPresenter;
import com.sskj.bfex.utils.CheckUtil;
import com.sskj.bfex.utils.ClickUtil;
import com.sskj.bfex.utils.ToastUtil;
import com.sskj.bfex.v.base.BaseActivity;

import butterknife.BindView;

/**
 * <pre>
 *     author : 吕志豪
 *     e-mail : 1030753080@qq.com
 *     time   : 2018/04/02
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class PwdSetActivity extends BaseActivity<PasswordSetPresenter> {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.iv_toggle_pwd)
    ImageView ivTogglePwd;
    @BindView(R.id.et_pwd_again)
    EditText etPwdAgain;
    @BindView(R.id.iv_toggle_pwd_again)
    ImageView ivTogglePwdAgain;
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.tv_me)
    TextView tvMe;
    @BindView(R.id.bt_next_step)
    Button btNextStep;
    @BindView(R.id.et_invite_code)
    EditText etInviteCode;
    @BindView(R.id.tv_contract)
    TextView tvContract;

    private boolean isSee = false;
    private boolean isSeeAgain = false;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_pws_set;
    }

    @Override
    public PasswordSetPresenter getPresenter() {
        return new PasswordSetPresenter();
    }

    @Override
    protected void onViewBinding() {
        String mobile = getIntent().getStringExtra(Constants.PHONE);
        String code = getIntent().getStringExtra(Constants.CODE);
        ClickUtil.click(ivBack, this::onBackPressed);
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
        ClickUtil.click(btNextStep, () -> {
            if (!CheckUtil.isPayPwd(etPwd.getText().toString()) || !CheckUtil.isPayPwd(etPwdAgain.getText().toString())) {
                ToastUtil.showShort("密码格式不符合");
                return;
            }
            if (!etPwd.getText().toString().equals(etPwdAgain.getText().toString())) {
                ToastUtil.showShort("两次输入密码不一致");
                return;
            }
            mPresenter.register(mobile, etPwd.getText().toString(), TextUtils.isEmpty(etInviteCode.getText().toString()) ? null : etInviteCode.getText().toString(), code);
        });
//        etInviteCode.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (checkbox.isChecked() && etPwdAgain.getText().toString().equals(etPwd.getText().toString()) && CheckUtil.isPayPwd(etPwd.getText().toString()) && s.length() > 0) {
//                    btNextStep.setEnabled(true);
//                } else {
//                    btNextStep.setEnabled(false);
//                }
//            }
//        });
        etPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals(etPwdAgain.getText().toString()) && checkbox.isChecked() && CheckUtil.isPayPwd(s.toString()) /*&& etInviteCode.getText().length() > 0*/) {
                    btNextStep.setEnabled(true);
                } else {
                    btNextStep.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals(etPwdAgain.getText().toString()) && checkbox.isChecked() && CheckUtil.isPayPwd(s.toString()) /*&& etInviteCode.getText().length() > 0*/) {
                    btNextStep.setEnabled(true);
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
                if (s.toString().equals(etPwd.getText().toString()) && checkbox.isChecked() && CheckUtil.isPayPwd(s.toString()) /*&& etInviteCode.getText().length() > 0*/) {
                    btNextStep.setEnabled(true);
                } else {
                    btNextStep.setEnabled(false);
                }
            }
        });
        checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked && etPwdAgain.getText().toString().equals(etPwd.getText().toString()) && CheckUtil.isPayPwd(etPwd.getText().toString()) /*&& etInviteCode.getText().length() > 0*/) {
                btNextStep.setEnabled(true);
            } else {
                btNextStep.setEnabled(false);
            }
        });
        ClickUtil.click(tvContract, () -> {
            startActivity(new Intent(this, WebActivity.class));
        });
    }

    public void registerSuccess() {
        startActivity(new Intent(this, LoginActivity.class));
    }


}
