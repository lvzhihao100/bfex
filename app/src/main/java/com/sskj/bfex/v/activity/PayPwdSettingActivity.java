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
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.sskj.bfex.MyAppLication;
import com.sskj.bfex.R;
import com.sskj.bfex.common.Constants;
import com.sskj.bfex.common.widget.ToolBarLayout;
import com.sskj.bfex.p.activity.SettingPresenter;
import com.sskj.bfex.utils.CheckUtil;
import com.sskj.bfex.utils.ClickUtil;
import com.sskj.bfex.utils.PhoneHideUtil;
import com.sskj.bfex.utils.SPUtils;
import com.sskj.bfex.utils.ToastUtil;
import com.sskj.bfex.v.base.BaseActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * <pre>
 *     author : 李岩
 *     e-mail : 465357793@qq.com
 *     time   : 2018/04/03
 *     desc   : 设置支付密码
 *     version: 1.0
 * </pre>
 */
public class PayPwdSettingActivity extends BaseActivity<SettingPresenter> {


    @BindView(R.id.toolBar)
    ToolBarLayout mToolBar;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.pay_pwd_edittext)
    EditText mPayPwdEdittext;
    @BindView(R.id.pay_pwd_switch)
    CheckBox mPayPwdSwitch;
    @BindView(R.id.pay_pwd_check_edittext)
    EditText mPayPwdCheckEdittext;
    @BindView(R.id.pay_pwd_check_switch)
    CheckBox mPayPwdCheckSwitch;
    @BindView(R.id.pay_pwd_auth_code_edittext)
    EditText mPayPwdAuthCodeEdittext;
    @BindView(R.id.pay_pwd_auth_code)
    CheckedTextView mPayPwdAuthCode;
    @BindView(R.id.submit)
    Button mSubmit;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
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
    private DisposableSubscriber<Long> disposableSubscriber;
    private String successCode;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_pay_pwd_setting;
    }

    @Override
    public SettingPresenter getPresenter() {
        return new SettingPresenter();
    }

    @Override
    protected void onViewBinding() {
        mTitle.setText("支付密码设置");
        mToolBar.setLeftButtonOnClickLinster(v -> onBackPressed());
        String mobile = (String) SPUtils.get(MyAppLication.getApplication(), Constants.SP_Mobile, "");
        if (!TextUtils.isEmpty(mobile)) {
            tvPhone.setText(String.format(getString(R.string.phone), PhoneHideUtil.getHide(mobile)));
        }

        ClickUtil.click(mPayPwdAuthCode, () -> { //获取验证码
            String pwd1 = mPayPwdEdittext.getText().toString().trim();
            String pwd2 = mPayPwdCheckEdittext.getText().toString().trim();
            if (TextUtils.isEmpty(pwd1)) {
                ToastUtil.showShort("请输入新的交易密码");
                return;
            } else if (TextUtils.isEmpty(pwd2)) {
                ToastUtil.showShort("请确认交易密码");
                return;
            }
            if (!pwd1.equals(pwd2)) {
                ToastUtil.showShort("确认密码不相同，请重新输入");
                return;
            }
            if (!CheckUtil.isPayPwd(pwd1.toString())) {
                ToastUtil.showShort("请输入正确格式的交易密码");
                return;
            }
            mPresenter.requestSms();

        });
        ClickUtil.click(mSubmit, () -> { //提交修改
            String pwd1 = mPayPwdEdittext.getText().toString().trim();
            String pwd2 = mPayPwdCheckEdittext.getText().toString().trim();
//            String code = mPayPwdAuthCodeEdittext.getText().toString().trim();
            if (TextUtils.isEmpty(pwd1)) {
                ToastUtil.showShort("请输入新的交易密码");
                return;
            } else if (TextUtils.isEmpty(pwd2)) {
                ToastUtil.showShort("请确认交易密码");
                return;
            }
            if (!pwd1.equals(pwd2)) {
                ToastUtil.showShort("确认密码不相同，请重新输入");
                return;
            }
            if (TextUtils.isEmpty(successCode)) {
                ToastUtil.showShort("请输入验证码");
                return;
            }
            mPresenter.setPayPwd(pwd1,successCode);
        });


        mPayPwdEdittext.setOnEditorActionListener(dealEditorAction);
        mPayPwdCheckEdittext.setOnEditorActionListener(dealEditorAction);
        mPayPwdAuthCodeEdittext.setOnEditorActionListener(dealEditorAction);

        mPayPwdCheckSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mPayPwdCheckEdittext.setTransformationMethod(new HideReturnsTransformationMethod());

            } else {
                mPayPwdCheckEdittext.setTransformationMethod(new PasswordTransformationMethod());
            }
        });
        mPayPwdSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mPayPwdEdittext.setTransformationMethod(new HideReturnsTransformationMethod());
            } else {
                mPayPwdEdittext.setTransformationMethod(new PasswordTransformationMethod());
            }
        });

        mPayPwdAuthCodeEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 6) {
                    mPresenter.checkCode(s.toString().trim());
                }
            }
        });
    }

    public void changeBt(String code) {
        successCode = code;
        mSubmit.setEnabled(true);
    }

    public void onSmsResponseSuccess() {
        startCount();
    }

    private void startCount() {
        mPayPwdAuthCode.setEnabled(false);
        disposableSubscriber = new DisposableSubscriber<Long>() {
            @Override
            public void onNext(Long aLong) {
                int time = (int) (60 - aLong);
                mPayPwdAuthCode.setText(time + "秒后重新发送");
                mPayPwdAuthCode.setTextColor(getResources().getColor(R.color.colorDarkGreen));
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.toString());
            }

            @Override
            public void onComplete() {
                mPayPwdAuthCode.setText("获取验证码");
                mPayPwdAuthCode.setEnabled(true);
                mPayPwdAuthCode.setTextColor(getResources().getColor(R.color.colorTextGreen));
                if (!disposableSubscriber.isDisposed()) {
                    disposableSubscriber.dispose();
                    disposableSubscriber = null;
                }

            }
        };

        Flowable.interval(0, 1, TimeUnit.SECONDS, Schedulers.newThread())
                .take(60)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableSubscriber);
    }


    public void onPayPwdSettingSuccess() {
        setResult(RESULT_OK, new Intent().putExtra("resultData", "1"));
        finish();
    }
}
