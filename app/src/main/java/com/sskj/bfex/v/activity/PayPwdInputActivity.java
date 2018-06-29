package com.sskj.bfex.v.activity;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.sskj.bfex.R;
import com.sskj.bfex.common.widget.ToolBarLayout;
import com.sskj.bfex.p.activity.PayPwdInputPresenter;
import com.sskj.bfex.utils.CheckUtil;
import com.sskj.bfex.utils.ClickUtil;
import com.sskj.bfex.utils.ToastUtil;
import com.sskj.bfex.v.base.BaseActivity;

import butterknife.BindView;

public class PayPwdInputActivity extends BaseActivity<PayPwdInputPresenter> {
    /**
     * <pre>
     *     author : 李岩
     *     e-mail : 465357793@qq.com
     *     time   : 2018/04/03
     *     desc   : 支付密码设置
     *     version: 1.0
     * </pre>
     */


    @BindView(R.id.toolBar)
    ToolBarLayout toolBar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.pay_pwd_switch)
    CheckBox mPayPwdSwitch;
    @BindView(R.id.submit)
    Button mSubmit;
    @BindView(R.id.pay_pwd_edittext)
    EditText mPayPwdEditText;
//    @BindView(R.id.etVerificationCode)
//    VerificationCodeEditText etVerificationCode;

    private TextView.OnEditorActionListener dealEditorAction = (v, actionId, event) -> {
        //以下方法防止两次发送请求
        if (actionId == EditorInfo.IME_ACTION_SEND ||
                (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
            switch (event.getAction()) {
                case KeyEvent.ACTION_UP://"你点了软键盘'回车'按钮"
                    //						//隐藏软键盘
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    return true;
                default:
                    return true;
            }
        }
        return false;
    };

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_pay_pwd_input;
    }

    @Override
    public PayPwdInputPresenter getPresenter() {
        return new PayPwdInputPresenter();
    }

    @Override
    protected void onViewBinding() {
        toolBar.setLeftButtonOnClickLinster(v -> onBackPressed());
        title.setText("输入支付密码");

        mPayPwdEditText.setOnEditorActionListener(dealEditorAction);

        mPayPwdSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mPayPwdEditText.setTransformationMethod(new HideReturnsTransformationMethod());

            } else {
                mPayPwdEditText.setTransformationMethod(new PasswordTransformationMethod());
            }
        });
        mPayPwdEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 8) {
                    mSubmit.setEnabled(true);
                } else {
                    mSubmit.setEnabled(false);
                }
            }
        });

        ClickUtil.click(mSubmit, () -> { //提交修改

            String pwd = mPayPwdEditText.getText().toString().trim();
            if (TextUtils.isEmpty(pwd)) {
                ToastUtil.showShort("请输入交易密码");
                return;
            }
            if (!CheckUtil.isPayPwd(pwd)) {
                ToastUtil.showShort("交易密码格式不正确");
                return;
            }

            mPresenter.checkPayPwd(pwd);
        });
    }

    public void checkOk() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void onCreateOrderSuccess() {
        startActivity(new Intent(PayPwdInputActivity.this, TradeActivity.class));
        finish();
    }



}
