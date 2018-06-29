package com.sskj.bfex.v.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sskj.bfex.R;
import com.sskj.bfex.common.Constants;
import com.sskj.bfex.p.activity.RegisterPresenter;
import com.sskj.bfex.utils.CheckUtil;
import com.sskj.bfex.utils.ClickUtil;
import com.sskj.bfex.utils.ToastUtil;
import com.sskj.bfex.v.base.BaseActivity;

import butterknife.BindView;

/**
 * <pre>
 *     author : wk_math
 *     e-mail : wk_math@163.com
 *     time   : 2018/04/02
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class RegisterActivity extends BaseActivity<RegisterPresenter> {
    @BindView(R.id.tv_goto_login)
    TextView tvGotoLogin;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.iv_delete)
    ImageView ivDelete;
    @BindView(R.id.bt_next_step)
    Button btNextStep;
    @BindView(R.id.tv_email_register)
    TextView tvEmailRegister;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_register;
    }

    @Override
    public RegisterPresenter getPresenter() {
        return new RegisterPresenter();
    }

    @Override
    protected void onViewBinding() {

        ClickUtil.click(ivDelete, () -> {
            etPhone.setText("");
        });
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (CheckUtil.isMobile(s.toString())) {
                    btNextStep.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (CheckUtil.isMobile(s.toString())) {
                    btNextStep.setEnabled(true);
                } else {
                    btNextStep.setEnabled(false);
                }
            }
        });
        ClickUtil.click(btNextStep, () -> {
            if (CheckUtil.isMobile(etPhone.getText().toString())) {
                mPresenter.sendCode(etPhone.getText().toString());
            } else {
                ToastUtil.showShort("请输入正确的手机号");
                etPhone.setText("");
            }
        });
        ClickUtil.click(tvGotoLogin, () -> {
            startActivity(new Intent(this, LoginActivity.class));
        });
    }

    public void sendCodeOk(String account) {

        Intent intent = new Intent(this, CodeCheckActivity.class);
        intent.putExtra(Constants.PHONE,account);
        startActivity(intent);

    }

    @Override
    protected void onDestroy() {
        System.out.println("destory");
        super.onDestroy();
    }
}
