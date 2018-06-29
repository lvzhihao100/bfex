package com.sskj.bfex.v.activity;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sskj.bfex.R;
import com.sskj.bfex.common.Constants;
import com.sskj.bfex.common.rxbus2.RxBus;
import com.sskj.bfex.p.activity.LoginPresenter;
import com.sskj.bfex.utils.CheckUtil;
import com.sskj.bfex.utils.ClickUtil;
import com.sskj.bfex.utils.ToastUtil;
import com.sskj.bfex.v.base.BaseActivity;

import butterknife.BindView;

/**
 * <pre>
 *     author : wk_math
 *     e-mail : wk_math@163.com
 *     time   : 2018/03/30
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class LoginActivity extends BaseActivity<LoginPresenter> {
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.iv_toggle_pwd)
    ImageView ivTogglePwd;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.tv_goto_register)
    TextView tvGotoRegister;
    @BindView(R.id.tv_pwd_forget)
    TextView tvPwdForget;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    private boolean isSee = false;

    private String type;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    public LoginPresenter getPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void onViewBinding() {
        ClickUtil.click(400, ivTogglePwd, () -> {
            if (isSee) {
                isSee = false;
                etPwd.setTransformationMethod(new PasswordTransformationMethod());
                ivTogglePwd.setImageResource(R.mipmap.unsee);
            } else {
                isSee = true;
                etPwd.setTransformationMethod(new HideReturnsTransformationMethod());
                ivTogglePwd.setImageResource(R.mipmap.see);
            }
        });
        ClickUtil.click(btLogin, () -> {
            if (CheckUtil.isMobile(etPhone.getText().toString()) || CheckUtil.isEmail(etPhone.getText().toString())) {
                if (etPwd.getText().length() >= 6 && etPwd.getText().length() <= 20) {
                    mPresenter.login(etPhone.getText().toString(), etPwd.getText().toString());
                } else {
                    ToastUtil.showShort("密码格式错误");
                }
            } else {
                ToastUtil.showShort("帐号格式错误");
            }
        });
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (CheckUtil.isMobile(s.toString()) || CheckUtil.isEmail(s.toString())) {
                    btLogin.setEnabled(true);
                } else {
                    btLogin.setEnabled(false);
                }
            }
        });
        ClickUtil.click(tvGotoRegister, () -> startActivity(new Intent(this, RegisterActivity.class)));
        ClickUtil.click(tvPwdForget, () -> {
            startActivity(new Intent(this, PwdForgetActivity.class));
        });
        ClickUtil.click(ivBack, () -> onBackPressed());

    }

    @Override
    protected void initView() {
        if (getIntent() != null) {
            type = getIntent().getStringExtra("from");
        }
    }

    /**
     * 登录
     *
     * @param context context
     * @param from    来源
     */
    public static void start(Context context, String from) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra("from", from);
        context.startActivity(intent);
    }

    /**
     * 登录成功
     */
    public void loginSuccess() {
        if (!TextUtils.isEmpty(type)) {
            RxBus.getDefault().send(Constants.USER_LOGIN);
            setResult(RESULT_OK);
            finish();
        } else {
            RxBus.getDefault().send(Constants.USER_LOGIN);
            startActivity(new Intent(this, MainActivity.class));
        }

    }

}
